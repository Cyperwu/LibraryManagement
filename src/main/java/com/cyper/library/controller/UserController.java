package com.cyper.library.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import com.cyper.library.dto.CreateUserDto;
import com.cyper.library.dto.ResetPasswordDto;
import com.cyper.library.dto.SignInDto;
import com.cyper.library.dto.UpdateUserDto;
import com.cyper.library.model.User;
import com.cyper.library.service.UserService;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "用户")
@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserService service;

  @GetMapping
  @Operation(summary = "查询用户列表")
  public Page<User> findAll(@ParameterObject Pageable pageable) {
    return service.findAll(pageable);
  }

  @GetMapping("/{id}")
  public User findById(@PathVariable Long id) {
    return service.findById(id);
  }

  @PostMapping("/signin")
  @Operation(summary = "登录")
  public String signin(
      @RequestBody SignInDto dto) {
    String username = dto.getUsername();
    String password = dto.getPassword();
    return service.signin(username, password);
  }

  @PostMapping("/register")
  @Operation(summary = "创建用户")
  public User register(@Parameter @RequestBody CreateUserDto createUserDto) {
    User persistedUser = service.createUser(createUserDto);
    return persistedUser;
  }

  @PutMapping
  @Operation(summary = "编辑自己的用户资料")
  public User updateUser(@Parameter(hidden = true) Principal principal,
      @Parameter @RequestBody UpdateUserDto updateUserDto) {
    String username = principal.getName();
    return service.updateUser(username, updateUserDto);
  }

  @PostMapping("/{id}/reset_password")
  @Operation(summary = "管理员使用，重置目标用户密码")
  @Secured("ROLE_admin")
  public String resetPassword(@PathVariable Long id,
      @RequestBody ResetPasswordDto dto) {
    service.resetPassword(id, dto.getOldPassword(), dto.getNewPassword());
    return "修改成功";
  }

  @PutMapping("/reset_password")
  @Operation(summary = "重置自己密码")
  public String resetPassword(Authentication authentication,
      @RequestBody ResetPasswordDto dto) {
    Long id = ((User) authentication.getPrincipal()).getId();
    service.resetPassword(id, dto.getOldPassword(), dto.getNewPassword());
    return "修改成功";
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "删除用户")
  public String deleteUser(@PathVariable Long id) {
    service.deleteUser(id);
    return "删除成功";
  }

  @GetMapping("/refresh_token")
  @Operation(summary = "更新token")
  public String refreshToken(@Parameter(hidden = true) Principal principal) {
    String username = principal.getName();
    return service.refresh(username);
  }

}
