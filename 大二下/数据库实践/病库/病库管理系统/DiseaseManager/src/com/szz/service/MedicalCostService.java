package com.szz.service;

import com.szz.dao.MedicalCostDao;
import com.szz.model.MedicalCost;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicalCostService {
    private final MedicalCostDao medicalCostDao;

    public MedicalCostService() {
        this.medicalCostDao = new MedicalCostDao();
    }

    public List<MedicalCost> getMedicalCostsByVisitId(int visitId) {
        try {
            return medicalCostDao.getMedicalCostsByVisitId(visitId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
