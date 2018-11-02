package com.sja.demo.aop;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author sja  created on 2018/11/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Test
    public void add() throws Exception {
        User user = new User();
        user.setName("张三丰");
        userService.add(user);
    }



}
