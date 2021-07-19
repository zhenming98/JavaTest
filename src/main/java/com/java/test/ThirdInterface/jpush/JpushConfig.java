package com.java.test.ThirdInterface.jpush;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yzm
 * @date 2020/12/16 - 13:56
 */
@Component("jpushConfig")
public class JpushConfig {

    @Value("${jpush.appKey}")
    private String appkey;
    @Value("${jpush.masterSecret}")
    private String masterSecret;
    @Value("${jpush.liveTime}")
    private String liveTime;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getMasterSecret() {
        return masterSecret;
    }

    public void setMasterSecret(String masterSecret) {
        this.masterSecret = masterSecret;
    }

    public String getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(String liveTime) {
        this.liveTime = liveTime;
    }
}
