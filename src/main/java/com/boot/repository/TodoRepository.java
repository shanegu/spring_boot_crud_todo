package com.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
