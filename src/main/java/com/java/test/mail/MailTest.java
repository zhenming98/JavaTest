package com.java.test.mail;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * @author yzm
 * @date 2021/4/13 - 16:44
 */
public class MailTest {

    public static void main(String[] args) throws AddressException {
        String address = "123 <1439976144@qq.com>,456 <456@qq.com>";
        InternetAddress[] internetAddresses = InternetAddress.parse(address);

        if (internetAddresses.length != 0) {
            for (InternetAddress internetAddress : internetAddresses) {
                System.out.println(internetAddress.getAddress());
                System.out.println(internetAddress.getPersonal());
                System.out.println("-------------------------------");
            }
        }
    }

}
