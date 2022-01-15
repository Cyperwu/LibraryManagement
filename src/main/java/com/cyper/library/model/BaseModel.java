package com.cyper.library.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@MappedSuperclass
@Data
public abstract class BaseModel implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @JsonIgnore
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @JsonIgnore
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @PrePersist
  public void preInsert() {
    setCreatedAt(LocalDateTime.now());
  }

  @PreUpdate
  public void preUpdate() {
    setUpdatedAt(LocalDateTime.now());
  }

}