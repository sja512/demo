package com.sja.demo.factory.function;

/**
 * 工厂方法模式
 * @author sja  created on 2019/1/9.
 */
public class FactoryTest {
    public static void main(String[] args){
        //工厂方法模式是完全符合开闭原则的
        //只要按照抽象产品角色、抽象工厂角色提供的合同来生成，那么就可以被客户使用，而不必去修改任何已有的代码
        Factory factory = new AudiFactory();
        System.out.println(factory.createCar());
    }
}
