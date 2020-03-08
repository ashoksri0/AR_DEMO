package com.p3.poc.demo.ar.preload.data.service;

import com.p3.poc.demo.ar.user.entity.Users;
import com.p3.poc.demo.ar.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ashok Kumar N
 * on 09/03/20.
 */

@Service
public class PreLoadService {
    @Autowired
    UserRepository userRepository;
    public void loadUser()
    {
        Users users=new Users();
    }
}
