package com.p3.poc.demo.ar.preload.data.service;

import com.p3.poc.demo.ar.user.entity.Users;
import com.p3.poc.demo.ar.user.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ashok Kumar N
 * on 09/03/20.
 */

@Service
public class PreLoadService {
    @Autowired
    EntityManager entityManager;
    @Autowired
    UserRepository userRepository;
    public void loadUser()
    {
        Users users=new Users();
    }

}
