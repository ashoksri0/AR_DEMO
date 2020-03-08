package com.p3.poc.demo.ar.user.service;


import com.p3.poc.demo.ar.customexceptions.UserNotFoundException;
import com.p3.poc.demo.ar.user.entity.Users;
import com.p3.poc.demo.ar.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public List<Users> findUsersByIdLikeOrFirstNameLikeOrLastNameLike(String search) {
        if (search.matches("\\d+")) {
            return userRepository.findUsersByIdOrFirstNameContainsOrLastNameContainsAllIgnoreCase(Long.valueOf(search),search,search);
        }
        return userRepository.findUsersByFirstNameContainsOrLastNameContainsAllIgnoreCase(search ,search);
    }

    public List<Users> findAllByUserIdOrFirstNameOrLastName(String firstName, String lastName, Long id) {

        List<Users> usersList = null;

        if(Objects.nonNull(firstName) && Objects.nonNull(lastName) && Objects.nonNull(id))
        {
            usersList= userRepository.findUsersByFirstNameAndLastNameAndIdAllIgnoreCase(firstName, lastName, id);
        }
        else if (Objects.nonNull(firstName) && Objects.nonNull(lastName)) {
            usersList= userRepository.findUsersByFirstNameAndLastNameAllIgnoreCase(firstName,lastName);
        }
        else if (Objects.nonNull(firstName) && Objects.nonNull(id)) {
            usersList= userRepository.findUsersByFirstNameAndIdAllIgnoreCase(firstName,id);
        }
        else if (Objects.nonNull(lastName) && Objects.nonNull(id)) {
            usersList = userRepository.findUsersByLastNameAndIdAllIgnoreCase(lastName, id);
        }
        else if (Objects.nonNull(firstName)) {
            usersList= userRepository.findUsersByFirstNameAllIgnoreCase(firstName);
        }
        else if (Objects.nonNull(lastName)) {
            usersList= userRepository.findUsersByLastNameAllIgnoreCase(lastName);
        }
        else if (Objects.nonNull(id)) {
            Optional<Users> optionalUsers = userRepository.findById(id);
            if (optionalUsers.isPresent()) {
                usersList=List.of(optionalUsers.get());
            }
        }
        return usersList;
    }

    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }

    public Users save(Users user) {
        return userRepository.save(user);
    }
}
