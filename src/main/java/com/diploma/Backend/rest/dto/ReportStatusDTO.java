package com.diploma.Backend.rest.dto;

import com.diploma.Backend.model.EReportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportStatusDTO {
    @NonNull
    private EReportStatus status;
}
