package com.diploma.Backend.service.docx.scoreList;

import com.diploma.Backend.model.Report;
import lombok.NonNull;
import org.springframework.core.io.InputStreamResource;

public interface ScoreListDocxService {

    InputStreamResource getScoreListInputStreamByReport(@NonNull Report report);
}
