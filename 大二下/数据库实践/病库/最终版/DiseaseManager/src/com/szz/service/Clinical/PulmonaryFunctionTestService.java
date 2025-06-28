package com.szz.service.Clinical;

import com.szz.dao.Clinical.PulmonaryFunctionTestDao;
import com.szz.model.Clinical.PulmonaryFunctionTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PulmonaryFunctionTestService {
    private final PulmonaryFunctionTestDao pulmonaryFunctionTestDao;

    public PulmonaryFunctionTestService() {
        this.pulmonaryFunctionTestDao = new PulmonaryFunctionTestDao();
    }

    public List<PulmonaryFunctionTest> getPulmonaryFunctionTestsByVisitId(int visitId) {
        try {
            return pulmonaryFunctionTestDao.getPulmonaryFunctionTestsByVisitId(visitId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}