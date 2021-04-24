package com.diploma.Backend.rest.service.mapper.impl;

import com.diploma.Backend.model.ToDo;
import com.diploma.Backend.rest.dto.ToDoDTO;
import com.diploma.Backend.rest.service.mapper.IToDoMapper;
import com.diploma.Backend.rest.service.mapper.IUserMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToDoMapperImpl implements IToDoMapper {
    private final IUserMapper userMapper;

    public ToDoMapperImpl(@Lazy IUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<ToDoDTO> toDoToToDoDTOs(List<ToDo> toDos) {
        return toDos.stream().map(this::toDoToToDoDTO).collect(Collectors.toList());
    }

    @Override
    public ToDoDTO toDoToToDoDTO(ToDo toDo) {
        if (toDo == null) {
            return null;
        } else {
            return ToDoDTO.builder()
                    .author(userMapper.userToUserDTO(toDo.getAuthor()))
                    .description(toDo.getDescription())
                    .text(toDo.getText())
                    .id(toDo.getId())
                    .title(toDo.getTitle())
                    .createdDate((toDo.getCreatedDate()))
                    .createdBy(toDo.getCreatedBy())
                    .build();
        }
    }

    @Override
    public List<ToDo> toDoDTOsToToDo(List<ToDoDTO> toDoDTOs) {
        return toDoDTOs.stream().map(this::toDoDTOToToDo).collect(Collectors.toList());
    }

    @Override
    public ToDo toDoDTOToToDo(ToDoDTO toDoDTO) {
        if (toDoDTO == null) {
            return null;
        } else {
            return ToDo.builder()
                    .author(userMapper.userDTOToUser(toDoDTO.getAuthor()))
                    .description(toDoDTO.getDescription())
                    .id(toDoDTO.getId())
                    .text(toDoDTO.getText())
                    .title(toDoDTO.getTitle())
                    .createdDate((toDoDTO.getCreatedDate()))
                    .createdBy(toDoDTO.getCreatedBy())
                    .build();
        }
    }
}
