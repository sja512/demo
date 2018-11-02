package com.sja.demo.aop;

import org.springframework.stereotype.Service;

/**
 * @author sja  created on 2018/11/1.
 */
@Service
public class UserService {
    public void add(User user){
        System.out.println("添加用戶："+user.getName());
    }
}
