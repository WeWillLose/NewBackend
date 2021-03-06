package com.diploma.Backend.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class ToDoDTO extends AuditBaseModel {

    private Long id;

    private String title;

    private String description;

    private String text;

    private UserDTO author;
}
