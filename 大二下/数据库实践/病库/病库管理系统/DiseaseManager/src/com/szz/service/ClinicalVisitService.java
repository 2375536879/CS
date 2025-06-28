package com.szz.service;

import com.szz.dao.ClinicalVisitDao;
import com.szz.model.ClinicalVisit;

import java.sql.SQLException;
import java.util.List;

public class ClinicalVisitService {
    private ClinicalVisitDao clinicalVisitDao = new ClinicalVisitDao();

    public List<ClinicalVisit> getVisitsByPatientId(int patientId) throws SQLException {
        List<ClinicalVisit> visits = clinicalVisitDao.getVisitsByPatientId(patientId);
        // 为每个就诊记录加载症状和体征信息
        for (ClinicalVisit visit : visits) {
            List<ClinicalVisit.VisitSymptomSign> symptoms = clinicalVisitDao.getVisitSymptomSigns(visit.getId());
            // 这里可以将症状信息添加到就诊记录中，如果需要的话可以在ClinicalVisit类中添加相应的字段
        }
        return visits;
    }

    public List<ClinicalVisit.VisitSymptomSign> getVisitSymptomSigns(int visitId) throws SQLException {
        return clinicalVisitDao.getVisitSymptomSigns(visitId);
    }
}