package com.sja.demo.factory.abst;

import com.sja.demo.factory.Car;

/**
 * 自驾游抽象接口
 * @author sja  created on 2019/1/14.
 */
public abstract class AbstractSelfDrivingTourFactory {

    /**
     * 开什么车
     * @return
     */
    public abstract Car getCar();

    /**
     * 去哪里 线路
     */
    public abstract void way();
}
