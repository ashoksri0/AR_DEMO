package com.p3.poc.demo.ar.user.controller;

import com.p3.poc.demo.ar.customexceptions.UserNotFoundException;
import com.p3.poc.demo.ar.user.entity.Users;
import com.p3.poc.demo.ar.user.repository.UserRepository;
import com.p3.poc.demo.ar.user.service.UserService;
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
    public Iterable<Users> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public Users getStudent(@PathVariable Long id) {
        return userService.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @PostMapping("/")
    public Users addStudent(@RequestBody Users user) {
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public Users updateStudent(@PathVariable Long id, @RequestBody Users user) {

        Users userToUpdate = repository.findById(id).orElseThrow(UserNotFoundException::new);
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
