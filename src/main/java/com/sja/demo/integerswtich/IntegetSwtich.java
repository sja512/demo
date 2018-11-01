package com.sja.demo.integerswtich;

import java.lang.reflect.Field;

/**
 * @author sja  created on 2018/10/29.
 */
public class IntegetSwtich {

    public static void main(String[] args) throws  Exception{
        Integer a = 1;
        Integer b = 2;

        swtich1(a,b);
        System.out.println("a="+a +" b="+b);
    }

    private static void  swtich(Integer a,Integer b) throws Exception{
        Field field = Integer.class.getDeclaredField("value");
        field.setAccessible(true);
        int temp = b;
        field.set(b,a);
        field.set(a,new Integer(temp));
    }

    /**
     * 值传递式的调用，对于对象传递的是地址值。给地址值重新赋值等于重新指向，不会影响外层
     * @param a
     * @param b
     * @throws Exception
     */
    private static void  swtich1(Integer a,Integer b) throws Exception{
        Integer temp = b;
        b = a;
        a = temp;
        System.out.println("aa="+a +" bb="+b);
    }

}
