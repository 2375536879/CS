package com.szz.service.Clinical;

import com.szz.dao.Clinical.MedicationDao;
import com.szz.model.Clinical.Medication;

import java.util.ArrayList;
import java.util.List;

public class MedicationService {
    private final MedicationDao medicationDao;

    public MedicationService() {
        this.medicationDao = new MedicationDao();
    }

    public List<Medication> getMedicationsByVisitId(int visitId) {
        try {
            return medicationDao.getMedicationsByVisitId(visitId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
