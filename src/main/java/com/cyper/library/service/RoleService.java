package com.cyper.library.service;

import com.cyper.library.model.Role;
import com.cyper.library.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
  @Autowired
  RoleRepository repository;

  Page<Role> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  Role findOneByCode(String code) {
    return repository.findOneByCode(code);
  }

  Role findById(Long id) {
    return repository.findById(id).orElse(null);
  }

}
