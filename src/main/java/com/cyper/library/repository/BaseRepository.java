package com.cyper.library.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends PagingAndSortingRepository<T, ID> {
  @Override
  @Transactional
  @Query("SELECT e FROM #{#entityName} e WHERE e.id = ?1")
  Optional<T> findById(ID id);

  @Override
  @Transactional
  default boolean existsById(ID id) {
      return findById(id).isPresent();
  }

  @Override
  @Transactional
  @Query("SELECT e FROM #{#entityName} e")
  List<T> findAll();

  @Override
  @Transactional
  @Query("SELECT e FROM #{#entityName} e WHERE e.id in ?1")
  List<T> findAllById(Iterable<ID> ids);

  @Override
  @Transactional
  @Query("select count(e) from #{#entityName} e")
  long count();

  @Override
  @Transactional
List<T> findAll(Sort sort);

  @Override
  @Transactional
Page<T> findAll(Pageable pageable);

}
