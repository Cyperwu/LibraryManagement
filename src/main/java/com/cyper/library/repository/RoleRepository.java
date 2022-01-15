package com.cyper.library.repository;

import com.cyper.library.model.Role;

public interface RoleRepository extends BaseRepository<Role, Long> {
  Role findOneByCode(String code);
}
