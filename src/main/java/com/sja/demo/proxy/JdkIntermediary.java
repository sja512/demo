package com.sja.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 中介代理
 * jdk动态生成一个代理类继承Proxy实现接口
 * @author sja  created on 2018/7/31.
 */
public class JdkIntermediary implements InvocationHandler{

    private Object target;
    //获取被代理的对象
    public Object getInstance(Object target)throws Exception {
        this.target = target;
        Class clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("请问要什么样的房子");
        method.invoke(target,args);
        System.out.println("刚好有你要的房子");

        return null;
    }
}
