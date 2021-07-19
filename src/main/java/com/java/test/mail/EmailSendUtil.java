package com.java.test.mail;

import com.sun.mail.util.MailSSLSocketFactory;

import java.util.Properties;
import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author yzm
 * @date 2021/4/13 - 11:29
 */
@Component
public class EmailSendUtil {

    @Resource
    EmailInstorageReceiveConfig emailConfig;

    public void send(String subject, String content) throws Exception {
        // 获取系统属性
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", emailConfig.getSmtpServer());
        props.put("mail.smtp.auth", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.host", emailConfig.getSmtpServer());

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);

        // 获取默认session对象
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailConfig.getAccount(), emailConfig.getPassword());
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailConfig.getAccount(), emailConfig.getNickName(), "utf-8"));
        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(emailConfig.getTo()));
        if (StringUtils.isNotEmpty(emailConfig.getCc())) {
            message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(emailConfig.getCc()));
        }
        if (StringUtils.isNotEmpty(emailConfig.getBcc())) {
            message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(emailConfig.getBcc()));
        }
        message.setSubject(subject);
        message.setText(content);
        Transport.send(message);
    }
}
