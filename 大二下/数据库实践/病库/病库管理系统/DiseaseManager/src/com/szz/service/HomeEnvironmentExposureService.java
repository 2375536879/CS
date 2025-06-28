package com.szz.service;

import com.szz.dao.HomeEnvironmentExposureDao;
import com.szz.model.HomeEnvironmentExposure;

import java.util.ArrayList;
import java.util.List;

public class HomeEnvironmentExposureService {
    private HomeEnvironmentExposureDao homeEnvironmentExposureDao;

    public HomeEnvironmentExposureService() {
        this.homeEnvironmentExposureDao = new HomeEnvironmentExposureDao();
    }

    // 创建家庭环境记录
    public int createHomeEnvironmentExposure(HomeEnvironmentExposure homeEnvironment) {
        try {
            return homeEnvironmentExposureDao.createHomeEnvironmentExposure(homeEnvironment);
        } catch (Exception e) {
            System.err.println("创建家庭环境记录失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("创建家庭环境记录失败", e);
        }
    }

    // 根据ID获取家庭环境记录
    public HomeEnvironmentExposure getHomeEnvironmentExposureById(int id) {
        try {
            return homeEnvironmentExposureDao.getHomeEnvironmentExposureById(id);
        } catch (Exception e) {
            System.err.println("获取家庭环境记录失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("获取家庭环境记录失败", e);
        }
    }

    // 根据参与者ID获取家庭环境记录列表
    public List<HomeEnvironmentExposure> getHomeEnvironmentExposuresByParticipantId(int participantId) {
        try {
            return homeEnvironmentExposureDao.getHomeEnvironmentExposuresByParticipantId(participantId);
        } catch (Exception e) {
            System.err.println("获取参与者家庭环境记录列表失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("获取参与者家庭环境记录列表失败", e);
        }
    }

    // 获取所有家庭环境记录
    public List<HomeEnvironmentExposure> getAllHomeEnvironmentExposures() {
        try {
            return homeEnvironmentExposureDao.getAllHomeEnvironmentExposures();
        } catch (Exception e) {
            System.err.println("获取所有家庭环境记录失败: " + e.getMessage());
            // 如果表不存在，返回空列表而不是抛出异常
            if (e.getMessage().contains("doesn't exist") || e.getMessage().contains("not found")) {
                return new ArrayList<>();
            }
            e.printStackTrace();
            throw new RuntimeException("获取所有家庭环境记录失败", e);
        }
    }

    // 更新家庭环境记录
    public boolean updateHomeEnvironmentExposure(HomeEnvironmentExposure homeEnvironment) {
        try {
            return homeEnvironmentExposureDao.updateHomeEnvironmentExposure(homeEnvironment);
        } catch (Exception e) {
            System.err.println("更新家庭环境记录失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("更新家庭环境记录失败", e);
        }
    }

    // 删除家庭环境记录
    public boolean deleteHomeEnvironmentExposure(int id) {
        try {
            return homeEnvironmentExposureDao.deleteHomeEnvironmentExposure(id);
        } catch (Exception e) {
            System.err.println("删除家庭环境记录失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("删除家庭环境记录失败", e);
        }
    }

    // 验证家庭环境记录数据
    public boolean validateHomeEnvironmentExposure(HomeEnvironmentExposure homeEnvironment) {
        if (homeEnvironment == null) {
            return false;
        }
        
        // 检查必填字段
        if (homeEnvironment.getParticipantId() <= 0) {
            return false;
        }
        
        return true;
    }

    // 根据住房类型搜索
    public List<HomeEnvironmentExposure> searchByHousingType(String housingType) {
        try {
            List<HomeEnvironmentExposure> allRecords = homeEnvironmentExposureDao.getAllHomeEnvironmentExposures();
            return allRecords.stream()
                    .filter(record -> 
                        record.getHousingType() != null && 
                        record.getHousingType().toLowerCase().contains(housingType.toLowerCase())
                    )
                    .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            System.err.println("根据住房类型搜索失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("根据住房类型搜索失败", e);
        }
    }

    // 根据是否有宠物搜索
    public List<HomeEnvironmentExposure> searchByPetStatus(boolean hasPets) {
        try {
            List<HomeEnvironmentExposure> allRecords = homeEnvironmentExposureDao.getAllHomeEnvironmentExposures();
            return allRecords.stream()
                    .filter(record -> 
                        record.getHasPets() != null && 
                        record.getHasPets().equals(hasPets)
                    )
                    .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            System.err.println("根据宠物状态搜索失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("根据宠物状态搜索失败", e);
        }
    }

    // 根据是否有室内吸烟搜索
    public List<HomeEnvironmentExposure> searchBySmokingStatus(boolean hasSmokingIndoors) {
        try {
            List<HomeEnvironmentExposure> allRecords = homeEnvironmentExposureDao.getAllHomeEnvironmentExposures();
            return allRecords.stream()
                    .filter(record -> 
                        record.getHasSmokingIndoors() != null && 
                        record.getHasSmokingIndoors().equals(hasSmokingIndoors)
                    )
                    .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            System.err.println("根据吸烟状态搜索失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("根据吸烟状态搜索失败", e);
        }
    }

    // 获取环境风险评估
    public String getEnvironmentalRiskAssessment(HomeEnvironmentExposure homeEnvironment) {
        if (homeEnvironment == null) {
            return "无法评估";
        }

        int riskScore = 0;
        StringBuilder riskFactors = new StringBuilder();

        // 检查各种风险因素
        if (Boolean.TRUE.equals(homeEnvironment.getHasSmokingIndoors())) {
            riskScore += 3;
            riskFactors.append("室内吸烟; ");
        }

        if (Boolean.TRUE.equals(homeEnvironment.getHasMoldOrDampness())) {
            riskScore += 2;
            riskFactors.append("霉菌/潮湿; ");
        }

        if (Boolean.TRUE.equals(homeEnvironment.getHasChemicalExposure())) {
            riskScore += 2;
            riskFactors.append("化学物质暴露; ");
        }

        if (Boolean.TRUE.equals(homeEnvironment.getHasDustMites())) {
            riskScore += 1;
            riskFactors.append("尘螨; ");
        }

        if (Boolean.FALSE.equals(homeEnvironment.getHasVentilationSystem())) {
            riskScore += 1;
            riskFactors.append("通风不良; ");
        }

        // 根据风险评分返回评估结果
        String riskLevel;
        if (riskScore >= 6) {
            riskLevel = "高风险";
        } else if (riskScore >= 3) {
            riskLevel = "中等风险";
        } else {
            riskLevel = "低风险";
        }

        return String.format("%s (评分: %d) - 风险因素: %s", 
                riskLevel, riskScore, 
                riskFactors.length() > 0 ? riskFactors.toString() : "无明显风险因素");
    }
}
