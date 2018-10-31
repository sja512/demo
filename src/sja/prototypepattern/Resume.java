package sja.prototypepattern;

/**
 * @author sja  created on 2018/10/31.
 */
public class Resume implements Cloneable {
    private String name;
    private String birthday;
    private String sex;
    private String school;
    private String time;
    private String company;
    private Skill skill;

    /**
     * 1.浅度克隆
     * 对某个对象进行克隆，对象的的成员变量如果包括引用类型或者数组，
     * 那么克隆的时候不会把这些对象也带着复制到克隆出来的对象里面的，只是复制一个引用，这个引用指向被克隆对象的成员对象，
     * 但是基本数据类型是会跟着被带到克隆对象里面去的。
     * 2.深度克隆
     * 深度可能就是把对象的所有属性都统统复制一份新的到目标对象里面去。
     * @return
     */
    @Override
    public Resume clone(){
        Resume resume = null;
        try {
            resume = (Resume)super.clone();
            //深克隆
            resume.skill = skill.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return resume;
    }

    public void print(){
        System.out.println("姓名：" + name);
        System.out.println("生日:" + birthday + ",性别:" + sex + ",毕业学校：" + school);
        System.out.println("工作年限:" + time + ",公司:" + company);
        System.out.println("技能:" + skill.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}
