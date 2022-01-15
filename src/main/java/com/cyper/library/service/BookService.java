package com.cyper.library.service;

import com.cyper.library.dto.CreateBookDto;
import com.cyper.library.exception.AuthorNotFoundException;
import com.cyper.library.exception.BookNotFoundException;
import com.cyper.library.mapper.BookMapper;
import com.cyper.library.model.Author;
import com.cyper.library.model.Book;
import com.cyper.library.repository.AuthorRepository;
import com.cyper.library.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class BookService {
  @Autowired
  BookRepository repository;

  @Autowired
  AuthorRepository authorRepository;

  @Autowired
  BookMapper mapper;

  public Page<Book> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public Page<Book> findAllByNameContains(String name, Pageable pageable) {
    return repository.findAllByNameContaining(name, pageable);
  }

  public Page<Book> findAllByAuthor(Long authorId, Pageable pageable) {
    return repository.findAllByAuthorId(authorId, pageable);
  }

  public Book findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  @Secured("ROLE_admin")
  public void deleteById(Long id) {
    repository.deleteById(id);
  }

  public Book update(Long id, CreateBookDto dto) {
    Book book = repository.findById(id).orElseThrow(BookNotFoundException::new);
    mapper.mapBookFromCreateBookDto(dto, book);
    if (dto.getAuthorId() != null) {
      Author author = authorRepository.findById(dto.getAuthorId()).orElseThrow(AuthorNotFoundException::new);
      book.setAuthor(author);
    }
    return repository.save(book);
  }

  public Book create(CreateBookDto dto) {
    Book book = new Book();
    mapper.mapBookFromCreateBookDto(dto, book);
    Long authorId = dto.getAuthorId();
    Author author = authorRepository.findById(authorId).orElseThrow(AuthorNotFoundException::new);
    book.setAuthor(author);
    return repository.save(book);
  }
}
