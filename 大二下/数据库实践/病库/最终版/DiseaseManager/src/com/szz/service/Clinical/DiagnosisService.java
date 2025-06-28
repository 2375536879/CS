package com.szz.service.Clinical;

import com.szz.dao.Clinical.DiagnosisDao;
import com.szz.model.Clinical.Diagnosis;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiagnosisService {
    private final DiagnosisDao diagnosisDao;

    public DiagnosisService() {
        this.diagnosisDao = new DiagnosisDao();
    }

    public List<Diagnosis> getDiagnosesByVisitId(int visitId) {
        try {
            return diagnosisDao.getDiagnosesByVisitId(visitId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}