CREATE SCHEMA IF NOT EXISTS library;

CREATE TABLE library.user (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(32),
  gender VARCHAR(1),
  phone VARCHAR(11),
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(255),
  created_at TIMESTAMP,
  fk_role_id BIGINT,
  updated_at TIMESTAMP,
  PRIMARY KEY(id)
);

CREATE TABLE library.role (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(32),
  code VARCHAR(32),
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  PRIMARY KEY(id)
);

CREATE TABLE library.book (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(32),
  code VARCHAR(32),
  fk_author_id BIGINT,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  PRIMARY KEY(id)
);

CREATE TABLE library.author (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(32),
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  PRIMARY KEY(id)
);

INSERT INTO
  library.role (id, name, code)
VALUES
  (1, '管理员', 'ROLE_admin'),
  (2, '普通用户', 'ROLE_user');

INSERT INTO
  library.user (id, name, username, password, fk_role_id)
VALUES
  (
    1,
    '管理员',
    'admin',
    '$2a$10$4Mjxrc0KJYpYTcV3UZ5FLe2CHANYzlda3Q9Gd7H/lG8nwWN01aX1W',
    1
  );

INSERT INTO
  library.author (name)
VALUES
  ('Adam'),
  ('Ben'),
  ('Clint');

INSERT INTO
  library.book (name, code, fk_author_id)
VALUES
  ('Book1', 'b11111', 1),
  ('Book2', 'b11112', 1),
  ('Book3', 'b11113', 1),
  ('Book4', 'b11114', 2);