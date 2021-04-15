package com.diploma.Backend.service.scoreList;


import com.diploma.Backend.rest.dto.InputStreamResourceDTO;

public interface ScoreListService {
    InputStreamResourceDTO generateScoreListByReportId(long reportId) ;
}
