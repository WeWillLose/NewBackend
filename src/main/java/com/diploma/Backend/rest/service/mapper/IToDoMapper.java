package com.diploma.Backend.rest.service.mapper;

import com.diploma.Backend.model.ToDo;
import com.diploma.Backend.rest.dto.ToDoDTO;

import java.util.List;

public interface IToDoMapper {
    List<ToDoDTO> toDoToToDoDTOs(List<ToDo> toDos);

    ToDoDTO toDoToToDoDTO(ToDo toDo);

    List<ToDo> toDoDTOsToToDo(List<ToDoDTO> toDoDTOs);

    ToDo toDoDTOToToDo(ToDoDTO toDoDTO);
}
