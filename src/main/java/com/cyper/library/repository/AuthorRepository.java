package com.cyper.library.repository;

import com.cyper.library.model.Author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorRepository extends BaseRepository<Author, Long> {
  Page<Author> findAllByName(String name, Pageable pageable);
}
