package com.java.test.ThirdInterface.baidu.unit;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.test.ThirdInterface.baidu.AuthService;
import com.java.test.ThirdInterface.baidu.BaiduHttpUtil;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

/**
 * @author yzm
 * @date 2020/12/14 - 16:46
 */
@Log4j
public class UnitRobotTest {

    private static final String TASK_FLOW_ROBOT = "S41822";
    private static final String SKILL_ROBOT = "S42124";

    private static final String TALK_URL = "https://aip.baidubce.com/rpc/2.0/unit/service/chat";

    private static String utterance(String query, String botSession) {
        // 请求的参数用map封装
        Map<String, Object> map = new HashMap<>();
        // =2.0，当前api版本对应协议版本号为2.0，固定值
        map.put("version", "2.0");
        // 机器人ID，service_id 与skill_ids不能同时缺失，至少一个有值。
        map.put("service_id", TASK_FLOW_ROBOT);
        // 技能ID列表。我们允许开发者指定调起哪些技能。
        // 这个列表是有序的——排在越前面的技能，优先级越高。技能优先级体现在response的排序上
        // service_id和skill_ids可以组合使用
        // map.put("skill_ids", botSession);
        // 开发者需要在客户端生成的唯一id，用来定位请求，响应中会返回该字段。对话中每轮请求都需要一个log_id
        map.put("log_id", UUID.randomUUID().toString().replaceAll("-", ""));
        map.put("session", botSession);

        // 机器人对话状态。
        Map<String, Object> mapDialogState = new HashMap<>();
        // 本轮请求体。
        Map<String, Object> mapRequest = new HashMap<>();
        // 本轮请求query（用户说的话）
        mapRequest.put("query", query);
        // 开发者为其用户分配的id，用于区别开发者的每个最终用户。
        // UNIT保留两种形式的id，开发者在为最终用户分配id时应当避免分配这些形式的id
        // ID形式	说明
        // UNITWEB*	用于标识来自UNIT网站对话窗口的请求。形式为UNITWEB + 技能ID/机器人ID
        // UNITDEV*	用于标识来自开发者自己的请求。形式为UNITDEV + 任意后缀（一个好的选择是使用开发者的百度id作为后缀）
        mapRequest.put("user_id", "UNIT_WEB_37819");

        map.put("dialog_state", mapDialogState);
        map.put("request", mapRequest);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String params = objectMapper.writeValueAsString(map);
            String accessToken = AuthService.getAuth();
            String result = BaiduHttpUtil.post(TALK_URL, accessToken, "application/json", params);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String session = "";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String userSay = scanner.nextLine();
            String result = utterance(userSay, session);
            // Result 返回结果json格式解析成java类.
            UnitRobotRes resultbean = JSONObject.parseObject(result, UnitRobotRes.class);
            // 当前处于那个意图
            String intention = resultbean.getResult().getResponseList().get(0).getSchema().getIntent();

            System.out.println(resultbean.getResult().getResponseList().get(0));

            // 答复文本内容
            String say = resultbean.getResult().getResponseList().get(0).getActionList().get(0).getSay();
            session = resultbean.getResult().getSession();
            String errorMsg = resultbean.getErrorMsg();
            int errorCode = resultbean.getErrorCode();
            if (!StringUtils.isBlank(errorMsg) && errorCode != 0) {
                break;
            } else {
                System.out.println(say + "---------------------------------------" + intention);
            }
        }
    }
}

