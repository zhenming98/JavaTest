package com.java.test.ThirdInterface.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.java.test.util.httputil.OkHttpUtil;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yzm
 * @date 2021/5/14 - 22:30
 */
@Service
public class WxService {

    private static final String CODE_SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";

    private static final String APP_ID = "wx0e143d0d62a658cb";
    private static final String APP_SECRET = "190ff4e9547fe13dfc12ebdbabcea272";

//    private static final String APP_ID = "wxef3104dfe2e8352c";
//    private static final String APP_SECRET = "0d3130d28994679b9fe40c30b72db955";

    private static final String GRANT_TYPE = "authorization_code";

    public Map<String, String> getCode2Session(String code) throws Exception {
        Map<String, Object> params = new HashMap<>(4);
        params.put("appid", APP_ID);
        params.put("secret", APP_SECRET);
        params.put("js_code", code);
        params.put("grant_type", GRANT_TYPE);

        String s = OkHttpUtil.doGet(CODE_SESSION_URL, params);

        JSONObject object = JSON.parseObject(s);

        System.out.println(object);

        Map<String, String> r = new HashMap<>(3);
        r.put("session_key", object.get("session_key") == null ? null : object.get("session_key").toString());
        r.put("openid", object.get("openid") == null ? null : object.get("openid").toString());
        return r;
    }

}
