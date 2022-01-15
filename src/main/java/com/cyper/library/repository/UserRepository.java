package com.cyper.library.repository;

import com.cyper.library.model.User;

public interface UserRepository extends BaseRepository<User, Long> {
  User findByUsername(String username);

}
