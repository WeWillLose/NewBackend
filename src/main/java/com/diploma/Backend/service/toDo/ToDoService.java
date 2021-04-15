package com.diploma.Backend.service.toDo;


import com.diploma.Backend.model.ToDo;

import java.util.List;

public interface ToDoService {
    List<ToDo> findByAuthorId(long authorId) ;

    ToDo findById(long id);

    ToDo editToDo(long sourceToDoId, ToDo changedToDo) ;

    ToDo addToDo(long authorId, ToDo toDo) ;

    boolean existsById(long id);

    void deleteToDo(long toDoId) ;
}
