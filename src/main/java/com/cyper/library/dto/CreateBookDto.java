package com.cyper.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateBookDto {
  @Schema(description = "书名")
  String name;
  @Schema(description = "书码")
  String code;
  @Schema(description = "作者id")
  Long authorId;
}
