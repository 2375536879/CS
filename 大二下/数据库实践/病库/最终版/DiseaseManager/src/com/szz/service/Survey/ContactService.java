package com.szz.service.Survey;

import com.szz.dao.Survey.ContactDao;
import com.szz.model.Survey.Contact;

import java.sql.SQLException;
import java.util.List;

public class ContactService {
    private ContactDao contactDao = new ContactDao();

    public List<Contact> getContactsByPatientId(int patientId) throws SQLException {
        return contactDao.getContactsByPatientId(patientId);
    }
}