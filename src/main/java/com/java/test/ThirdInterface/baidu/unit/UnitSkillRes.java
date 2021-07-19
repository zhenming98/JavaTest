package com.java.test.ThirdInterface.baidu.unit;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yzm
 * @date 2020/12/14 - 15:34
 */
@NoArgsConstructor
@Data
public class UnitSkillRes {
    /**
     * 错误码，为0时表示成功
     */
    @JSONField(name = "error_code")
    private int errorCode;
    /**
     * 错误信息，errno!= 0 时存在
     */
    @JSONField(name = "error_msg")
    private String errorMsg;
    /**
     * 返回数据对象，当errno为0时有效
     */
    private ResultBean result;

    @NoArgsConstructor
    @Data
    public static class ResultBean {
        /**
         * 日志唯一ID（用户与技能的一问一答为一次interaction，其中用户每说一次对应有一个log_id），同输入参数
         */
        @JSONField(name = "log_id")
        private String logId;
        /**
         * 本轮对话后更新的session信息，同请求参数
         */
        @JSONField(name = "bot_session")
        private String botSession;
        /**
         * 为本轮请求+应答之组合，生成的id
         */
        @JSONField(name = "interaction_id")
        private String interactionId;
        /**
         * =2.0，当前api版本对应协议版本号为2.0，固定值
         */
        private String version;
        /**
         * 技能ID，同请求参数
         */
        @JSONField(name = "bot_id")
        private String botId;
        private String timestamp;
        /**
         * 本轮应答体
         */
        private ResponseBean response;

        @NoArgsConstructor
        @Data
        public static class ResponseBean {
            /**
             * 解析的schema，解析意图、词槽结果都从这里面获取
             */
            private SchemaBean schema;
            /**
             * SLU解析的结果
             */
            @JSONField(name = "qu_res")
            private QuResBean quRes;
            /**
             * 动作列表
             */
            @JSONField(name = "action_list")
            private List<ActionListBean> actionList;

            @NoArgsConstructor
            @Data
            public static class SchemaBean {
                /**
                 * 意图置信度
                 */
                @JSONField(name = "intent_confidence")
                private int intentConfidence;
                /**
                 * domain分类置信度[已废弃]
                 */
                @JSONField(name = "domain_confidence")
                private int domainConfidence;
                /**
                 * 意图
                 */
                private String intent;
                /**
                 * 词槽列表
                 */
                private List<SlotsBean> slots;

                @NoArgsConstructor
                @Data
                public static class SlotsBean {
                    /**
                     * 词槽值细化类型[保留字段]
                     */
                    @JSONField(name = "word_type")
                    private String wordType;
                    /**
                     * 词槽置信度
                     */
                    private int confidence;
                    /**
                     * 词槽长度
                     */
                    private int length;
                    /**
                     * 词槽名称
                     */
                    private String name;
                    /**
                     * 词槽值
                     */
                    @JSONField(name = "original_word")
                    private String originalWord;
                    /**
                     * 词槽是在第几轮对话中引入的
                     */
                    @JSONField(name = "session_offset")
                    private int sessionOffset;
                    /**
                     * 词槽起始
                     */
                    private int begin;
                    /**
                     * 归一化词槽值
                     */
                    @JSONField(name = "normalized_word")
                    private String normalizedWord;
                    /**
                     * 引入的方式
                     */
                    @JSONField(name = "merge_method")
                    private String mergeMethod;
                    /**
                     * 子词槽list，内部结构同正常词槽。
                     */
                    @JSONField(name = "sub_slots")
                    private List<?> subSlots;
                }
            }

            @NoArgsConstructor
            @Data
            public static class QuResBean {
                /**
                 * 最终qu结果，内部格式同result.response.qu_res.candidates[]
                 */
                @JSONField(name = "qu_res_chosen")
                private String quResChosen;
                /**
                 * 原始query
                 */
                @JSONField(name = "raw_query")
                private String rawQuery;
                /**
                 * query结果状态
                 */
                private int status;
                /**
                 * query结果时间戳
                 */
                private int timestamp;
                /**
                 * 意图候选项
                 */
                private List<CandidatesBean> candidates;
                /**
                 * query的词法分析结果
                 */
                @JSONField(name = "lexical_analysis")
                private List<LexicalAnalysisBean> lexicalAnalysis;
                /**
                 * query的情感分析结果
                 */
                @JSONField(name = "sentiment_analysis")
                private SentimentAnalysisBean sentimentAnalysis;

                @NoArgsConstructor
                @Data
                public static class SentimentAnalysisBean {
                    private double pval;
                    private String label;
                }

                @NoArgsConstructor
                @Data
                public static class CandidatesBean {
                    /**
                     * intent_confidence : 100
                     * match_info : {"group_id":"23","id":"1958136","informal_word":"我\t是","match_keywords":"  ","match_pattern":"[D:user_answer1]\t[D:user_answer1]","ori_pattern":"[D:user_answer1]\t[D:user_answer1]","ori_slots":{"confidence":100.0,"domain_confidence":0.0,"extra_info":{},"from_who":"smart_qu","intent":"INTENTION_2","intent_confidence":100.0,"intent_need_clarify":false,"match_info":"[D:user_answer1] \t[D:user_answer1] ","slots":[{"begin":0,"confidence":100.0,"father_idx":-1,"length":2,"name":"user_answer1","need_clarify":false,"normalized_word":"","original_word":"对","word_type":""},{"begin":2,"confidence":100.0,"father_idx":-1,"length":4,"name":"user_answer1","need_clarify":false,"normalized_word":"","original_word":"我是","word_type":""}]},"real_threshold":1.0,"threshold":0.4000000059604645}
                     * slots : [{"word_type":"","father_idx":-1,"confidence":100,"length":1,"name":"user_answer1","original_word":"对","begin":0,"need_clarify":false,"normalized_word":"对"},{"word_type":"","father_idx":-1,"confidence":100,"length":2,"name":"user_answer1","original_word":"我是","begin":1,"need_clarify":false,"normalized_word":"我是"}]
                     * extra_info : {"group_id":"23","real_threshold":"1","threshold":"0.4"}
                     * confidence : 100
                     * domain_confidence : 0
                     * from_who : pow-slu-lev1
                     * intent : INTENTION_2
                     * intent_need_clarify : false
                     */
                    @JSONField(name = "intent_confidence")
                    private int intentConfidence;
                    @JSONField(name = "match_info")
                    private String matchInfo;
                    @JSONField(name = "extra_info")
                    private ExtraInfoBean extraInfo;
                    private int confidence;
                    @JSONField(name = "domain_confidence")
                    private int domainConfidence;
                    @JSONField(name = "from_who")
                    private String fromWho;
                    private String intent;
                    @JSONField(name = "intent_need_clarify")
                    private boolean intentNeedClarify;
                    private List<SlotsBeanX> slots;

                    @NoArgsConstructor
                    @Data
                    public static class ExtraInfoBean {
                        /**
                         * group_id : 23
                         * real_threshold : 1
                         * threshold : 0.4
                         */
                        @JSONField(name = "group_id")
                        private String groupId;
                        @JSONField(name = "real_threshold")
                        private String realThreshold;
                        private String threshold;
                    }

                    @NoArgsConstructor
                    @Data
                    public static class SlotsBeanX {
                        /**
                         * word_type :
                         * father_idx : -1
                         * confidence : 100
                         * length : 1
                         * name : user_answer1
                         * original_word : 对
                         * begin : 0
                         * need_clarify : false
                         * normalized_word : 对
                         */
                        @JSONField(name = "word_type")
                        private String wordType;
                        @JSONField(name = "father_idx")
                        private int fatherIdx;
                        private int confidence;
                        private int length;
                        private String name;
                        @JSONField(name = "original_word")
                        private String originalWord;
                        private int begin;
                        @JSONField(name = "need_clarify")
                        private boolean needClarify;
                        @JSONField(name = "normalized_word")
                        private String normalizedWord;
                    }
                }

                @NoArgsConstructor
                @Data
                public static class LexicalAnalysisBean {
                    /**
                     * etypes : ["[D:user_answer1]"]
                     * basic_word : ["对"]
                     * weight : 0.129
                     * term : 对
                     * type : user_answer1
                     */
                    private double weight;
                    private String term;
                    private String type;
                    private List<String> etypes;
                    @JSONField(name = "basic_word")
                    private List<String> basicWord;
                }
            }

            @NoArgsConstructor
            @Data
            public static class ActionListBean {
                /**
                 * 动作ID
                 */
                @JSONField(name = "action_id")
                private String actionId;
                /**
                 * 澄清与引导(type=clarify/guide/faqguide)时有效，表达澄清或引导的详细信息。
                 */
                @JSONField(name = "refine_detail")
                private RefineDetailBean refineDetail;
                /**
                 * 动作置信度
                 */
                private int confidence;
                /**
                 * 用户自定义应答，如果action_type为event，对应事件定义在此处
                 */
                @JSONField(name = "custom_reply")
                private String customReply;
                /**
                 * 应答话术
                 */
                private String say;
                /**
                 * 动作类型，具体有以下几种:
                 * clarify(澄清)
                 * satisfy(满足)
                 * guide(引导到对话意图)
                 * faqguide(引导到问答意图)
                 * understood(理解达成，注：内部使用)
                 * failure(理解失败)
                 * chat(聊天话术)
                 * event(触发事件，在答复型对话回应中选择了"执行函数"，将返回event类型的action)
                 */
                private String type;

                @NoArgsConstructor
                @Data
                public static class RefineDetailBean {
                    /**
                     * 交互形式。具体有以下几种：
                     * select(给出选项供选择)
                     * ask(提问)
                     * selectandask(给出选项并且追加提问)
                     */
                    private String interact;
                    /**
                     * 动作类型为clarify时有值，表明起因
                     */
                    @JSONField(name = "clarify_reason")
                    private String clarifyReason;
                    /**
                     * 选项列表。
                     */
                    @JSONField(name = "option_list")
                    private List<?> optionList;
                }
            }
        }
    }
}

