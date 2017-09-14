package com.boot;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.boot.controller.TodoController;
import com.boot.model.Todo;
import com.boot.repository.TodoRepository;

public class TodoControllerTest {
	@InjectMocks
	private TodoController tc;

	@Mock
	private TodoRepository todoRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testTodoGet() {
    	Todo td = new Todo();
    	td.setId(1l);
    	//mock the database behavior such that when findOne method is called, it will return a stubbed object
		when(todoRepository.findOne(1l)).thenReturn(td); 

		Todo todo = tc.get(1L);

		verify(todoRepository).findOne(1l);
		//System.out.println("checkpoint");

		assertEquals(1l, todo.getId().longValue());	
		//assertThat(todo.getId(), is(1l));
	}

}
