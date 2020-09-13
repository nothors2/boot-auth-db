package com.example.demo.security.service;

import com.example.demo.domain.model.Auth;
import com.example.demo.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author:james
 * day:2020-09-13
 */
@Repository
public class ResourceMetaServiceImpl  implements ResourceMetaService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private ApplicationContext applicationContext;

    private List<Auth> auths ;

    @Override
    public void findAllResources() {
        List<Auth> authorities = authRepository.list();

        authorities.stream().forEach(auth -> {
//            log.info("role name {} ", auth.getAuthority());
//            log.info("url {}", auth.getUrl());
        });

        System.out.println(authorities);
        applicationContext.publishEvent(authorities);
        auths = authorities;
    }
    @Override
    public List<Auth> getAuths() {
        if(auths == null) findAllResources();
        return auths;
    }
}