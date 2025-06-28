package com.szz.service;

import com.szz.dao.PotentialConfoundingFactorsDao;
import com.szz.model.PotentialConfoundingFactors;

import java.util.ArrayList;
import java.util.List;

public class PotentialConfoundingFactorsService {
    private PotentialConfoundingFactorsDao potentialConfoundingFactorsDao;

    public PotentialConfoundingFactorsService() {
        this.potentialConfoundingFactorsDao = new PotentialConfoundingFactorsDao();
    }

    // 获取所有潜在混杂因素记录
    public List<PotentialConfoundingFactors> getAllPotentialConfoundingFactors() {
        try {
            return potentialConfoundingFactorsDao.getAllPotentialConfoundingFactors();
        } catch (Exception e) {
            System.err.println("获取所有潜在混杂因素记录失败: " + e.getMessage());
            // 如果表不存在，返回空列表而不是抛出异常
            if (e.getMessage().contains("doesn't exist") || e.getMessage().contains("not found")) {
                return new ArrayList<>();
            }
            e.printStackTrace();
            throw new RuntimeException("获取所有潜在混杂因素记录失败", e);
        }
    }
}
