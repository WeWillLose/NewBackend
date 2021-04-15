package com.diploma.Backend.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ToDoDTO {

    private Long id;

    private String title;

    private String description;

    private String text;

    private UserDTO author;
}
