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
public class UnitRobotRes {
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
         * =2.0，当前api版本对应协议版本号为2.0，固定值
         */
        private String version;
        /**
         * 机器人ID，同请求参数
         */
        @JSONField(name = "service_id")
        private String serviceId;
        /**
         * 日志唯一ID（用户与技能的一问一答为一次interaction，其中用户每说一次对应有一个log_id），同输入参数
         */
        @JSONField(name = "log_id")
        private String logId;
        /**
         * 本轮对话后更新的session信息，同请求参数
         */
        @JSONField(name = "session")
        private String session;

        /**
         * 为本轮请求+应答之组合，生成的id
         */
        @JSONField(name = "interaction_id")
        private String interactionId;

        /**
         * interaction生成的时间（以interaction_id的生成时间为准）。格式：YYYY-MM-DD HH:MM:SS.fff （24小时制，精确到毫秒）
         */
        private String timestamp;

        /**
         * 对话状态数据，外提以方便session托管。
         */
        @JSONField(name = "dialog_state")
        private DialogState dialogState;
        @NoArgsConstructor
        @Data
        public static class DialogState {

        }

        /**
         * 本轮应答列表。由于请求接口支持请求多个技能，因此这里可能有多个应答。应答列表是有序的，其第一个元素是最为推荐采用的一个应答
         */
        @JSONField(name = "response_list")
        private List<ResponseListBean> responseList;
        @NoArgsConstructor
        @Data
        public static class ResponseListBean {
            /**
             * 状态码，0为正常
             */
            private int status;
            /**
             * 错误信息，非零时有效
             */
            private String msg;
            /**
             * 应答来自哪个技能（skill_id）或机器人（service_id），注意有些应答可能是机器人给出的（不来自任何一个技能）。
             */
            private String origin;

            /**
             * 动作列表
             */
            @JSONField(name = "action_list")
            private List<ActionListBean> actionList;
            @NoArgsConstructor
            @Data
            public static class ActionListBean {
                /**
                 * 动作置信度
                 */
                private float confidence;
                /**
                 * 动作ID
                 */
                @JSONField(name = "action_id")
                private String actionId;
                /**
                 * 应答话术
                 */
                private String say;
                /**
                 * 用户自定义应答，如果action_type为event，对应事件定义在此处
                 */
                @JSONField(name = "custom_reply")
                private String customReply;
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

                /**
                 * 澄清与引导(type=clarify/guide/faqguide)时有效，表达澄清或引导的详细信息。
                 */
                @JSONField(name = "refine_detail")
                private RefineDetail refineDetail;

                @NoArgsConstructor
                @Data
                public static class RefineDetail {
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
                    private List<OptionListBean> optionList;

                    @NoArgsConstructor
                    @Data
                    public static class OptionListBean {
                        /**
                         * 选项文字
                         */
                        private String option;

                        /**
                         * result.response.action_list[].type 为 clarify 时
                         * Key	        类型	说明
                         * name	        string	意图、词槽的英文名
                         * text	        string	意图、词槽的中文描述
                         * value	    string
                         * optional	    词槽值（仅针对词槽）
                         * <p>
                         * result.response.action_list[].type 为 guide/faqguide 时
                         * Key	                类型	说明
                         * next_expect_intent	String	下一意图
                         */
                        private String info;
                    }
                }
            }

            /**
             * 解析的schema，解析意图、词槽结果都从这里面获取
             */
            private SchemaBean schema;

            @NoArgsConstructor
            @Data
            public static class SchemaBean {
                /**
                 * schema的总体置信度
                 */
                private double confidence;

                /**
                 * 意图
                 */
                private String intent;

                /**
                 * 意图置信度
                 */
                @JSONField(name = "intent_confidence")
                private double intentConfidence;

                /**
                 * 词槽列表
                 */
                private List<SlotsBean> slots;

                @NoArgsConstructor
                @Data
                public static class SlotsBean {
                    /**
                     * 词槽置信度
                     */
                    private double confidence;
                    /**
                     * 起始位置
                     */
                    private int begin;
                    /**
                     * 长度
                     */
                    private int length;
                    /**
                     * 词槽值
                     */
                    @JSONField(name = "original_word")
                    private String originalWord;
                    /**
                     * 归一化词槽值
                     */
                    @JSONField(name = "normalized_word")
                    private String normalizedWord;
                    /**
                     * 词槽值细化类型[保留字段]
                     */
                    @JSONField(name = "word_type")
                    private String wordType;
                    /**
                     * 词槽名称
                     */
                    private String name;
                    /**
                     * 词槽是在第几轮对话中引入的
                     */
                    @JSONField(name = "session_offset")
                    private int sessionOffset;
                    /**
                     * 词槽引入的方式
                     */
                    @JSONField(name = "merge_method")
                    private String mergeMethod;
                    /**
                     * 子词槽list，内部结构同正常词槽。
                     */
                    @JSONField(name = "sub_slots")
                    private List<SlotsBean> subSlots;
                }
            }

            /**
             * SLU解析结果，之所以是optional，是因为response不一定是由某个技能产生，因此不一定有SLU结果
             */
            @JSONField(name = "qu_res")
            private QuResBean quRes;

            @NoArgsConstructor
            @Data
            public static class QuResBean {
                /**
                 * query结果时间戳
                 */
                private int timestamp;

                /**
                 * query结果状态
                 */
                private int status;

                /**
                 * 原始query
                 */
                private String rawQuery;

                /**
                 * 最终qu结果，内部格式同 result.response.qu_res.candidates[]
                 */
                @JSONField(name = "qu_res_chosen")
                private String quResChosen;

                /**
                 * 意图候选项
                 */
                private List<CandidatesBean> candidates;

                @NoArgsConstructor
                @Data
                public static class CandidatesBean {
                    /**
                     * 解析结果整体的（综合意图和词槽）置信度，如果返回结果中无该字段，请重新训练后尝试。
                     */
                    private double confidence;
                    /**
                     * 候选项意图名称
                     */
                    private String intent;
                    /**
                     * 候选项意图置信度
                     */
                    @JSONField(name = "intent_confidence")
                    private double intentConfidence;
                    /**
                     * 意图是否需要澄清
                     */
                    @JSONField(name = "intent_need_clarify")
                    private boolean intentNeedClarify;

                    /**
                     * 来自哪个qu策略（smart-qu对应对话模板，ml-qu对应对话样本学习）
                     */
                    @JSONField(name = "from_who")
                    private String fromWho;
                    /**
                     * query匹配信息
                     */
                    @JSONField(name = "match_info")
                    private String matchInfo;
                    /**
                     * 候选项附加信息
                     */
                    @JSONField(name = "extra_info")
                    private String extraInfo;

                    /**
                     * 词槽列表
                     */
                    private List<SlotsBean> slots;

                    @NoArgsConstructor
                    @Data
                    public static class SlotsBean {
                        /**
                         * 词槽置信度
                         */
                        private double confidence;
                        /**
                         * 起始位置，注意：单位为字符
                         */
                        private int begin;
                        /**
                         * 长度，注意：单位为字符
                         */
                        private int length;
                        /**
                         * 词槽原始值
                         */
                        private String originalWord;
                        /**
                         * 词槽归一化值
                         */
                        private String normalizedWord;
                        /**
                         * 细粒度词槽类型（预留字段）
                         */
                        private String wordType;
                        /**
                         * 词槽名
                         */
                        private String name;
                        /**
                         * 词槽是否需要澄清
                         */
                        private boolean needClarify;
                        /**
                         * 父词槽index，非子词槽，取值-1
                         */
                        private int fatherIdx;

                    }
                }

                /**
                 * query的词法分析结果
                 */
                @JSONField(name = "lexical_analysis")
                private List<LexicalAnalysisBean> lexicalAnalysis;

                @NoArgsConstructor
                @Data
                public static class LexicalAnalysisBean {
                    /**
                     * 词汇(含命名实体)
                     */
                    private String term;
                    /**
                     * 重要性权重
                     */
                    private double weight;
                    /**
                     * 词性或专名类别
                     */
                    private String type;
                    /**
                     * 命名实体兼属的所有专名类别
                     */
                    private List<String> etypes;
                    /**
                     * 构成词汇的基本词
                     */
                    @JSONField(name = "basic_word")
                    private List<String> basicWord;
                }

                /**
                 * query的情感分析结果
                 */
                @JSONField(name = "sentiment_analysis")
                private SentimentAnalysisBean sentimentAnalysis;

                @NoArgsConstructor
                @Data
                public static class SentimentAnalysisBean {
                    /**
                     * 情感标签，取值范围："0"、"1"、"2"，分别代表：负向情感、无情感、正向情感
                     */
                    private String label;
                    /**
                     * 置信度，取值范围0-1
                     */
                    private double pval;
                }
            }
        }
    }
}

