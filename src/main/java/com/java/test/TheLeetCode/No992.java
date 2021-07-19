package com.java.test.TheLeetCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author yzm
 * @date 2021/2/9 - 16:06
 */
public class No992 {

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



}
