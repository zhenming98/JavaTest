package com.java.test.TheLeetCode;

/**
 * @author yzm
 * @date 2021/5/16 - 12:00
 */
public class No1450 {
    /**
     * 给你两个整数数组 startTime（开始时间）和 endTime（结束时间），并指定一个整数 queryTime 作为查询时间。
     * 已知，第 i 名学生在 startTime[i] 时开始写作业并于 endTime[i] 时完成作业。
     * 请返回在查询时间 queryTime 时正在做作业的学生人数。形式上，返回能够使 queryTime 处于区间 [startTime[i], endTime[i]]（含）的学生人数。
     * <p>
     * 输入：startTime = [4], endTime = [4], queryTime = 4
     * 输出：1
     * 解释：在查询时间只有一名学生在做作业。
     */
    public static int no1450(int[] startTime, int[] endTime, int queryTime) {
        int result = 0;
        for (int i = 0; i < startTime.length; i++) {
            if (queryTime >= startTime[i] && queryTime <= endTime[i]) {
                result++;
            }
        }
        return result;
    }
}
