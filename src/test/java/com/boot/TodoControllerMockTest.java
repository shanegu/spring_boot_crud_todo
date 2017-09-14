package com.boot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.boot.controller.TodoController;
import com.boot.model.Todo;
import com.boot.repository.TodoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TodoController.class, secure = false)
public class TodoControllerMockTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TodoRepository todoRepository;

	Todo tdStub = new Todo(1L, "Spring", "DI", "2017-07-17", 0, true);

	@Test
	public void testTodoGet() throws Exception {
		Mockito.when(todoRepository.findOne(1l)).thenReturn(tdStub);

		// execute
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/todos/" + "{id}", new Long(1))
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(todoRepository).findOne(1l);

		// String expected =
		// "{id:1,name:Spring,description:DI,dateCreated:2017-07-17,priority:0,completed:true}";
		String expected = "{id:1,name:Spring,description:DI}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}

	@Test
	public void testTodoList() throws Exception {
		List<Todo> todos = new ArrayList<>();
		todos.add(tdStub);
		Mockito.when(todoRepository.findAll()).thenReturn(todos);

		// execute
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/todos/").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(todoRepository).findAll();
		System.out.println(result.getResponse().getContentAsString());

	}

	@Test
	public void testTodoCreate() throws Exception {
		Todo todo = new Todo(2L, "Spring Boot", "DI Manager", "2017-07-19", 1, true);
		Mockito.when(todoRepository.saveAndFlush(todo)).thenReturn(todo);

		// Use Jackson 2.x to convert Java object to / from JSON
		ObjectMapper mapper = new ObjectMapper();
		String convertTodoJson = mapper.writeValueAsString(todo);
		// String exampleTodoJson = "{\"id\":\"1\",\"name\":\"Spring Boot\",
		// \"description\":\"DI\"}";

		// Send todo as body to /api/v1/todos
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/todos").accept(MediaType.APPLICATION_JSON)
				.content(convertTodoJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		// verify that service method was called once
		verify(todoRepository).saveAndFlush(any((Todo.class)));
		System.out.println(response.getContentAsString());

		Todo todoResult = mapper.readValue(response.getContentAsString(), Todo.class);
		assertNotNull(todoResult);
		assertEquals(2l, todoResult.getId().longValue());

	}

}
