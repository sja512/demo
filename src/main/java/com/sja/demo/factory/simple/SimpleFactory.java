package com.sja.demo.factory.simple;

import com.sja.demo.factory.Audi;
import com.sja.demo.factory.Benz;
import com.sja.demo.factory.Bmw;
import com.sja.demo.factory.Car;

/**
 * @author sja  created on 2019/1/9.
 */
public class SimpleFactory {

    public Car getCar(String name){
        if("Audi".equalsIgnoreCase(name)){
            return new Audi();
        }else if("Bmw".equalsIgnoreCase(name)){
            return new Bmw();
        }else if("Benz".equalsIgnoreCase(name)){
            return new Benz();
        }else {
            return null;
        }
    }
}
