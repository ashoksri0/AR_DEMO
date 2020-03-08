package com.p3.poc.demo.ar.user.repository;

import com.p3.poc.demo.ar.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    List<Users>  findUsersByIdOrFirstNameContainsOrLastNameContainsAllIgnoreCase(Long id,String firstName,String lastName);
    List<Users>  findUsersByFirstNameContainsOrLastNameContainsAllIgnoreCase(String firstName,String lastName);
    List<Users> findUsersByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);
    List<Users> findUsersByFirstNameAndLastNameAndIdAllIgnoreCase(String firstName, String lastName, Long Id);
    List<Users> findUsersByFirstNameAllIgnoreCase(String firstName);
    List<Users> findUsersByLastNameAllIgnoreCase(String lastName);
    List<Users> findUsersByFirstNameAndIdAllIgnoreCase(String firstName, Long id);
    List<Users> findUsersByLastNameAndIdAllIgnoreCase(String lastName, Long id);
}
