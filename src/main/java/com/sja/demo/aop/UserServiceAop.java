package com.sja.demo.aop;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author sja  created on 2018/11/1.
 */
public class UserServiceAop {

    public void before(){
        System.out.println("新增用户前执行");
    }

    public void after(){
        System.out.println("新增用户后执行");
    }

    public void around(ProceedingJoinPoint joinPoint){
        Object[] arguments = joinPoint.getArgs();//传入的参数
        //此处joinPoint的实现类是MethodInvocationProceedingJoinPoint
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;//获取参数名
        for(int i=0;i<methodSignature.getParameterNames().length;i++){
            System.out.println(methodSignature.getParameterNames()[i]);//这就是每个参数的名字啦
        }
        System.out.println("新增用户前执行");
    }
}
