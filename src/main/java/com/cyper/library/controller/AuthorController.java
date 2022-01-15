package com.cyper.library.controller;

import com.cyper.library.dto.CreateAuthorDto;
import com.cyper.library.model.Author;
import com.cyper.library.service.AuthorService;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "作者")
@RestController
@RequestMapping("/author")
public class AuthorController {
  @Autowired
  AuthorService service;

  @GetMapping
  @Operation(summary = "查询所有作者")
  public Page<Author> findAll(@ParameterObject Pageable pageable) {
    return service.findAll(pageable);
  }

  @PostMapping
  @Operation(summary = "创建作者")
  @Secured("ROLE_admin")
  public Author create(@RequestBody CreateAuthorDto dto) {
    return service.createAuthor(dto);
  }

  @GetMapping("/{id}")
  @Operation(summary = "id查询")
  public Author findById(@PathVariable @Parameter(description = "作者id") Long id) {
    return service.findById(id);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "根据id删除")
  @Secured("ROLE_admin")
  public String deleteById(@PathVariable @Parameter(description = "作者id") Long id) {
    service.deleteAuthor(id);
    return "删除成功";
  }

  @PutMapping("/{id}")
  @Operation(summary = "根据id更新")
  @Secured("ROLE_admin")
  public Author updateById(@PathVariable @Parameter(description = "作者id") Long id, @RequestBody CreateAuthorDto dto) {
    return service.updateAuthor(id, dto);
  }

}
