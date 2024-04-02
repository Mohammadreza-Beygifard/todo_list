package com.example.demo.services;

import com.example.demo.models.RoleEntity;
import com.example.demo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

  @Autowired private RoleRepository roleRepository;

  public RoleEntity findByName(String name) {

    return roleRepository
        .findByName(name)
        .orElseThrow(() -> new RuntimeException("Role not found"));
  }
}
