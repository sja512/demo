package com.sja.demo.factory.function;

import com.sja.demo.factory.Car;

/**
 * 抽象工厂角色
 * 这是工厂方法模式的核心，它与应用程序无关。是具体工厂角色必须实现的接口或者必须继承的父类
 * @author sja  created on 2019/1/9.
 */
public interface Factory {

    Car createCar();
}
