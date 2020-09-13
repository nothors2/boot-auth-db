package com.example.demo.security.service;

import com.example.demo.domain.model.Auth;

import java.util.List;

/**
 * author:james
 * day:2020-09-13
 */
public interface ResourceMetaService {
    void findAllResources();

    List<Auth> getAuths();
}