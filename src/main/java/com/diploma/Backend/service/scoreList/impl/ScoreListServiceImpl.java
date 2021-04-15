package com.diploma.Backend.service.scoreList.impl;

import com.diploma.Backend.model.Report;
import com.diploma.Backend.rest.dto.InputStreamResourceDTO;
import com.diploma.Backend.rest.exception.impl.ReportNotFoundExceptionImpl;
import com.diploma.Backend.service.docx.scoreList.ScoreListDocxService;
import com.diploma.Backend.service.report.ReportService;
import com.diploma.Backend.service.scoreList.ScoreListService;
import com.diploma.Backend.utils.FileNameUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScoreListServiceImpl implements ScoreListService {

    private final ReportService reportService;

    private final ScoreListDocxService scoreListDocxService;

    @Override
    public InputStreamResourceDTO generateScoreListByReportId(long reportId)  {
        Report byReportId = reportService.findByReportId(reportId).orElse(null);
        if (byReportId != null) {
            return new InputStreamResourceDTO(scoreListDocxService.getScoreListInputStreamByReport(byReportId),FileNameUtils.getScoreListFileNameOrDefault(byReportId.getReportName()));
        }else{
            throw new ReportNotFoundExceptionImpl(reportId);
        }
    }

}
