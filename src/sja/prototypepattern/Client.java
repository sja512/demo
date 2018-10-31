package sja.prototypepattern;

/**
 * @author sja  created on 2018/10/31.
 */
public class Client {
    public static void main(String[] args) {
        Resume resume1 = new Resume();
        Skill skill = new Skill();
        skill.setName("全栈");
        resume1.setName("张三");
        resume1.setBirthday("1988-05-12");
        resume1.setCompany("XX云计算");
        resume1.setSchool("大学");
        resume1.setSkill(skill);
        // resume3 和 resume1同一个对象
        Resume resume3 = resume1;
        //克隆 内存中新的对象
        Resume resume2 = resume1.clone();
        //浅克隆 修改同一个对象skill 深克隆不会影响resume1的skill
        resume2.getSkill().setName("后端");
        resume1.print();
        resume2.print();
        //false
        System.out.println(resume1 == resume2);
        //true
        System.out.println(resume1 == resume3);
        //true
        System.out.println(resume1.getClass() == resume2.getClass());

    }
}
