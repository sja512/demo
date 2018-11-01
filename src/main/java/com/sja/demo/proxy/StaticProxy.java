package com.sja.demo.proxy;

/**
 * 静态代理
 *
 * @author sja  created on 2018/8/2.
 */
public class StaticProxy implements Person {

    private Person target;

    public StaticProxy(Person target) {
        this.target = target;
    }

    @Override
    public void renting() {
        System.out.println("请问找什么样的房子");
        target.renting();
        System.out.println("ok");
    }
}
