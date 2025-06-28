package com.szz.dao;

import com.szz.model.ClinicalVisit;
import com.szz.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClinicalVisitDao {

    // 创建就诊记录
    public int createClinicalVisit(ClinicalVisit visit) throws Exception {
        String sql = "INSERT INTO clinical_visits (patient_id, visit_date, height_cm, weight_kg, " +
                    "healthcare_provider_name, healthcare_provider_title, institution_name, institution_address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, visit.getPatientId());
            ps.setTimestamp(2, visit.getVisitDate() != null ? new Timestamp(visit.getVisitDate().getTime()) : null);
            ps.setObject(3, visit.getHeightCm());
            ps.setObject(4, visit.getWeightKg());
            ps.setString(5, visit.getHealthcareProviderName());
            ps.setString(6, visit.getHealthcareProviderTitle());
            ps.setString(7, visit.getInstitutionName());
            ps.setString(8, visit.getInstitutionAddress());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("创建就诊记录失败，没有行被影响。");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("创建就诊记录失败，无法获取ID。");
                }
            }
        }
    }

    // 根据ID获取就诊记录
    public ClinicalVisit getClinicalVisitById(int id) throws Exception {
        String sql = "SELECT * FROM clinical_visits WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToClinicalVisit(rs);
                }
            }
        }
        return null;
    }

    // 更新就诊记录
    public boolean updateClinicalVisit(ClinicalVisit visit) throws Exception {
        String sql = "UPDATE clinical_visits SET visit_date = ?, height_cm = ?, weight_kg = ?, " +
                    "healthcare_provider_name = ?, healthcare_provider_title = ?, institution_name = ?, " +
                    "institution_address = ? WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, visit.getVisitDate() != null ? new Timestamp(visit.getVisitDate().getTime()) : null);
            ps.setObject(2, visit.getHeightCm());
            ps.setObject(3, visit.getWeightKg());
            ps.setString(4, visit.getHealthcareProviderName());
            ps.setString(5, visit.getHealthcareProviderTitle());
            ps.setString(6, visit.getInstitutionName());
            ps.setString(7, visit.getInstitutionAddress());
            ps.setInt(8, visit.getId());

            return ps.executeUpdate() > 0;
        }
    }

    // 删除就诊记录（级联删除相关数据）
    public boolean deleteClinicalVisit(int id) throws Exception {
        String sql = "DELETE FROM clinical_visits WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<ClinicalVisit> getVisitsByPatientId(int patientId) throws SQLException {
        List<ClinicalVisit> visits = new ArrayList<>();
        String sql = "SELECT * FROM clinical_visits WHERE patient_id = ? ORDER BY visit_date DESC";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, patientId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ClinicalVisit visit = new ClinicalVisit();
                    visit.setId(rs.getInt("id"));
                    visit.setPatientId(rs.getInt("patient_id"));
                    visit.setVisitDate(rs.getTimestamp("visit_date"));
                    visit.setHeightCm(rs.getDouble("height_cm"));
                    visit.setWeightKg(rs.getDouble("weight_kg"));
                    visit.setHealthcareProviderName(rs.getString("healthcare_provider_name"));
                    visit.setHealthcareProviderTitle(rs.getString("healthcare_provider_title"));
                    visit.setInstitutionName(rs.getString("institution_name"));
                    visit.setInstitutionAddress(rs.getString("institution_address"));
                    visit.setCreatedAt(rs.getTimestamp("created_at"));
                    visit.setUpdatedAt(rs.getTimestamp("updated_at"));
                    visits.add(visit);
                }
            }
        }catch (SQLException e) {
            System.err.println("更新患者信息时发生SQL异常: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("更新患者信息失败", e);
        } catch (Exception e) {
            System.err.println("更新患者信息时发生未知异常: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("更新患者信息失败", e);
        }


        return visits;
    }

    public List<ClinicalVisit.VisitSymptomSign> getVisitSymptomSigns(int visitId) throws SQLException {
        List<ClinicalVisit.VisitSymptomSign> symptoms = new ArrayList<>();
        String sql = "SELECT * FROM visit_symptoms_signs WHERE visit_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, visitId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ClinicalVisit.VisitSymptomSign symptom = new ClinicalVisit.VisitSymptomSign();
                    symptom.setId(rs.getInt("id"));
                    symptom.setVisitId(rs.getInt("visit_id"));
                    symptom.setSymptomDescription(rs.getString("symptom_description"));
                    symptom.setAsthmaWheezing(rs.getBoolean("asthma_wheezing"));
                    symptom.setAsthmaCoughing(rs.getBoolean("asthma_coughing"));
                    symptom.setAsthmaDyspnea(rs.getBoolean("asthma_dyspnea"));
                    symptom.setAsthmaActivityLimitation(rs.getBoolean("asthma_activity_limitation"));
                    symptom.setAsthmaNightAwakening(rs.getBoolean("asthma_night_awakening"));
                    symptom.setAsthmaChestTightness(rs.getBoolean("asthma_chest_tightness"));
                    symptom.setAsthmaNightMorningSymptoms(rs.getBoolean("asthma_night_morning_symptoms"));
                    symptom.setAsthmaChestTightness(rs.getBoolean("asthma_chest_tightness"));
                    symptom.setAsthmaTriggersDescription(rs.getString("asthma_triggers"));
                    // AR symptoms
                    symptom.setArClearNasalDischarge(rs.getBoolean("ar_clear_nasal_discharge"));
                    symptom.setArNasalItching(rs.getBoolean("ar_nasal_itching"));
                    symptom.setArNasalCongestion(rs.getBoolean("ar_nasal_congestion"));
                    symptom.setArParoxysmalSneezing(rs.getBoolean("ar_paroxysmal_sneezing"));
                    symptom.setArOlfactoryDecline(rs.getBoolean("ar_olfactory_decline"));
                    symptom.setArThroatClearing(rs.getBoolean("ar_throat_clearing"));
                    symptom.setArSniffing(rs.getBoolean("ar_sniffing"));
                    symptom.setArIrritativeDryCough(rs.getBoolean("ar_irritative_dry_cough"));
                    symptom.setArGlobusSensation(rs.getBoolean("ar_globus_sensation"));
                    symptom.setArEyeItching(rs.getBoolean("ar_eye_itching"));
                    symptom.setArBlinking(rs.getBoolean("ar_blinking"));
                    symptom.setArSleepDisturbance(rs.getBoolean("ar_sleep_disturbance"));
                    symptom.setArDailyActivityLimitation(rs.getBoolean("ar_daily_activity_limitation"));
                    symptom.setArSchoolWorkPerformanceDecline(rs.getBoolean("ar_school_work_performance_decline"));
                    // AD symptoms
                    symptom.setAdChronicRecurrentPruriticRash(rs.getBoolean("ad_chronic_recurrent_pruritic_rash"));
                    symptom.setAdFlexuralOrFacialDermatitisHistory(rs.getBoolean("ad_flexural_or_facial_dermatitis_history"));
                    symptom.setAdDrySkin(rs.getBoolean("ad_dry_skin"));
                    symptom.setAdXerosis(rs.getBoolean("ad_xerosis"));
                    symptom.setAdRetroauricularFissures(rs.getBoolean("ad_retroauricular_fissures"));
                    symptom.setAdIchthyosis(rs.getBoolean("ad_ichthyosis"));
                    symptom.setAdPalmarHyperlinearity(rs.getBoolean("ad_palmar_hyperlinearity"));
                    symptom.setAdKeratosisPilaris(rs.getBoolean("ad_keratosis_pilaris"));
                    symptom.setAdSkinInfectionTendency(rs.getBoolean("ad_skin_infection_tendency"));
                    symptom.setAdDennieMorganInfraorbitalFold(rs.getBoolean("ad_dennie_morgan_infraorbital_fold"));
                    symptom.setAdPeriorbitalDarkening(rs.getBoolean("ad_periorbital_darkening"));
                    symptom.setAdFacialPallor(rs.getBoolean("ad_facial_pallor"));
                    symptom.setAdPityriasisAlba(rs.getBoolean("ad_pityriasis_alba"));
                    symptom.setAdAnteriorNeckFolds(rs.getBoolean("ad_anterior_neck_folds"));
                    // General symptoms
                    symptom.setSymptomSeverity(rs.getString("symptom_severity"));
                    symptom.setSymptomFrequency(rs.getString("symptom_frequency"));
                    symptom.setSymptomStartDate(rs.getDate("symptom_start_date"));
                    symptom.setSymptomTriggersGeneral(rs.getString("symptom_triggers_general"));
                    symptom.setSignDescription(rs.getString("sign_description"));
                    // AR signs
                    symptom.setArNasalMucosaSwelling(rs.getBoolean("ar_nasal_mucosa_swelling"));
                    symptom.setArNasalMucosaPaleOrCongested(rs.getBoolean("ar_nasal_mucosa_pale_or_congested"));
                    symptom.setArNasalDischargeObserved(rs.getBoolean("ar_nasal_discharge_observed"));
                    symptom.setArTurbinateHypertrophy(rs.getBoolean("ar_turbinate_hypertrophy"));
                    symptom.setArAllergicShiners(rs.getBoolean("ar_allergic_shiners"));
                    symptoms.add(symptom);
                }
            }
        }catch (SQLException e) {
            System.err.println("更新患者信息时发生SQL异常: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("更新患者信息失败", e);
        } catch (Exception e) {
            System.err.println("更新患者信息时发生未知异常: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("更新患者信息失败", e);
        }


        return symptoms;
    }

    // 映射ResultSet到ClinicalVisit对象
    private ClinicalVisit mapResultSetToClinicalVisit(ResultSet rs) throws SQLException {
        ClinicalVisit visit = new ClinicalVisit();
        visit.setId(rs.getInt("id"));
        visit.setPatientId(rs.getInt("patient_id"));
        visit.setVisitDate(rs.getTimestamp("visit_date"));
        visit.setHeightCm(rs.getDouble("height_cm"));
        visit.setWeightKg(rs.getDouble("weight_kg"));
        visit.setHealthcareProviderName(rs.getString("healthcare_provider_name"));
        visit.setHealthcareProviderTitle(rs.getString("healthcare_provider_title"));
        visit.setInstitutionName(rs.getString("institution_name"));
        visit.setInstitutionAddress(rs.getString("institution_address"));
        visit.setCreatedAt(rs.getTimestamp("created_at"));
        visit.setUpdatedAt(rs.getTimestamp("updated_at"));
        return visit;
    }
}