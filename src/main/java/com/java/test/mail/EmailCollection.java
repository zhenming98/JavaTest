package com.java.test.mail;

import emptyhead.framework.core.web.api.response.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author yzm
 * @date 2021/4/13 - 13:45
 */
@RestController
public class EmailCollection {

    @Resource
    EmailSendUtil emailSendUtil;

    @PostMapping(value = "email/send")
    public R<?> emailSend(@RequestBody Map params) {

        try {
            emailSendUtil.send(params.get("subject").toString(), params.get("content").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return R.success();
    }

}
