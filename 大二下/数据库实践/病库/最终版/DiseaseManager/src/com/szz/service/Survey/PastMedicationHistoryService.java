package com.szz.service.Survey;

import com.szz.dao.Survey.PastMedicationHistoryDao;
import com.szz.model.Survey.PastMedicationHistory;

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
