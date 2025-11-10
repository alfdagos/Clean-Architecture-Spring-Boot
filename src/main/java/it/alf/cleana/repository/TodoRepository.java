package it.alf.cleana.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.alf.cleana.domain.TodoItem;

public interface TodoRepository extends JpaRepository<TodoItem, Long> {
}
