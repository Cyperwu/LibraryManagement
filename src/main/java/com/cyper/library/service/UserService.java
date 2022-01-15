package com.cyper.library.service;

import com.cyper.library.dto.CreateUserDto;
import com.cyper.library.dto.UpdateUserDto;
import com.cyper.library.exception.ServiceFailureException;
import com.cyper.library.jwt.JwtTokenProvider;
import com.cyper.library.mapper.UserMapper;
import com.cyper.library.model.Role;
import com.cyper.library.model.User;
import com.cyper.library.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserDetailsService {
  @Autowired
  private UserRepository repository;

  @Autowired
  private RoleService roleService;

  @Autowired
  private UserMapper mapper;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  @Lazy
  private AuthenticationManager authenticationManager;

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  @Override
  public User loadUserByUsername(String username) {
    return repository.findByUsername(username);
  }

  public Page<User> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public User findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  public User createUser(CreateUserDto createUserDto) {
    String roleCode = createUserDto.getRoleCode();
    Role role = roleService.findOneByCode(roleCode);
    if (role == null) {
      role = roleService.findOneByCode("ROLE_user");
    }

    User user = new User();
    mapper.mapUserFromCreateUserDto(createUserDto, user);
    user.setPassword(encoder.encode(user.getPassword()));
    user.setRole(role);

    try {
      repository.save(user);
    } catch (org.springframework.dao.DataIntegrityViolationException e) {
      log.error("eeeeeee", e);
      throw new ServiceFailureException("用户名已存在", 400);
    }
    return user;
  }

  public String signin(String username, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      return jwtTokenProvider.createTokenByUsername(username);
    } catch (AuthenticationException e) {
      throw new ServiceFailureException("登录失败", 400);
    }
  }

  public User updateUser(String username, UpdateUserDto updateUserDto) {
    User user = repository.findByUsername(username);
    mapper.mapUserFromUpdateUserDto(updateUserDto, user);
    repository.save(user);
    return user;
  }

  public String refresh(String username) {
    return jwtTokenProvider.createTokenByUsername(username);
  }

  public void deleteUser(Long id) {
    User user = findById(id);
    if (user != null) {
      repository.delete(user);
    }
  }

  public void resetPassword(Long id, String oldPassword, String newPassword) {
    User user = repository.findById(id).orElse(null);
    if (user != null) {
      Boolean match = encoder.matches(oldPassword, user.getPassword());
      if (!match) {
        throw new ServiceFailureException("密码错误");
      }
      user.setPassword(encoder.encode(newPassword));
      repository.save(user);
    }
  }

}
