package com.sja.demo.factory.function;

import com.sja.demo.factory.Audi;
import com.sja.demo.factory.Car;

/**
 * 具体工厂角色
 * 它含有和具体业务逻辑有关的代码。由应用程序调用以创建对应的具体产品的对象。在java中它由具体的类来实现
 * @author sja  created on 2019/1/9.
 */
public class AudiFactory implements Factory {
    public Car createCar() {
        return new Audi();
    }
}
