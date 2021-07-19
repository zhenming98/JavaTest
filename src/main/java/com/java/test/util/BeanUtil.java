package com.java.test.util;


import org.apache.poi.ss.formula.functions.T;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yzm
 * @date 2021/5/24 - 10:59
 */
public class BeanUtil {

    /**
     * bean => map
     *
     * @param o Object
     * @return Map
     */
    public static Map<String, Object> beanToMap(Object o) {
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            //1.获取bean信息
            BeanInfo beanInfo = Introspector.getBeanInfo(o.getClass());
            PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
            if(properties != null && properties.length>0){
                for (PropertyDescriptor prop :properties) {
                    //2.得到属性名
                    String name = prop.getName();
                    //3.过滤class属性
                    if(!"class".equals(name)){
                        //4.得到属性的get方法
                        Method getter = prop.getReadMethod();
                        //5.获取属性值
                        Object value = getter.invoke(o);
                        //6.放入map中
                        map.put(name,value);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    /**
     * map => bean
     * @param map map
     * @param c
     * @return
     */
    public static T mapToBean(Map<String, Object> map, Class<T> c) {
        return null;
    }

}
