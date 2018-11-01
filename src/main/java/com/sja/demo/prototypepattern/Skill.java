package com.sja.demo.prototypepattern;

/**
 * @author sja  created on 2018/10/31.
 */
public class Skill implements Cloneable{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Skill clone(){
        Skill skill = null;
        try {
            skill = (Skill) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return skill;
    }
}
