package com.sja.demo.factory.function;

import com.sja.demo.factory.Bmw;
import com.sja.demo.factory.Car;

/**
 * @author sja  created on 2019/1/9.
 */
public class BmwFactory implements Factory {
    public Car createCar() {
        return new Bmw();
    }
}
