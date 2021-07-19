package com.java.test.mail;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yzm
 * @date 2021/4/13 - 11:30
 */
@Data
@Component
@ConfigurationProperties(prefix = "instorage.mail")
public class EmailInstorageReceiveConfig {
    private String smtpServer;
    private String account;
    private String password;
    private String nickName;
    private String to;
    private String cc;
    private String bcc;
}
