package com.cyper.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateAuthorDto {
  @Schema(description = "作者名")
  String name;
}
