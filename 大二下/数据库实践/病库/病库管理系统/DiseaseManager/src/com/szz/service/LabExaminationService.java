package com.szz.service;

import com.szz.dao.LabExaminationDao;
import com.szz.model.LabExamination;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LabExaminationService {
    private final LabExaminationDao labExaminationDao;

    public LabExaminationService() {
        this.labExaminationDao = new LabExaminationDao();
    }

    public List<LabExamination> getLabExaminationsByVisitId(int visitId) {
        try {
            return labExaminationDao.getLabExaminationsByVisitId(visitId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}