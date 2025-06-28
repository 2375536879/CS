package com.szz.service.Clinical;

import com.szz.dao.Clinical.LabExaminationDao;
import com.szz.model.Clinical.LabExamination;

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