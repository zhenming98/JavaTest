package com.java.test.TheBeautyOfDesignPatterns.actualCombat.idBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * @author yzm
 * @date 2021/5/19 - 10:18
 */

public class IdGeneratorOld {
    private static final Logger logger = LoggerFactory.getLogger(IdGeneratorOld.class);

    /**
     * 一份“能用”的代码实现
     *
     * @return 第一部分是本机名的最后一个字段。第二部分是当前时间戳，精确到毫秒。第三部分是 8 位的随机字符串，包含大小写字母和数字。尽管这样生成的 ID 并不是绝对唯一
     * 103-1577456311467-3nR3Do45
     * 103-1577456311468-0wnuV5yw
     * 103-1577456311468-sdrnkFxN
     * 103-1577456311468-8lwk0BP0
     */
    public static String generate() {
        String id = "";
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            String[] tokens = hostName.split("\\.");
            if (tokens.length > 0) {
                hostName = tokens[tokens.length - 1];
            }
            char[] randomChars = new char[8];
            int count = 0;
            Random random = new Random();
            while (count < 8) {
                int randomAscii = random.nextInt(122);
                if (randomAscii >= 48 && randomAscii <= 57) {
                    randomChars[count] = (char) ('0' + (randomAscii - 48));
                    count++;
                } else if (randomAscii >= 65 && randomAscii <= 90) {
                    randomChars[count] = (char) ('A' + (randomAscii - 65));
                    count++;
                } else if (randomAscii >= 97 && randomAscii <= 122) {
                    randomChars[count] = (char) ('a' + (randomAscii - 97));
                    count++;
                }
            }
            id = String.format("%s-%d-%s", hostName, System.currentTimeMillis(), new String(randomChars));
        } catch (UnknownHostException e) {
            logger.warn("Failed to get the host name.", e);
        }
        return id;
    }

    /**
     * +
     * + 第二轮重构：提高代码的可测试性
     * + 第三轮重构：编写完善的单元测试
     * + 第四轮重构：所有重构完成之后添加注释
     */

}
