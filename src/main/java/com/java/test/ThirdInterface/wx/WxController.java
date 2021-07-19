package com.java.test.ThirdInterface.wx;

import emptyhead.framework.core.web.api.response.R;
import org.junit.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author yzm
 * @date 2021/5/15 - 14:44
 */
@RestController
public class WxController {

    @Resource
    WxService wxService;

    @GetMapping(value = "wx")
    public R<?> wxTest(@RequestParam("code") String code,
                       @RequestParam("encryptedData") String encryptedData,
                       @RequestParam("iv") String iv) throws Exception {

        System.out.println(code);
        System.out.println(encryptedData);
        System.out.println(iv);

        Map<String, String> r = wxService.getCode2Session(code);

        DecryptUtil d = new DecryptUtil();
        byte[] result = d.decrypt(encryptedData, r.get("session_key"), iv);
        System.out.println(new String(result, StandardCharsets.UTF_8));

        r.put("data", new String(result, StandardCharsets.UTF_8));

        return R.success(r);
    }

    @Test
    public void test() throws Exception {
        WxService wxService = new WxService();

        String code = "061iSQ000FDhIL14xT000WbBsJ0iSQ0G";
        String encryptedData = "/bJk35sP7OszxZcdcojZKA05e8aIfgo2R+A+qm8/IaxoXnk6Mz2V0YhKWUxgFxCVhwijd5Uo/UEERs2J40BxBOXRY+EXgLKkMjjvSuljJOHvtyNSQOaOC2N/TwQZRRwBAAx02KaQtt/8y7Uiy1pfTNtWMLvzRktxJS5CYV6Lj+1K/y7pe5dBhNU/cafu9CoEwpMDf6RrwRY+0EbGJhOV3KYObeLCajyBaXQw8ImGmp5xX8CR8ZFaUA8ArSMIiFShaUxxFVExNmvZ0GTjhfk0DgufjwCo1YQIfDmz/68gdtEBTN9Mo5/TCsl6OcyTX7E9ch0tC1j/UnGCJCS7rEht+mWxERnZ/BMuTG0yPLnJ3xpHdvptGrMsXBJoXBL7szaxlodnM5eCl8KPXWNryQWFF2G4TRygdMg69wflzU/NZn0=";
        String iv = "9tChlIoGtmCZmfydHBMtRg==";

        Map<String, String> r = wxService.getCode2Session(code);

        DecryptUtil d = new DecryptUtil();
        byte[] result = d.decrypt(encryptedData, r.get("session_key"), iv);
        System.out.println(new String(result, StandardCharsets.UTF_8));

        r.put("data", new String(result, StandardCharsets.UTF_8));

        System.out.println(r);

    }
}
