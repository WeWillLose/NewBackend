package com.diploma.Backend.repo;

import com.diploma.Backend.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepo extends JpaRepository<Report,Long> {
    List<Report> findAllByAuthorId(long id);
}
