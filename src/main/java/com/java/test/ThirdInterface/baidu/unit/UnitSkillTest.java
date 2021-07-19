package com.java.test.ThirdInterface.baidu.unit;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.test.ThirdInterface.baidu.AuthService;
import com.java.test.ThirdInterface.baidu.BaiduHttpUtil;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

/**
 * @author yzm
 * @date 2020/12/14 - 14:52
 */
@Log4j
public class UnitSkillTest {

    private static final int BOOK_TICKET = 1065944;

    private static String utterance(String query, String botSession) {
        // 对话请求URL
        String talkUrl = "https://aip.baidubce.com/rpc/2.0/unit/bot/chat";

        //请求的参数用map封装
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> mapRequest = new HashMap<>();
        Map<String, Object> mapQueryInfo = new HashMap<>();
        Map<String, Object> mapClientSession = new HashMap<>();
        List<String> asrCandidatesList = new ArrayList<>();
        List<String> candidateOptionsList = new ArrayList<>();

        // 技能的session信息，由技能创建，client从上轮应答中取出并直接传递，不需要了解其内容。
        // 如果为空，则表示清空session（开发者判断用户意图已经切换且下一轮会话不需要继承上一轮会话中的词槽信息时可以把session置空，从而进行新一轮的会话）。传参时可只传session_id。
        map.put("bot_session", botSession);
        // 日志唯一ID（用户与技能的一问一答为一次interaction，其中用户每说一次对应有一个log_id），同输入参数
        map.put("log_id", UUID.randomUUID().toString().replaceAll("-", ""));
        // 本轮请求体
        map.put("request", mapRequest);
        // 技能唯一标识，在『我的技能』的技能列表中第一列数字即为bot_id
        map.put("bot_id", BOOK_TICKET);
        // 固定值
        map.put("version", "2.0");

        // 系统自动发现不置信意图/词槽，
        // 并据此主动发起澄清确认的敏感程度。
        // 取值范围：0(关闭)、1(中敏感度)、2(高敏感度)。
        // 取值越高BOT主动发起澄清的频率就越高，建议值为1
        mapRequest.put("bernard_level", 1);
        // 本轮请求query（用户说的话）
        mapRequest.put("query", query);
        // 本轮请求query的附加信息
        mapRequest.put("query_info", mapQueryInfo);
        // 干预信息
        mapRequest.put("updates", "");
        // 开发者为其用户分配的id，用于区别开发者的每个最终用户。
        // UNIT保留两种形式的id，开发者在为最终用户分配id时应当避免分配这些形式的id
        // ID形式	说明
        // UNITWEB*	用于标识来自UNIT网站对话窗口的请求。形式为UNITWEB + 技能ID/机器人ID
        // UNITDEV*	用于标识来自开发者自己的请求。形式为UNITDEV + 任意后缀（一个好的选择是使用开发者的百度id作为后缀）
        mapRequest.put("user_id", "UNIT_WEB_37819");

        // 请求信息来源若为ASR，该字段为ASR候选信息。（如果调用百度语音的API会有该信息，UNIT会参考该候选信息做综合判断处理。）
        mapQueryInfo.put("asr_candidates", asrCandidatesList);
        // 请求信息来源，可选值："ASR","KEYBOARD"。
        // ASR为语音输入，KEYBOARD为键盘文本输入。
        // 针对ASR输入，UNIT平台内置了纠错机制，会尝试解决语音输入中的一些常见错误
        mapQueryInfo.put("source", "KEYBOARD");
        // query_info.type为TEXT时，为常规的文本型query
        // query_info.type为EVENT时，代表query是一个事件，事件为一组K-V（json），且其中必须包含一个名为『event_name』的key，其他自便。
        mapQueryInfo.put("type", "TEXT");
        String clientSession = "";
        // 预留字段
        mapClientSession.put("client_results", "");
        // 存储client端提供的候选项列表，每个候选项对应一个object。
        mapClientSession.put("candidate_options", candidateOptionsList);


        ObjectMapper objectMapper = new ObjectMapper();
        try {
            clientSession = objectMapper.writeValueAsString(mapClientSession).replace("\"", "\\\\\\\"");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        mapRequest.put("client_session", clientSession);

        try {
            // 请求参数
            String params = objectMapper.writeValueAsString(map);
            String accessToken = AuthService.getAuth();
            String result = BaiduHttpUtil.post(talkUrl, accessToken, "application/json", params);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        // 实现多伦对话需要将上一轮中的bot-session 作为参数传入
        String botSession = "";
        String result = utterance("我要买票", botSession);
        // Result 返回结果json格式解析成java类.
        UnitSkillRes resultbean = JSONObject.parseObject(result, UnitSkillRes.class);
        // 当前处于那个意图
        String intention = resultbean.getResult().getResponse().getSchema().getIntent();
        // 答复文本内容
        String say = resultbean.getResult().getResponse().getActionList().get(0).getSay();
        botSession = resultbean.getResult().getBotSession();
        String errorMsg = resultbean.getErrorMsg();
        int errorCode = resultbean.getErrorCode();

        while (StringUtils.isBlank(errorMsg) && errorCode == 0) {
            // 如果意图部署结束语或者正常的结束就继续调用否则跳出或者挂断
            // 如果调用成功则进行下一步操作
            System.out.println(say);
            // 结束意图分别是：INTENTION_11
            if ("INTENTION_11".equals(intention)) {
                System.out.println(" 本次对话结束， 如果进行再次话请继续！");
                botSession = "";
            }
            Scanner scanner = new Scanner(System.in);
            String userAnswer = scanner.nextLine();
            result = utterance(userAnswer, botSession);
            resultbean = JSONObject.parseObject(result, UnitSkillRes.class);
            // 当前处于那个意图
            intention = resultbean.getResult().getResponse().getSchema().getIntent();
            // 答复文本内容
            say = resultbean.getResult().getResponse().getActionList().get(0).getSay();
            botSession = resultbean.getResult().getBotSession();
            errorMsg = resultbean.getErrorMsg();
            errorCode = resultbean.getErrorCode();
        }
    }

}


