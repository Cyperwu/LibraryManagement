package com.cyper.library.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Entity
@Schema(description = "作者")
@Table(name = "author", schema = "library")
public class Author extends BaseModel {
  private String name;
}
