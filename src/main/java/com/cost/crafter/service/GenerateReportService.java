package com.cost.crafter.service;

import com.cost.crafter.dal.GenerateReportRepository;
import com.cost.crafter.dto.GenerateReport;

import java.util.List;

public class GenerateReportService {

    private GenerateReportRepository generateReportRepository;

    public GenerateReportService(GenerateReportRepository generateReportRepository) {
        this.generateReportRepository = generateReportRepository;
    }

    public List<GenerateReport> fetchMonthlyReportData(Integer userId, Integer userInputMonth) throws Exception {
        if (userId == null) {
            throw new Exception("User id is required");
        }else if(userInputMonth == null){
            throw new Exception("User input month is required");
        }
        try {
            generateReportRepository = new GenerateReportRepository();
            return generateReportRepository.fetchMonthlyReportData(userId, userInputMonth);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while fetching report data");
        } finally {
            generateReportRepository = null;
        }

    }
}
