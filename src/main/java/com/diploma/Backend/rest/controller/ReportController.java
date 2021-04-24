package com.diploma.Backend.rest.controller;



import com.diploma.Backend.model.Report;
import com.diploma.Backend.model.User;
import com.diploma.Backend.rest.dto.InputStreamResourceDTO;
import com.diploma.Backend.rest.dto.ReportDTO;
import com.diploma.Backend.rest.dto.ReportStatusDTO;
import com.diploma.Backend.rest.service.mapper.IReportMapper;
import com.diploma.Backend.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/report")
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    private final ReportService reportService;

    private final IReportMapper reportMapperService;

    @PostMapping("")
    public ResponseEntity<?> saveReport(@RequestBody Report report, @AuthenticationPrincipal User user){
        return   ResponseEntity.ok(reportService.saveReport(report,user.getId()));

    }

    @GetMapping("docx/{id:\\d+}")
    public ResponseEntity<?> getReportDocx(@PathVariable Long id){
        InputStreamResourceDTO inputStreamResource = reportService.generateReportDocx(id);
        HttpHeaders headers = new HttpHeaders();
        String format = String.format("attachment; filename=%s", inputStreamResource.getFileName());
        headers.add(HttpHeaders.CONTENT_DISPOSITION,format);
        return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(inputStreamResource.getInputStreamResource());
    }

    @GetMapping("author/current")
    public ResponseEntity<?> getReportByCurrentUser(@AuthenticationPrincipal User user){
            List<ReportDTO> reportDTOS = reportMapperService.reportToReportDTOs(reportService.findAllByAuthorId(user.getId()));
            return ResponseEntity.ok().body(reportDTOS);
    }

    @GetMapping("followers/{id}")
    public ResponseEntity<?> getReportByChairmanId(@PathVariable(name = "id") Long chairmanID){
            List<ReportDTO> followersReports = reportMapperService.reportToReportDTOs(reportService.findFollowersReports(chairmanID));
            return ResponseEntity.ok().body(followersReports);
    }
    @GetMapping("followers/current")
    public ResponseEntity<?> getReportByChairmanId(@AuthenticationPrincipal User user){
            List<ReportDTO> followersReports = reportMapperService.reportToReportDTOs(reportService.findFollowersReports(user.getId()));
            return ResponseEntity.ok().body(followersReports);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteReportById(@PathVariable Long id){
            reportService.deleteReport(id);
            return ResponseEntity.ok().body(null);
    }

    @GetMapping("author/{id}")
    public ResponseEntity<List<ReportDTO>> getReportByAuthor(@PathVariable Long id){
        return ResponseEntity.ok().body(reportMapperService.reportToReportDTOs(reportService.findAllByAuthorId(id)));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getReportByReportId(@PathVariable(name = "id") Long reportId){
        return ResponseEntity.ok().body(reportMapperService.reportToReportDTO(reportService.findByReportId(reportId).orElse(null)));
    }
    @GetMapping("withoutdata/{id}")
    public ResponseEntity<?> getReportByReportIdWithoutData(@PathVariable(name = "id") Long reportId){
        return ResponseEntity.ok().body(reportMapperService.reportToReportDTOWithoutData(reportService.findByReportId(reportId).orElse(null)));
    }

    @PutMapping("data/{id}")
    public ResponseEntity<?> updateReport(@PathVariable(name="id") Long reportId,@RequestBody ReportDTO reportDTO){
            return ResponseEntity.ok().body(reportMapperService.reportToReportDTO(reportService.updateReportData(reportId,
                    reportMapperService.reportDTOToReport(reportDTO))));
    }
    @PutMapping("status/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CHAIRMAN')")
    public ResponseEntity<?> updateReportStatus(@PathVariable(name="id") Long reportId, @Valid @RequestBody ReportStatusDTO reportStatusDTO){
            return ResponseEntity.ok().body(reportMapperService.reportToReportDTO(reportService.updateReportStatus(reportId,reportStatusDTO.getStatus())));
    }
}
