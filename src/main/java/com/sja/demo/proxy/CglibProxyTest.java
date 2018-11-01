package com.sja.demo.proxy;

/**
 * @author sja  created on 2018/11/1.
 */
public class CglibProxyTest {
    public static void main(String[] args){
        CglibProxy cglibProxy = new CglibProxy();
        Person jianan = (Person) cglibProxy.getInstance(new Jianan());
        jianan.renting();
    }
}
