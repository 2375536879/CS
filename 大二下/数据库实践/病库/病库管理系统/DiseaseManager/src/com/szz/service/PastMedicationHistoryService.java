package com.szz.service;

import com.szz.dao.PastMedicationHistoryDao;
import com.szz.model.PastMedicationHistory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PastMedicationHistoryService {
    private final PastMedicationHistoryDao pastMedicationHistoryDao;

    public PastMedicationHistoryService() {
        this.pastMedicationHistoryDao = new PastMedicationHistoryDao();
    }

    public List<PastMedicationHistory> getPastMedicationHistoryByPatientId(int patientId) {
        try {
            return pastMedicationHistoryDao.getPastMedicationHistoryByPatientId(patientId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
