package com.sja.demo.proxy;

/**
 * 动态代理应用
 */
public class ProxyTest {

    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");

        try {
            Person obj = (Person) new JdkIntermediary().getInstance(new Jianan());
            System.out.println(obj.getClass());
            obj.renting();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
