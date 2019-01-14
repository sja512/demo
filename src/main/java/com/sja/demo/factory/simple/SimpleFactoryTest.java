package com.sja.demo.factory.simple;

import com.sja.demo.factory.Car;

/**
 * 简单工厂
 * @author sja  created on 2019/1/9.
 */
public class SimpleFactoryTest {

    public static void main(String[] args){
        //消费者  免除了直接创建产品对象
        //新增产品 对于产品部分来说，它是符合开闭原则的——对扩展开放、对修改关闭
        //工厂类不太理想，因为每增加一辆车，都要在工厂类中增加相应的商业逻辑和判 断逻辑，这显自然是违背开闭原则的
        //简单工厂模式适用于业务简单的情况下或者具体产品很少增加的情况
        Car car = new SimpleFactory().getCar("Audi");
        System.out.println(car.getName());
    }
}
