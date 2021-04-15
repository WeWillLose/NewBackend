package com.diploma.Backend.rest.controller;


import com.diploma.Backend.model.User;
import com.diploma.Backend.rest.dto.ToDoDTO;
import com.diploma.Backend.rest.service.mapper.IToDoMapper;
import com.diploma.Backend.service.toDo.ToDoService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/v1/toDo")
@Slf4j
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService toDoService;


    private final IToDoMapper toDoMapperService;

    @GetMapping("author/{id}")
    public ResponseEntity<?> getToDos(@PathVariable(name = "id") Long id){
            return ResponseEntity.ok().body(toDoMapperService.toDoToToDoDTOs(toDoService.findByAuthorId(id)));
    }
    @GetMapping("author/current")
    public ResponseEntity<?> getToDoesCurrentUser(@AuthenticationPrincipal User currentUsers){
            return ResponseEntity.ok().body(toDoMapperService.toDoToToDoDTOs(toDoService.findByAuthorId(currentUsers.getId())));
    }

    @PostMapping("")
    public ResponseEntity<?> createToDo(@RequestBody ToDoDTO toDoDTO,@Valid @NotNull @AuthenticationPrincipal User currentUsers){
            return ResponseEntity.ok().body(toDoMapperService.toDoToToDoDTO(toDoService.addToDo(currentUsers.getId(), toDoMapperService.toDoDTOToToDo(toDoDTO))));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteToDo(@PathVariable long id){
            toDoService.deleteToDo(id);
            return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editToDo(@PathVariable(name = "id") long sourceToDoId,@Valid @NotNull @RequestBody ToDoDTO toDoDTO){
            ToDoDTO editedToDoDTO = toDoMapperService.toDoToToDoDTO(toDoService.editToDo(sourceToDoId, toDoMapperService.toDoDTOToToDo(toDoDTO)));
            return ResponseEntity.ok().body(editedToDoDTO);
    }
}
