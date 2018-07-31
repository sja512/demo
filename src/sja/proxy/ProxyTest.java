package sja.proxy;

public class ProxyTest {

    public static void main(String[] args) {
        try {
            Person obj = (Person) new Intermediary().getInstance(new Jianan());
            System.out.println(obj.getClass());
            obj.renting();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
