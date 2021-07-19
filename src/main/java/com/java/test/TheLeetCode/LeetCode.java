package com.java.test.TheLeetCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author yzm
 * @date 2021/2/9 - 16:06
 */
public class LeetCode {

    public static void main(String[] args) {
        System.out.println(no992(new int[]{1, 2, 1, 2, 3}, 3));
        System.out.println(no1450(new int[]{1, 2, 1, 2, 3}, new int[]{1, 2, 1, 2, 3}, 3));
    }

    /**
     * 给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。
     */
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode no83(ListNode head) {
        // TODO: 2021/2/9
        return head;
    }

    /**
     * 给定一个正整数数组 A，如果 A 的某个子数组中不同整数的个数恰好为 K，则称 A 的这个连续、不一定独立的子数组为好子数组。
     * （例如，[1,2,3,1,2] 中有 3 个不同的整数：1，2，以及 3。）
     * 返回 A 中好子数组的数目。
     * <p>
     * 输入：A = [1,2,1,3,4], K = 3
     * 输出：3
     * 解释：恰好由 3 个不同整数组成的子数组：[1,2,1,3], [2,1,3], [1,3,4].
     */
    public static int no992(int[] A, int K) {
        // TODO: 2021/2/9
        List<Integer> a = IntStream.of(A).boxed().collect(Collectors.toList());
        int result = 0;
        if (K > A.length || K == 0) {
            return 0;
        }
        for (int i = 0; i < a.size(); i++) {
            List<Integer> b = new ArrayList<>();
            for (int j = i; j < a.size(); j++) {
                b.add(A[j]);
                if (b.size() == K) {
                    result++;
                    break;
                }
            }
        }
        return result;
    }

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
