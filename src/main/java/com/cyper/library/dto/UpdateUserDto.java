package com.cyper.library.dto;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

public class UpdateUserDto {
  @NotNull
  @Schema(description = "密码")
  String password;
  @Schema(description = "性别")
  String gender;
  @Schema(description = "手机号码")
  String phone;
  @Schema(description = "角色嘛")
  String roleCode;
  @NotNull
  @Schema(description = "姓名")
  String name;
}
