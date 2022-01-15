package com.cyper.library.service;

import com.cyper.library.dto.CreateAuthorDto;
import com.cyper.library.exception.AuthorNotFoundException;
import com.cyper.library.mapper.AuthorMapper;
import com.cyper.library.model.Author;
import com.cyper.library.repository.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
  @Autowired
  AuthorRepository repository;

  @Autowired
  AuthorMapper mapper;

  public Page<Author> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public Author findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  @Secured("ROLE_admin")
  public Author createAuthor(CreateAuthorDto dto) {
    Author author = new Author();
    mapper.mapAuthorFromCreateAuthorDto(dto, author);
    return repository.save(author);
  }

  @Secured("ROLE_admin")
  public Author updateAuthor(Long id, CreateAuthorDto dto) {
    Author author = repository.findById(id).orElseThrow(AuthorNotFoundException::new);
    mapper.mapAuthorFromCreateAuthorDto(dto, author);
    return repository.save(author);
  }

  @Secured("ROLE_admin")
  public void deleteAuthor(Long id) {
    repository.deleteById(id);
  }
}
