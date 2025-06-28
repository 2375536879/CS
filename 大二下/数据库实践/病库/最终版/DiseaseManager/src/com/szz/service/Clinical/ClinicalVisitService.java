package com.szz.service.Clinical;

import com.szz.dao.Clinical.ClinicalVisitDao;
import com.szz.model.Clinical.ClinicalVisit;

import java.sql.SQLException;
import java.util.List;

public class ClinicalVisitService {
    private ClinicalVisitDao clinicalVisitDao = new ClinicalVisitDao();

    public List<ClinicalVisit> getVisitsByPatientId(int patientId) throws SQLException {
        List<ClinicalVisit> visits = clinicalVisitDao.getVisitsByPatientId(patientId);
        // 为每个就诊记录加载症状和体征信息
        for (ClinicalVisit visit : visits) {
            List<ClinicalVisit.VisitSymptomSign> symptoms = clinicalVisitDao.getVisitSymptomSigns(visit.getId());
        }
        return visits;
    }

    public List<ClinicalVisit.VisitSymptomSign> getVisitSymptomSigns(int visitId) throws SQLException {
        return clinicalVisitDao.getVisitSymptomSigns(visitId);
    }
}