package com.java.test.TheBeautyOfDesignPatterns.actualCombat.idBuilder;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * 第二轮重构：提高代码的可测试性
 *
 * @author yzm
 * @date 2021/5/19 - 11:06
 */

/**
 * Id Generator that is used to generate random IDs.
 * <p>
 * The IDs generated by this class are not absolutely unique,
 * but the probability of duplication is very low.
 */
public class RandomIdGenerator2 implements LogTraceIdGenerator {

    private static final Logger logger = LoggerFactory.getLogger(RandomIdGenerator2.class);

    /**
     * Generate the random ID. The IDs may be duplicated only in extreme situation.
     *
     * @return an random ID
     */
    @Override
    public String generate() {
        String substrOfHostName = getLastfieldOfHostName();
        long currentTimeMillis = System.currentTimeMillis();
        String randomString = generateRandomAlphameric(8);
        String id = String.format("%s-%d-%s",
                substrOfHostName, currentTimeMillis, randomString);
        return id;
    }

    /**
     * Get the local hostname and * extract the last field of the name string splitted by delimiter '.'.
     *
     * @return the last field of hostname. Returns null if hostname is not obtained.
     */
    private String getLastfieldOfHostName() {
        String substrOfHostName = null;
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            substrOfHostName = getLastSubstrSplittedByDot(hostName);
        } catch (UnknownHostException e) {
            logger.warn("Failed to get the host name.", e);
        }
        return substrOfHostName;
    }

    /**
     * Get the last field of {@hostName} splitted by delemiter '.'.
     *
     * @param hostName should not be null
     * @return the last field of {@hostName}. Returns empty string if {@hostName} is empty string.
     */
    @VisibleForTesting
    protected String getLastSubstrSplittedByDot(String hostName) {
        String[] tokens = hostName.split("\\.");
        String substrOfHostName = tokens[tokens.length - 1];
        return substrOfHostName;
    }

    /**
     * 这个 annotation 没有任何实际的作用，
     * 只起到标识的作用，告诉其他人说，这两个函数本该是 private 访问权限的，
     * 之所以提升访问权限到 protected，只是为了测试，只能用于单元测试中。
     *
     * @param length
     * @return
     */
    /**
     * Generate random string which * only contains digits, uppercase letters and lowercase letters.
     *
     * @param length should not be less than 0
     * @return the random string. Returns empty string if {@length} is 0
     */
    @VisibleForTesting
    protected String generateRandomAlphameric(int length) {
        char[] randomChars = new char[length];
        int count = 0;
        Random random = new Random();
        while (count < length) {
            int maxAscii = 'z';
            int randomAscii = random.nextInt(maxAscii);
            boolean isDigit = randomAscii >= '0' && randomAscii <= '9';
            boolean isUppercase = randomAscii >= 'A' && randomAscii <= 'Z';
            boolean isLowercase = randomAscii >= 'a' && randomAscii <= 'z';
            if (isDigit || isUppercase || isLowercase) {
                randomChars[count] = (char) (randomAscii);
                ++count;
            }
        }
        return new String(randomChars);
    }
}
