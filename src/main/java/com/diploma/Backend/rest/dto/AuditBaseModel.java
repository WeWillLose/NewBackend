package com.diploma.Backend.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class AuditBaseModel {
    private Instant createdDate;
    private String createdBy;
}
