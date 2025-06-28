package com.szz.service;

import com.szz.dao.InsuranceDao;
import com.szz.model.Insurance;

import java.sql.SQLException;
import java.util.List;

public class InsuranceService {
    private InsuranceDao insuranceDao = new InsuranceDao();

    public List<Insurance> getInsuranceByPatientId(int patientId) throws SQLException {
        return insuranceDao.getInsuranceByPatientId(patientId);
    }
}