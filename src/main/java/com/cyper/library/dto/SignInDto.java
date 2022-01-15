package com.cyper.library.dto;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SignInDto {
  @NotNull
  @Schema(description = "用户名")
  String username;
  @NotNull
  @Schema(description = "密码")
  String password;
}
