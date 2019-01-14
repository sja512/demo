package com.sja.demo.factory.abst;

import com.sja.demo.factory.Audi;
import com.sja.demo.factory.Car;

/**
 * @author sja  created on 2019/1/14.
 */
public class AudiNanJingFactory extends AbstractSelfDrivingTourFactory {

    @Override
    public Car getCar() {
        System.out.println("开奥迪车");
        return new Audi();
    }

    @Override
    public void way(){
        new NanJingRoute().way();
    }

}
