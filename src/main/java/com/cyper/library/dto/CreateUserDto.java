package com.cyper.library.dto;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateUserDto {
  @NotNull
  @Schema(description = "用户名")
  String username;
  @NotNull
  @Schema(description = "密码")
  String password;
  @Schema(description = "性别")
  String gender;
  @Schema(description = "手机号码")
  String phone;
  @Schema(description = "角色码，可不填")
  String roleCode;
  @NotNull
  @Schema(description = "姓名")
  String name;
}
