package com.diploma.Backend.service.report.impl;

import com.diploma.Backend.model.EReportStatus;
import com.diploma.Backend.model.Report;
import com.diploma.Backend.model.User;
import com.diploma.Backend.repo.ReportRepo;
import com.diploma.Backend.rest.dto.InputStreamResourceDTO;
import com.diploma.Backend.rest.exception.impl.ForbiddenExceptionImpl;
import com.diploma.Backend.rest.exception.impl.ReportNotFoundExceptionImpl;
import com.diploma.Backend.rest.exception.impl.UserNotFoundExceptionImpl;
import com.diploma.Backend.rest.exception.impl.ValidationExceptionImpl;
import com.diploma.Backend.service.docx.report.ReportDocxService;
import com.diploma.Backend.service.report.ReportService;
import com.diploma.Backend.service.report.ReportValidationService;
import com.diploma.Backend.service.user.UserService;
import com.diploma.Backend.utils.FileNameUtils;
import com.diploma.Backend.utils.SecurityUtils;
import com.diploma.Backend.utils.UserUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final ReportRepo reportRepo;

    private final UserService userService;

    private final UserUtils userUtils;

    private final ReportDocxService reportDocxService;

    private final ReportValidationService reportValidationService;

    private final SecurityUtils securityUtils;

    public ReportServiceImpl(ReportRepo reportRepo, @Lazy UserService userService,
                             @Lazy UserUtils userUtils, @Lazy ReportDocxService reportDocxService,
                             ReportValidationService reportValidationService, SecurityUtils securityUtils) {
        this.reportRepo = reportRepo;
        this.userService = userService;
        this.userUtils = userUtils;
        this.reportDocxService = reportDocxService;
        this.reportValidationService = reportValidationService;
        this.securityUtils = securityUtils;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Report> findByReportId(long reportId) {
        return reportRepo.findById(reportId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(long reportId) {
        return reportRepo.existsById(reportId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Report> findAll() {
        return reportRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Report> findAllByAuthorId(long authorId)  {
        if (!userService.existsById(authorId)) {
            throw new UserNotFoundExceptionImpl(authorId);
        }
        return reportRepo.findAllByAuthorId(authorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Report> findFollowersReports(long chairmanId) {
        List<User> followers = userService.findFollowersByChairmanId(chairmanId);

        if (followers == null) {
            throw new UserNotFoundExceptionImpl(chairmanId);
        }

        List<Report> reports = new ArrayList<>();

        for (User follower : followers) {
            reports.addAll(findAllByAuthorId(follower.getId()));
        }
        return reports;

    }

    @Override
    @Transactional
    public Report updateReportData(long reportId, @NonNull Report updatedReport){
        Report report = findByReportId(reportId).orElse(null);

        if (report == null) {
            throw new ReportNotFoundExceptionImpl(reportId);
        }

        report.setData(updatedReport.getData());

        return reportRepo.save(report);
    }

    @Override
    @Transactional
    public Report saveReport(@NonNull Report report, long authorId){
        User author = userService.findById(authorId).orElse(null);

        if (author == null) {
            throw new UserNotFoundExceptionImpl(authorId);
        }

        report.setReportName(FileNameUtils.generateRandomNameIfEmpty(report.getReportName()));

        List<String> errors = reportValidationService.validateReport(report);
        if (errors != null) {
            if (!errors.isEmpty()) {
                throw new ValidationExceptionImpl(errors.get(0));
            }
        }

        if (author.getChairman() != null && report.getData().get("META").get("chairmanFIO") == null) {
            ((ObjectNode) report.getData().get("META")).put("chairmanFIO",
                userUtils.getFioFromUser(author)
            );
        }

        if (report.getData().get("META").get("fioShort") == null) {
            ((ObjectNode) report.getData().get("META")).put("fioShort",
                userUtils.getShortFioFromUser(author)
            );
        }

        report.setStatus(EReportStatus.UNCHECKED);
        report.setAuthor(author);

        return reportRepo.save(report);

    }
    @Override
    @Transactional
    public Report updateReportStatus(long reportId, @NonNull EReportStatus reportStatus) {
        Report report = findByReportId(reportId).orElse(null);
        if (report == null) {
            throw new ReportNotFoundExceptionImpl(reportId);
        }
        report.setStatus(reportStatus);
        return reportRepo.save(report);

    }

    @Override
    @Transactional
    public void deleteReport(long id){
        Report report = findByReportId(id).orElse(null);
        if (report == null) {
            throw new ReportNotFoundExceptionImpl(id);
        }
        if(!securityUtils.isCurrentUserCanEditReportIfIsOwnerAndReportStatusUNCHECKEDOrIsOwnerChairmanOrAdmin(report)){
        throw new ForbiddenExceptionImpl();
        }
        reportRepo.delete(report);
    }

    @Override
    @Transactional(readOnly = true)
    public InputStreamResourceDTO generateReportDocx(long reportId) {

        Report report = findByReportId(reportId).orElse(null);

        if (report == null) {
            throw new ReportNotFoundExceptionImpl(reportId);
        }
        String reportFileName = FileNameUtils.getReportFileNameOrDefault(report.getReportName());
        if (report.getData() == null) {
            log.error("IN generateReportDocx report.data is null, report: {}", report);
            throw new NullPointerException("IN generateReportDocx report.data is null");
        }
        return new InputStreamResourceDTO(reportDocxService.getReportDocxInputStreamResourceByReportData(report.getData()), reportFileName);
    }

}
