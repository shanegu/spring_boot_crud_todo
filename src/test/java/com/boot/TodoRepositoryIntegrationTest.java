package com.boot;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.boot.model.Todo;
import com.boot.repository.TodoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class TodoRepositoryIntegrationTest {

	@Autowired
	private TodoRepository todoRepository;

	@Test
	public void testFindAll() {
		List<Todo> todos = todoRepository.findAll();
		assertThat(todos.size(), is(greaterThanOrEqualTo(0)));
	}
	
}
