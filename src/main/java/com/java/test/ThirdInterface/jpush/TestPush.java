package com.java.test.ThirdInterface.jpush;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.google.gson.JsonObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yzm
 * @date 2021/1/18 - 11:42
 */
public class TestPush {

    //lockboss
    protected static final String APP_KEY = "cf3c743941968543f612ad70";
    protected static final String MASTER_SECRET = "f288450e0e36d6bc435aac66";
    protected static final Logger LOG = LoggerFactory.getLogger(TestPush.class);
    public static final String REGISTRATION_ID3 = "170976fa8a083c86af0";
    public static final String ALIAS1 = "audience_alias111";
    public static final String tags1 = "audience_tag1";
    public static final String TAG1 = "audience_tag111";
    public static final String TAG2 = "audience_tag2";
    public static final String TAG_ALL = "audience_tag_all111";


    public static void main(String[] args) throws Exception {
        testSendPush();
//        Set<String> tags1 = new HashSet<String>();
//        tags1.add(TAG1);
//        tags1.add(TAG_ALL);
//        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
//        DefaultResult result = jpushClient.updateDeviceTagAlias(REGISTRATION_ID3, ALIAS1, tags1, null);
//        assertThat(result.isResultOK(), is(true));

    }

    public static void testSendPush() {
        ClientConfig clientConfig = ClientConfig.getInstance();
        final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
        final PushPayload payload = buildPushObject_android_and_ios();
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
            System.out.println(result);
            // 如果使用 NettyHttpClient，需要手动调用 close 方法退出进程
            // If uses NettyHttpClient, call close when finished sending request, otherwise process will not exit.
            // jpushClient.close();
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            LOG.error("Sendno: " + payload.getSendno());
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            LOG.error("Sendno: " + payload.getSendno());
        }
    }

    //       三星 registId = "170976fa8a083c86af0";   华为： 100d855909c8bb3f21e  140fe1da9e18f888e99;  魅族：190e35f7e0cd4e32106；

    @Test
    public static PushPayload buildPushObject_android_and_ios() {
        Map<String, String> extras = new HashMap<String, String>();
        extras.put("test", "https://community.jiguang.cn/push");
        Map<String, JsonObject> thirdMap = new HashMap<>();
        setThirdPartyChannel(thirdMap);
        JsonObject trd = new JsonObject();
        trd.addProperty("title", "msg");
        trd.addProperty("content", "kkklllllllll");
        JsonObject trdExtras = new JsonObject();
        trdExtras.addProperty("trdExtras1", "123");
        trdExtras.addProperty("trdExtras2", "456");
        trd.add("extras",trdExtras);
        JsonObject callback = new JsonObject();
        callback.addProperty("url", "https://www.jiguagn.cn/callback");
        JsonObject params = new JsonObject();
        params.addProperty("name", "joe");
        params.addProperty("age", 26);
        callback.add("params", params);
        callback.addProperty("type", 3);
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId("140fe1da9e18f888e99"))
                .setOptions(Options.newBuilder()
                        //设置ios的APNS环境
                        .setApnsProduction(true)
                        //设置第三方通道策略
                        .setThirdPartyChannelV2(thirdMap)
                        .build())
                .addCustom("notification_3rd",trd)
                .setMessage(Message.newBuilder()
                        .setTitle("Message title")
                        .setMsgContent("自定义消息内容：msgContent------")
                        .addExtras(extras)
                        .build())
                .setNotification(Notification.newBuilder()
                        .setAlert("alert content")
                        //设置android通知消息
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle("Android Title")
                                //设置角标自动+1
                                .addCustom("badge_add_num", 1)
                                .addCustom("badge_class", "com.example.lockboss.MainActivity")
                                //设置自定义铃声，针对Android 8.0以上
//                                .setChannelId("1")
                                //如果通知内容很长，可以设置大文本通知栏样式，同时style要设置为1
                                .setStyle(1)
                                .setBigText("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh")
                                //设置右侧大图，只针对走极光通道
                                .setLargeIcon("https://aliosstest.woaizuji.com/image/oss//1024079842791718_5ef19fcfe9ce4c70a9b139f07da7c740.png")
                                //设置厂商跳转指定界面,不设置则默认跳转到主界面
                                .addCustom("uri_activity", "com.example.lockboss.TestActivity")
                                .addCustom("uri_action", "com.example.lockboss.TestActivity")
                                .addExtras(extras).build())
                        //设置ios通知消息
                        .addPlatformNotification(IosNotification.newBuilder()
                                //设置ios标题
                                .setAlert(IosAlert.newBuilder()
                                        .setTitleAndBody("title", "subtitle", "test ios alert json")
                                        .build())
                                //如果要设置右侧大图，推送的时候携带 "mutable-content":true 说明是支持 iOS10 的 UNNotificationServiceExtension
                                .setMutableContent(true)
                                //如果不需要自定义声音，推送的时候，iOS 的 sound 值保持默认，传 default ，不要传空！
                                .setSound("default")
                                //设置角标自动+1
                                .incrBadge(1)
                                .addExtra("extra_key", "extra_value").build())
                        .build())
//                .addCustom("callback", callback)
                .build();
    }

    /**
     * 设置第三方通道策略
     * distribution 为字符串类型，取值不能为空字符串，只能是"ospush"、"jpush"、"secondary_push"。
     * 值为 ospush 表示推送强制走厂商通道下发；
     * 值为 jpush 表示推送强制走极光通道下发；
     * 值为 secondary_push 表示推送优先走极光，极光不在线再走厂商，厂商作为辅助；【建议此种方式】
     * options.third_party_channel 的 key 只支持 xiaomi、huawei、meizu、oppo、vivo、fcm；
     */
    private static void setThirdPartyChannel(Map<String, JsonObject> thirdMap) {
        JsonObject ospushJsonObj = new JsonObject();
        ospushJsonObj.addProperty("distribution", "ospush");
        JsonObject jpushJsonObj = new JsonObject();
        jpushJsonObj.addProperty("distribution", "jpush");
        JsonObject secpushJsonObj = new JsonObject();
        secpushJsonObj.addProperty("distribution", "secondary_push");

        thirdMap.put("huawei", ospushJsonObj);
        thirdMap.put("xiaomi", ospushJsonObj);
        thirdMap.put("meizu", secpushJsonObj);
        thirdMap.put("fcm", ospushJsonObj);

    }

}

