package com.cyper.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ResetPasswordDto {
  @Schema(description = "旧密码")
  String oldPassword;
  @Schema(description = "新密码")
  String newPassword;
}
