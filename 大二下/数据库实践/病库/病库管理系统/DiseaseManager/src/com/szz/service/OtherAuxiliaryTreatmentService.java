package com.szz.service;

import com.szz.dao.OtherAuxiliaryTreatmentDao;
import com.szz.model.OtherAuxiliaryTreatment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OtherAuxiliaryTreatmentService {
    private final OtherAuxiliaryTreatmentDao otherAuxiliaryTreatmentDao;

    public OtherAuxiliaryTreatmentService() {
        this.otherAuxiliaryTreatmentDao = new OtherAuxiliaryTreatmentDao();
    }

    public List<OtherAuxiliaryTreatment> getOtherAuxiliaryTreatmentsByVisitId(int visitId) {
        try {
            return otherAuxiliaryTreatmentDao.getOtherAuxiliaryTreatmentsByVisitId(visitId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
