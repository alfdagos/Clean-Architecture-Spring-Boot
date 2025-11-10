-- Initial schema for users and todos
CREATE TABLE IF NOT EXISTS users (
  id bigserial PRIMARY KEY,
  email varchar(320) NOT NULL UNIQUE,
  password_hash varchar(200) NOT NULL,
  first_name varchar(200),
  last_name varchar(200)
);

CREATE TABLE IF NOT EXISTS todos (
  id bigserial PRIMARY KEY,
  title varchar(200) NOT NULL,
  description text,
  priority varchar(20),
  completed boolean NOT NULL DEFAULT false,
  user_id bigint,
  due_date timestamp,
  created_at timestamp,
  completed_at timestamp
);

CREATE TABLE IF NOT EXISTS todo_labels (
  todo_id bigint NOT NULL,
  label varchar(400),
  CONSTRAINT fk_todo FOREIGN KEY(todo_id) REFERENCES todos(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS permissions (
  id bigserial PRIMARY KEY,
  user_id bigint NOT NULL,
  permission varchar(400) NOT NULL
);
