package com.p3.poc.demo.ar.user.service;


import com.p3.poc.demo.ar.user.entity.Users;
import com.p3.poc.demo.ar.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public List<Users> findAll()
    {
        return userRepository.findAll();
    }

    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }

    public Users save(Users user) {
        return userRepository.save(user);
    }
}
