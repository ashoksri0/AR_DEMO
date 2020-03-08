package com.p3.poc.demo.ar.user.controller;

import com.p3.poc.demo.ar.customexceptions.UserNotFoundException;
import com.p3.poc.demo.ar.user.entity.Users;
import com.p3.poc.demo.ar.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public Iterable<Users> getUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public Users getUser(@PathVariable Long id) {
        return userService.findUserById(id).orElseThrow(UserNotFoundException::new);
    }

    @PostMapping("/")
    public Users addUser(@RequestBody Users user) {
        return userService.saveUser(user);
    }

    @PutMapping("/{id}")
    public Users updateUser(@PathVariable Long id, @RequestBody Users user) {
        return userService.updateUserById(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

}
