package com.boot.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boot.model.Todo;
import com.boot.repository.TodoRepository;

@RestController
@RequestMapping("api/v1/")
public class TodoController {
	
	@Autowired
	private TodoRepository todoRepository;

	@RequestMapping(value = "todos", method = RequestMethod.GET)
	public List<Todo> list() {
		return todoRepository.findAll();
	}

	@RequestMapping(value = "todos", method = RequestMethod.POST)
	public Todo create(@RequestBody Todo todo) {
		todoRepository.saveAndFlush(todo);
		return todo;
	}

	@RequestMapping(value = "todos/{id}", method = RequestMethod.GET)
	public Todo get(@PathVariable Long id) {
		return todoRepository.findOne(id);
	}

	@RequestMapping(value = "todos/{id}", method = RequestMethod.PUT)
	public Todo update(@PathVariable Long id, @RequestBody Todo todo) {
		Todo existingTodo = todoRepository.findOne(id);
		BeanUtils.copyProperties(todo, existingTodo);
		return todoRepository.saveAndFlush(existingTodo);
	}

	@RequestMapping(value = "todos/{id}", method = RequestMethod.DELETE)
	public Todo delete(@PathVariable Long id) {
		Todo existingTodo = todoRepository.findOne(id);
		todoRepository.delete(existingTodo);
		return existingTodo;
	}
	
}
