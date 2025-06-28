package com.szz.service.Clinical;

import com.szz.dao.Clinical.ExhaledNitricOxideTestDao;
import com.szz.model.Clinical.ExhaledNitricOxideTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExhaledNitricOxideTestService {
    private final ExhaledNitricOxideTestDao exhaledNitricOxideTestDao;

    public ExhaledNitricOxideTestService() {
        this.exhaledNitricOxideTestDao = new ExhaledNitricOxideTestDao();
    }

    public List<ExhaledNitricOxideTest> getExhaledNitricOxideTestsByVisitId(int visitId) {
        try {
            return exhaledNitricOxideTestDao.getExhaledNitricOxideTestsByVisitId(visitId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}