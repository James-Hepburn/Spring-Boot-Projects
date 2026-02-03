package com.example.todolistapi.service;

import com.example.todolistapi.model.*;
import com.example.todolistapi.repository.TodoRepository;
import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Getter
@AllArgsConstructor
@Service
public class TodoService {
    private TodoRepository repo;

    public Todo createTodo (User user, String title, String description) {
        Todo todo = new Todo (title, description);
        todo.setUser (user);
        user.getTodos ().add (todo);
        return this.repo.save (todo);
    }

    public Todo updateTodo (User user, Long todoId, String title, String description) {
        Optional <Todo> todoOptional = getTodoById (user, todoId);

        if (todoOptional.isPresent ()) {
            Todo todo = todoOptional.get ();

            todo.setTitle (title);
            todo.setDescription (description);

            this.repo.save (todo);
            return todo;
        }

        throw new RuntimeException ("Todo not found or unauthorized access.");
    }

    public void deleteTodo (User user, Long todoId) {
        Optional <Todo> todoOptional = this.repo.findByIdAndUser (todoId, user);

        if (todoOptional.isPresent ()) {
            Todo todo = todoOptional.get ();
            this.repo.delete (todo);
        } else {
            throw new RuntimeException ("Todo not found or unauthorized access.");
        }
    }

    public Page <Todo> getTodos (User user, int page, int size) {
        Pageable pageable = PageRequest.of (page - 1, size);
        return this.repo.findAllByUser (user, pageable);
    }

    public Optional <Todo> getTodoById (User user, Long todoId) {
        return this.repo.findByIdAndUser (todoId, user);
    }
}
