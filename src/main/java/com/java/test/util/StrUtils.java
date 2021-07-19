package com.java.test.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author yzm
 * @date 2021/4/14 - 9:16
 */
public class StrUtils extends StringUtils {

    public static final char SLASH = '/';
    public static final char BACK_SLASH = '\\';
    public static final char BIG_BRACKETS_LEFT = '{';
    public static final char BIG_BRACKETS_RIGHT = '}';
    public static final char BRACKETS_LEFT = '[';
    public static final char BRACKETS_RIGHT = ']';
    public static final char PARENTHESES_LEFT = '(';
    public static final char PARENTHESES_RIGHT = ')';
    public static final String EMPTY_JSON = "{}";

    /**
     * 格式化字符串<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") =》 this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") =》 this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") =》 this is \a for b<br>
     *
     * @param strPattern 字符串模板
     * @param argArray   参数列表
     * @return 结果
     */
    public static String format(final String strPattern, final Object... argArray) {
        if (StringUtils.isBlank(strPattern) || ArrayUtils.isEmpty(argArray)) {
            return strPattern;
        }
        final int strPatternLength = strPattern.length();
        // 初始化定义好的长度以获得更好的性能
        StringBuilder sbuf = new StringBuilder(strPatternLength + 50);
        // 记录已经处理到的位置
        int handledPosition = 0;
        // 占位符所在位置
        int delimIndex;
        for (int argIndex = 0; argIndex < argArray.length; argIndex++) {
            delimIndex = strPattern.indexOf(EMPTY_JSON, handledPosition);
            if (delimIndex == -1) {
                // 剩余部分无占位符
                if (handledPosition == 0) {
                    // 不带占位符的模板直接返回
                    return strPattern;
                }
                // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
                sbuf.append(strPattern, handledPosition, strPatternLength);
                return sbuf.toString();
            }
            // 转义符
            if (delimIndex > 0 && strPattern.charAt(delimIndex - 1) == BACK_SLASH) {
                if (delimIndex > 1 && strPattern.charAt(delimIndex - 2) == BACK_SLASH) {
                    // 双转义符
                    // 转义符之前还有一个转义符，占位符依旧有效
                    sbuf.append(strPattern, handledPosition, delimIndex - 1);
                    sbuf.append(str(argArray[argIndex], StandardCharsets.UTF_8));
                    handledPosition = delimIndex + 2;
                } else {
                    // 占位符被转义
                    argIndex--;
                    sbuf.append(strPattern, handledPosition, delimIndex - 1);
                    sbuf.append(BIG_BRACKETS_LEFT);
                    handledPosition = delimIndex + 1;
                }
            } else {
                // 正常占位符
                sbuf.append(strPattern, handledPosition, delimIndex);
                sbuf.append(str(argArray[argIndex], StandardCharsets.UTF_8));
                handledPosition = delimIndex + 2;
            }
        }
        // 加入最后一个占位符后所有的字符
        sbuf.append(strPattern, handledPosition, strPattern.length());
        return sbuf.toString();
    }

    /**
     * 将对象转为字符串
     * <pre>
     * 	 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 	 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj     对象
     * @param charset 字符集
     * @return 字符串
     */
    public static String str(Object obj, Charset charset) {
        if (null == obj) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof byte[]) {
            return str((byte[]) obj, charset);
        } else if (obj instanceof Byte[]) {
            return str((Byte[]) obj, charset);
        } else if (obj instanceof ByteBuffer) {
            return str((ByteBuffer) obj, charset);
        } else if (ArrUtils.isArray(obj)) {
            return ArrUtils.toString(obj);
        }
        return obj.toString();
    }

}
