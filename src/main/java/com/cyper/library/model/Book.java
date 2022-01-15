package com.cyper.library.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Entity
@Schema(description = "书")
@Table(name = "book", schema = "library")
public class Book extends BaseModel {
  private String name;

  private String code;

  @ManyToOne
  @JoinColumn(name = "fk_author_id")
  private Author author;
}