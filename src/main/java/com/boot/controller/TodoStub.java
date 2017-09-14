package com.boot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boot.model.Todo;

public class TodoStub {
	private static Map<Long, Todo> todos = new HashMap<Long, Todo>();
	private static Long idIndex = 2L;

	//populate initial todos
	static {
		Todo a = new Todo(1L, "Spring", "Dependency injection framework for Java, feature-rick libraries", "2017-07-17", 0, true);
		todos.put(1L, a);
		Todo b = new Todo(2L, "Spring Boot", "Dependency management with starters, providing auto-configuration", "2017-07-19", 1, true);
		todos.put(2L, b);
		
	}

	public static List<Todo> list() {
		return new ArrayList<Todo>(todos.values());
	}

	public static Todo create(Todo todo) {
		idIndex += idIndex;
		todo.setId(idIndex);
		todos.put(idIndex, todo);
		return todo;
	}

	public static Todo get(Long id) {
		return todos.get(id);
	}

	public static Todo update(Long id, Todo todo) {
		todos.put(id, todo);
		return todo;
	}

	public static Todo delete(Long id) {
		return todos.remove(id);
	}
}
