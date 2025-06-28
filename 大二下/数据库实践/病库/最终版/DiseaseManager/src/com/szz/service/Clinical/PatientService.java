package com.szz.service.Clinical;

import com.szz.dao.Clinical.PatientDao;
import com.szz.model.Clinical.Patient;

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
        return patientDao.getPatientById(id);
    }

    public void updatePatient(Patient patient) throws Exception {
        // TODO: 实现更新
        patientDao.updatePatient(patient);
    }

    public void deletePatient(int id) throws Exception {
        // TODO: 实现删除
        patientDao.deletePatient(id);
    }



}
