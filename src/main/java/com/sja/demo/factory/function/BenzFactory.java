package com.sja.demo.factory.function;

import com.sja.demo.factory.Benz;
import com.sja.demo.factory.Car;

/**
 * @author sja  created on 2019/1/9.
 */
public class BenzFactory implements Factory {
    public Car createCar() {
        return new Benz();
    }
}
