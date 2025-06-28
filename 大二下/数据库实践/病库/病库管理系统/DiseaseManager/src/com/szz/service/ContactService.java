package com.szz.service;

import com.szz.dao.ContactDao;
import com.szz.model.Contact;

import java.sql.SQLException;
import java.util.List;

public class ContactService {
    private ContactDao contactDao = new ContactDao();

    public List<Contact> getContactsByPatientId(int patientId) throws SQLException {
        return contactDao.getContactsByPatientId(patientId);
    }
}