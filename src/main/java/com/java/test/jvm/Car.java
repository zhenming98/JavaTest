package com.java.test.jvm;

/**
 * @author yzm
 * @date 2021/4/10 - 10:14
 */
public class Car {

    public static void main(String[] args) {
        Car car1 = new Car();
        Car car2 = new Car();

        System.out.println(car1.hashCode());
        System.out.println(car2.hashCode());

        System.out.println(car1.getClass().hashCode());
        System.out.println(car2.getClass().hashCode());

        Class c1 = car1.getClass();
        ClassLoader classLoader = c1.getClassLoader();
        //App ClassLoader
        System.out.println(classLoader);
        //Ext ClasslLader
        System.out.println(classLoader.getParent());
        //null
        System.out.println(classLoader.getParent().getParent());
    }

}
