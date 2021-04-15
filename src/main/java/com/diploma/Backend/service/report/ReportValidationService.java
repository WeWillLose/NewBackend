package com.diploma.Backend.service.report;


import com.diploma.Backend.model.Report;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface ReportValidationService {
    List<String> validateReport(Report report);

    List<String> validateReportData(JsonNode data);
}
