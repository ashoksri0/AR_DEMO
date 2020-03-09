/*package com.p3.poc.demo.ar;


import com.p3.poc.demo.ar.user.entity.User;
import com.p3.poc.demo.ar.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    UserRepository repository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        repository.save(new User("User FN", "User LN", "user1@user.com", new Date("1968-01-01"), new Date("2020-01-01")));
    }
}*/

