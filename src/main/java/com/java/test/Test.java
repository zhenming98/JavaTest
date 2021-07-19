package com.java.test;


import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author yzm
 * @date 2021/7/12 - 17:07
 */
public class Test {

    public static void main(String args[]) {

        List<Program> massageList = new ArrayList<>();
        massageList.add(new Program(2, "22222222"));
        massageList.add(new Program(4, "55555555"));
        massageList.add(new Program(4, "66666666"));
        massageList.add(new Program(4, "77777777"));
        massageList.add(new Program(4, "12321321312312321312"));
        massageList.add(new Program(4, "88888888"));
        massageList.add(new Program(5, "99999999"));
        massageList.add(new Program(5, "00000000"));
        massageList.add(new Program(5, "00000000"));

        System.out.println(massageList);
        System.out.println(massageList.size());

        int[] typeList = new int[]{3, 1, 4, 5, 2};

        for (int type : typeList) {
            List<Program> massageType = massageList.stream().filter(p -> p.getType() == type).collect(Collectors.toList());
            while (massageList.size() > 8) {
                if (massageType.size() == 0) {
                    break;
                }
                int i = new Random().nextInt(massageType.size());
                Program massage = massageType.get(i);
                if (massage == null || massageList.size() == 8) {
                    return;
                }
                massageList.remove(massage);
                massageType.remove(massage);
            }
        }

        System.out.println(massageList);
        System.out.println(massageList.size());
    }

    @Data
    static class Program {
        public Integer type;
        public String command;

        public Program() {

        }

        public Program(Integer type, String command) {
            this.type = type;
            this.command = command;
        }
    }

}

