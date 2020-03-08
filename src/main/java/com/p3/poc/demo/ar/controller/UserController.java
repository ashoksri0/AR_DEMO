package com.p3.poc.demo.ar.controller;

import com.p3.poc.demo.ar.customexceptions.UserNotFoundException;
import com.p3.poc.demo.ar.model.User;
import com.p3.poc.demo.ar.repo.UserRepository;
import com.p3.poc.demo.ar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
