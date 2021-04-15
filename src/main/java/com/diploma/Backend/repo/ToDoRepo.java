package com.diploma.Backend.repo;

import com.diploma.Backend.model.ToDo;
import com.diploma.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoRepo extends JpaRepository<ToDo,Long> {
    List<ToDo> findByAuthor(User user);
}
