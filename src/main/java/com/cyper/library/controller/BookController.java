package com.cyper.library.controller;

import com.cyper.library.dto.CreateBookDto;
import com.cyper.library.model.Book;
import com.cyper.library.service.AuthorService;
import com.cyper.library.service.BookService;

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

@Tag(name = "书籍")
@RestController
@RequestMapping("/books")
public class BookController {
  @Autowired
  BookService service;

  @Autowired
  AuthorService authorService;

  @PostMapping
  @Operation(summary = "创建书籍")
  @Secured("ROLE_admin")
  public Book create(@RequestBody CreateBookDto dto) {
    return service.create(dto);
  }

  @GetMapping
  @Operation(summary = "查询所有书籍")
  public Page<Book> findAll(@ParameterObject Pageable pageable) {
    return service.findAll(pageable);
  }

  @GetMapping("/name")
  @Operation(summary = "按名字模糊查询")
  public Page<Book> findAllByNameContains(@Parameter(description = "书名") String name,
      @ParameterObject Pageable pageable) {
    return service.findAllByNameContains(name, pageable);
  }

  @GetMapping("/written_by/{authorId}")
  @Operation(summary = "按作者名查询")
  public Page<Book> findAllByAuthor(@PathVariable @Parameter(name = "authorId", description = "作者id") Long authorId,
      @ParameterObject Pageable pageable) {
    return service.findAllByAuthor(authorId, pageable);
  }

  @PutMapping("/{id}")
  @Operation(summary = "更新书籍")
  @Secured("ROLE_admin")
  public Book update(@PathVariable @Parameter(description = "书籍id") Long id, @RequestBody CreateBookDto dto) {
    return service.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "删除")
  @Secured("ROLE_admin")
  public String delete(@PathVariable @Parameter(description = "书籍id") Long id) {
    service.deleteById(id);
    return "删除成功";
  }
}
