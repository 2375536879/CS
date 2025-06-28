package com.szz.service.Clinical;

import com.szz.dao.Clinical.ImagingStudyDao;
import com.szz.model.Clinical.ImagingStudy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImagingStudyService {
    private final ImagingStudyDao imagingStudyDao;

    public ImagingStudyService() {
        this.imagingStudyDao = new ImagingStudyDao();
    }

    public List<ImagingStudy> getImagingStudiesByVisitId(int visitId) {
        try {
            return imagingStudyDao.getImagingStudiesByVisitId(visitId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}