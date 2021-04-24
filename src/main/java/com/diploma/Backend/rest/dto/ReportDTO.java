package com.diploma.Backend.rest.dto;

import com.diploma.Backend.model.EReportStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class ReportDTO extends AuditBaseModel {

    private Long id;
    @NotNull
    private JsonNode data;

    private EReportStatus status;



    private UserDTO author;

    private String reportName;
}
