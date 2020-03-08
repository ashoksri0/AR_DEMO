package com.p3.poc.demo.ar.user.controller;

import com.p3.poc.demo.ar.customexceptions.UserNotFoundException;
import com.p3.poc.demo.ar.user.entity.User;
import com.p3.poc.demo.ar.user.repository.UserRepository;
import com.p3.poc.demo.ar.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository repository;

    @GetMapping("/")
    public Iterable<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getStudent(@PathVariable Long id) {
        return userService.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @PostMapping("/")
    public User addStudent(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public User updateStudent(@PathVariable Long id, @RequestBody User user) {
        User userToUpdate = repository.findById(id).orElseThrow(UserNotFoundException::new);
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setDob(user.getDob());
        userToUpdate.setDoj(user.getDoj());
        userToUpdate.setEmail(user.getEmail());
        return repository.save(userToUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        repository.findById(id).orElseThrow(UserNotFoundException::new);
        repository.deleteById(id);
    }
}
