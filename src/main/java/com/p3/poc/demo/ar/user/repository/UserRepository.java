package com.p3.poc.demo.ar.user.repository;

import com.p3.poc.demo.ar.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
}
