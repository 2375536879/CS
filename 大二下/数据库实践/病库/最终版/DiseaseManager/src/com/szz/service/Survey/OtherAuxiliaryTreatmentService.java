package com.szz.service.Survey;

import com.szz.dao.Survey.OtherAuxiliaryTreatmentDao;
import com.szz.model.Survey.OtherAuxiliaryTreatment;

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
