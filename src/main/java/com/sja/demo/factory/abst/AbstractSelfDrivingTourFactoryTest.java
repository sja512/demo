package com.sja.demo.factory.abst;

/**
 * @author sja  created on 2019/1/14.
 */
public class AbstractSelfDrivingTourFactoryTest {

    public static void main(String[] args){
        AbstractSelfDrivingTourFactory factory = new AudiNanJingFactory();
        System.out.println(factory.getCar());
        factory.way();
    }
}
