package com.p3.poc.demo.ar.user.controller;

import com.p3.poc.demo.ar.MapperUtils;
import com.p3.poc.demo.ar.utils.exceptions.UserNotFoundException;
import com.p3.poc.demo.ar.user.entity.Users;
import com.p3.poc.demo.ar.user.model.UsersModel;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository repository;

    @Autowired
    MapperUtils mapperUtils;

    @GetMapping("/")
    public List<Users> getAllUsers() {
        return userService.findAll();
    }


    @GetMapping("/wild/{search}")
    public List<UsersModel> getUsers(@PathVariable String search) {
        List<Users> users = userService.findUsersByIdLikeOrFirstNameLikeOrLastNameLike(search);
        return users.stream().map(users1 -> {
            return   UsersModel.builder().dob(users1.getDob()).email(users1.getEmail()).doj(users1.getDoj()).id(users1.getId()).firstName(users1.getFirstName()).lastName(users1.getLastName()).invoiceCount(users1.getInvoices().size()).build();
        }).collect(Collectors.toList());
    }

    @GetMapping("/advance-search")
    public List<UsersModel> getUsers(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName,
                                     @RequestParam(required = false) Long id) {
        List<Users> usersList = userService.findAllByUserIdOrFirstNameOrLastName(firstName, lastName, id);
        return usersList.stream().map(users1 -> UsersModel.builder().dob(users1.getDob()).email(users1.getEmail()).doj(users1.getDoj()).id(users1.getId()).firstName(users1.getFirstName()).lastName(users1.getLastName()).invoiceCount(users1.getInvoices().size()).build()).collect(Collectors.toList());
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
