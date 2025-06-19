package com.szz.service;

import com.szz.dao.PatientDao;
import com.szz.model.Patient;

import java.util.List;

public class PatientService {

    private PatientDao patientDao=new PatientDao();
    public void registerNewPatient(Patient patient) throws Exception{
            patientDao.addPatient(patient);

    }


    public List<Patient>  getAllPatients()throws Exception{
            return patientDao.getAllPatients();
    }

    public Patient getPatientById(int id) throws Exception {
        // TODO: 实现根据ID查询
        return null;
    }

    public void updatePatient(Patient patient) throws Exception {
        // TODO: 实现更新

    }


}
