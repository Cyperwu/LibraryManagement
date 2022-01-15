package com.cyper.library.repository;

import com.cyper.library.model.Author;
import com.cyper.library.model.Book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepository extends BaseRepository<Book, Long> {
  public Page<Book> findAllByNameContaining(String name, Pageable pageable);

  public Page<Book> findAllByAuthorId(Long authorId, Pageable pageable);
}
