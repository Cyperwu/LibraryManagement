package com.cyper.library.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Entity
@Schema(description = "角色")
@Table(name = "role", schema = "library")
public class Role extends BaseModel {
  private String name;

  private String code;
}
