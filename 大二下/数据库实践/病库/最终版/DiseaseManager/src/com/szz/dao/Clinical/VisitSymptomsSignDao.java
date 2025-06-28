package com.szz.dao.Clinical;

import com.szz.model.Clinical.VisitSymptomsSign;
import com.szz.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VisitSymptomsSignDao {
    
    // 创建症状体征记录
    public int createVisitSymptomsSign(VisitSymptomsSign symptomsSign) throws Exception {
        String sql = "INSERT INTO visit_symptoms_signs (visit_id, symptom_description, " +
                    "asthma_wheezing, asthma_coughing, asthma_dyspnea, asthma_activity_limitation, " +
                    "asthma_night_awakening, asthma_chest_tightness, asthma_night_morning_symptoms, asthma_triggers, " +
                    "ar_clear_nasal_discharge, ar_nasal_itching, ar_nasal_congestion, ar_paroxysmal_sneezing, " +
                    "ar_olfactory_decline, ar_throat_clearing, ar_sniffing, ar_irritative_dry_cough, " +
                    "ar_globus_sensation, ar_eye_itching, ar_blinking, ar_sleep_disturbance, " +
                    "ar_daily_activity_limitation, ar_school_work_performance_decline, " +
                    "ad_chronic_recurrent_pruritic_rash, ad_flexural_or_facial_dermatitis_history, ad_dry_skin, " +
                    "ad_xerosis, ad_retroauricular_fissures, ad_ichthyosis, ad_palmar_hyperlinearity, " +
                    "ad_keratosis_pilaris, ad_skin_infection_tendency, ad_dennie_morgan_infraorbital_fold, " +
                    "ad_periorbital_darkening, ad_facial_pallor, ad_pityriasis_alba, ad_anterior_neck_folds, " +
                    "symptom_severity, symptom_frequency, symptom_start_date, symptom_triggers_general, " +
                    "sign_description, ar_nasal_mucosa_swelling, ar_nasal_mucosa_pale_or_congested, " +
                    "ar_nasal_discharge_observed, ar_turbinate_hypertrophy, ar_allergic_shiners, " +
                    "ar_allergic_salute, ar_allergic_crease, asthma_auscultation_wheezing, environmental_factors_notes) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, symptomsSign.getVisitId());
            stmt.setString(2, symptomsSign.getSymptomDescription());
            stmt.setObject(3, symptomsSign.getAsthmaWheezing());
            stmt.setObject(4, symptomsSign.getAsthmaCoughing());
            stmt.setObject(5, symptomsSign.getAsthmaDyspnea());
            stmt.setObject(6, symptomsSign.getAsthmaActivityLimitation());
            stmt.setObject(7, symptomsSign.getAsthmaNightAwakening());
            stmt.setObject(8, symptomsSign.getAsthmaChestTightness());
            stmt.setObject(9, symptomsSign.getAsthmaNightMorningSymptoms());
            stmt.setString(10, symptomsSign.getAsthmaTriggers());
            stmt.setObject(11, symptomsSign.getArClearNasalDischarge());
            stmt.setObject(12, symptomsSign.getArNasalItching());
            stmt.setObject(13, symptomsSign.getArNasalCongestion());
            stmt.setObject(14, symptomsSign.getArParoxysmalSneezing());
            stmt.setObject(15, symptomsSign.getArOlfactoryDecline());
            stmt.setObject(16, symptomsSign.getArThroatClearing());
            stmt.setObject(17, symptomsSign.getArSniffing());
            stmt.setObject(18, symptomsSign.getArIrritativeDryCough());
            stmt.setObject(19, symptomsSign.getArGlobusSensation());
            stmt.setObject(20, symptomsSign.getArEyeItching());
            stmt.setObject(21, symptomsSign.getArBlinking());
            stmt.setObject(22, symptomsSign.getArSleepDisturbance());
            stmt.setObject(23, symptomsSign.getArDailyActivityLimitation());
            stmt.setObject(24, symptomsSign.getArSchoolWorkPerformanceDecline());
            stmt.setObject(25, symptomsSign.getAdChronicRecurrentPruriticRash());
            stmt.setObject(26, symptomsSign.getAdFlexuralOrFacialDermatitisHistory());
            stmt.setObject(27, symptomsSign.getAdDrySkin());
            stmt.setObject(28, symptomsSign.getAdXerosis());
            stmt.setObject(29, symptomsSign.getAdRetroauricularFissures());
            stmt.setObject(30, symptomsSign.getAdIchthyosis());
            stmt.setObject(31, symptomsSign.getAdPalmarHyperlinearity());
            stmt.setObject(32, symptomsSign.getAdKeratosisPilaris());
            stmt.setObject(33, symptomsSign.getAdSkinInfectionTendency());
            stmt.setObject(34, symptomsSign.getAdDennieMorganInfraorbitalFold());
            stmt.setObject(35, symptomsSign.getAdPeriorbitalDarkening());
            stmt.setObject(36, symptomsSign.getAdFacialPallor());
            stmt.setObject(37, symptomsSign.getAdPityriasisAlba());
            stmt.setObject(38, symptomsSign.getAdAnteriorNeckFolds());
            stmt.setString(39, symptomsSign.getSymptomSeverity());
            stmt.setString(40, symptomsSign.getSymptomFrequency());
            stmt.setDate(41, symptomsSign.getSymptomStartDate() != null ? new java.sql.Date(symptomsSign.getSymptomStartDate().getTime()) : null);
            stmt.setString(42, symptomsSign.getSymptomTriggersGeneral());
            stmt.setString(43, symptomsSign.getSignDescription());
            stmt.setObject(44, symptomsSign.getArNasalMucosaSwelling());
            stmt.setObject(45, symptomsSign.getArNasalMucosaPaleOrCongested());
            stmt.setObject(46, symptomsSign.getArNasalDischargeObserved());
            stmt.setObject(47, symptomsSign.getArTurbinateHypertrophy());
            stmt.setObject(48, symptomsSign.getArAllergicShiners());
            stmt.setObject(49, symptomsSign.getArAllergicSalute());
            stmt.setObject(50, symptomsSign.getArAllergicCrease());
            stmt.setObject(51, symptomsSign.getAsthmaAuscultationWheezing());
            stmt.setString(52, symptomsSign.getEnvironmentalFactorsNotes());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("创建症状体征记录失败，没有行被影响。");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("创建症状体征记录失败，无法获取ID。");
                }
            }
        }
    }

    // 根据ID获取症状体征记录
    public VisitSymptomsSign getVisitSymptomsSignById(int id) throws Exception {
        String sql = "SELECT * FROM visit_symptoms_signs WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToVisitSymptomsSign(rs);
            }
            return null;
        }
    }

    // 根据就诊ID获取症状体征记录
    public VisitSymptomsSign getVisitSymptomsSignByVisitId(int visitId) throws Exception {
        String sql = "SELECT * FROM visit_symptoms_signs WHERE visit_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, visitId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToVisitSymptomsSign(rs);
            }
            return null;
        }
    }

    // 获取所有症状体征记录
    public List<VisitSymptomsSign> getAllVisitSymptomsSign() throws Exception {
        List<VisitSymptomsSign> symptomsSignList = new ArrayList<>();
        String sql = "SELECT * FROM visit_symptoms_signs ORDER BY created_at DESC";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                symptomsSignList.add(mapResultSetToVisitSymptomsSign(rs));
            }
        }

        return symptomsSignList;
    }

    // 更新症状体征记录
    public boolean updateVisitSymptomsSign(VisitSymptomsSign symptomsSign) throws Exception {
        String sql = "UPDATE visit_symptoms_signs SET symptom_description = ?, " +
                    "asthma_wheezing = ?, asthma_coughing = ?, asthma_dyspnea = ?, asthma_activity_limitation = ?, " +
                    "asthma_night_awakening = ?, asthma_chest_tightness = ?, asthma_night_morning_symptoms = ?, " +
                    "asthma_triggers = ?, ar_clear_nasal_discharge = ?, ar_nasal_itching = ?, ar_nasal_congestion = ?, " +
                    "ar_paroxysmal_sneezing = ?, ar_olfactory_decline = ?, ar_throat_clearing = ?, ar_sniffing = ?, " +
                    "ar_irritative_dry_cough = ?, ar_globus_sensation = ?, ar_eye_itching = ?, ar_blinking = ?, " +
                    "ar_sleep_disturbance = ?, ar_daily_activity_limitation = ?, ar_school_work_performance_decline = ?, " +
                    "ad_chronic_recurrent_pruritic_rash = ?, ad_flexural_or_facial_dermatitis_history = ?, " +
                    "ad_dry_skin = ?, ad_xerosis = ?, ad_retroauricular_fissures = ?, ad_ichthyosis = ?, " +
                    "ad_palmar_hyperlinearity = ?, ad_keratosis_pilaris = ?, ad_skin_infection_tendency = ?, " +
                    "ad_dennie_morgan_infraorbital_fold = ?, ad_periorbital_darkening = ?, ad_facial_pallor = ?, " +
                    "ad_pityriasis_alba = ?, ad_anterior_neck_folds = ?, symptom_severity = ?, symptom_frequency = ?, " +
                    "symptom_start_date = ?, symptom_triggers_general = ?, sign_description = ?, " +
                    "ar_nasal_mucosa_swelling = ?, ar_nasal_mucosa_pale_or_congested = ?, ar_nasal_discharge_observed = ?, " +
                    "ar_turbinate_hypertrophy = ?, ar_allergic_shiners = ?, ar_allergic_salute = ?, ar_allergic_crease = ?, " +
                    "asthma_auscultation_wheezing = ?, environmental_factors_notes = ? WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, symptomsSign.getSymptomDescription());
            stmt.setObject(2, symptomsSign.getAsthmaWheezing());
            stmt.setObject(3, symptomsSign.getAsthmaCoughing());
            stmt.setObject(4, symptomsSign.getAsthmaDyspnea());
            stmt.setObject(5, symptomsSign.getAsthmaActivityLimitation());
            stmt.setObject(6, symptomsSign.getAsthmaNightAwakening());
            stmt.setObject(7, symptomsSign.getAsthmaChestTightness());
            stmt.setObject(8, symptomsSign.getAsthmaNightMorningSymptoms());
            stmt.setString(9, symptomsSign.getAsthmaTriggers());
            stmt.setObject(10, symptomsSign.getArClearNasalDischarge());
            stmt.setObject(11, symptomsSign.getArNasalItching());
            stmt.setObject(12, symptomsSign.getArNasalCongestion());
            stmt.setObject(13, symptomsSign.getArParoxysmalSneezing());
            stmt.setObject(14, symptomsSign.getArOlfactoryDecline());
            stmt.setObject(15, symptomsSign.getArThroatClearing());
            stmt.setObject(16, symptomsSign.getArSniffing());
            stmt.setObject(17, symptomsSign.getArIrritativeDryCough());
            stmt.setObject(18, symptomsSign.getArGlobusSensation());
            stmt.setObject(19, symptomsSign.getArEyeItching());
            stmt.setObject(20, symptomsSign.getArBlinking());
            stmt.setObject(21, symptomsSign.getArSleepDisturbance());
            stmt.setObject(22, symptomsSign.getArDailyActivityLimitation());
            stmt.setObject(23, symptomsSign.getArSchoolWorkPerformanceDecline());
            stmt.setObject(24, symptomsSign.getAdChronicRecurrentPruriticRash());
            stmt.setObject(25, symptomsSign.getAdFlexuralOrFacialDermatitisHistory());
            stmt.setObject(26, symptomsSign.getAdDrySkin());
            stmt.setObject(27, symptomsSign.getAdXerosis());
            stmt.setObject(28, symptomsSign.getAdRetroauricularFissures());
            stmt.setObject(29, symptomsSign.getAdIchthyosis());
            stmt.setObject(30, symptomsSign.getAdPalmarHyperlinearity());
            stmt.setObject(31, symptomsSign.getAdKeratosisPilaris());
            stmt.setObject(32, symptomsSign.getAdSkinInfectionTendency());
            stmt.setObject(33, symptomsSign.getAdDennieMorganInfraorbitalFold());
            stmt.setObject(34, symptomsSign.getAdPeriorbitalDarkening());
            stmt.setObject(35, symptomsSign.getAdFacialPallor());
            stmt.setObject(36, symptomsSign.getAdPityriasisAlba());
            stmt.setObject(37, symptomsSign.getAdAnteriorNeckFolds());
            stmt.setString(38, symptomsSign.getSymptomSeverity());
            stmt.setString(39, symptomsSign.getSymptomFrequency());
            stmt.setDate(40, symptomsSign.getSymptomStartDate() != null ? new java.sql.Date(symptomsSign.getSymptomStartDate().getTime()) : null);
            stmt.setString(41, symptomsSign.getSymptomTriggersGeneral());
            stmt.setString(42, symptomsSign.getSignDescription());
            stmt.setObject(43, symptomsSign.getArNasalMucosaSwelling());
            stmt.setObject(44, symptomsSign.getArNasalMucosaPaleOrCongested());
            stmt.setObject(45, symptomsSign.getArNasalDischargeObserved());
            stmt.setObject(46, symptomsSign.getArTurbinateHypertrophy());
            stmt.setObject(47, symptomsSign.getArAllergicShiners());
            stmt.setObject(48, symptomsSign.getArAllergicSalute());
            stmt.setObject(49, symptomsSign.getArAllergicCrease());
            stmt.setObject(50, symptomsSign.getAsthmaAuscultationWheezing());
            stmt.setString(51, symptomsSign.getEnvironmentalFactorsNotes());
            stmt.setInt(52, symptomsSign.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    // 删除症状体征记录
    public boolean deleteVisitSymptomsSign(int id) throws Exception {
        String sql = "DELETE FROM visit_symptoms_signs WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // 映射ResultSet到VisitSymptomsSign对象
    private VisitSymptomsSign mapResultSetToVisitSymptomsSign(ResultSet rs) throws SQLException {
        VisitSymptomsSign symptomsSign = new VisitSymptomsSign();
        symptomsSign.setId(rs.getInt("id"));
        symptomsSign.setVisitId(rs.getInt("visit_id"));
        symptomsSign.setSymptomDescription(rs.getString("symptom_description"));
        symptomsSign.setAsthmaWheezing(rs.getObject("asthma_wheezing", Boolean.class));
        symptomsSign.setAsthmaCoughing(rs.getObject("asthma_coughing", Boolean.class));
        symptomsSign.setAsthmaDyspnea(rs.getObject("asthma_dyspnea", Boolean.class));
        symptomsSign.setAsthmaActivityLimitation(rs.getObject("asthma_activity_limitation", Boolean.class));
        symptomsSign.setAsthmaNightAwakening(rs.getObject("asthma_night_awakening", Boolean.class));
        symptomsSign.setAsthmaChestTightness(rs.getObject("asthma_chest_tightness", Boolean.class));
        symptomsSign.setAsthmaNightMorningSymptoms(rs.getObject("asthma_night_morning_symptoms", Boolean.class));
        symptomsSign.setAsthmaTriggers(rs.getString("asthma_triggers"));
        symptomsSign.setArClearNasalDischarge(rs.getObject("ar_clear_nasal_discharge", Boolean.class));
        symptomsSign.setArNasalItching(rs.getObject("ar_nasal_itching", Boolean.class));
        symptomsSign.setArNasalCongestion(rs.getObject("ar_nasal_congestion", Boolean.class));
        symptomsSign.setArParoxysmalSneezing(rs.getObject("ar_paroxysmal_sneezing", Boolean.class));
        symptomsSign.setArOlfactoryDecline(rs.getObject("ar_olfactory_decline", Boolean.class));
        symptomsSign.setArThroatClearing(rs.getObject("ar_throat_clearing", Boolean.class));
        symptomsSign.setArSniffing(rs.getObject("ar_sniffing", Boolean.class));
        symptomsSign.setArIrritativeDryCough(rs.getObject("ar_irritative_dry_cough", Boolean.class));
        symptomsSign.setArGlobusSensation(rs.getObject("ar_globus_sensation", Boolean.class));
        symptomsSign.setArEyeItching(rs.getObject("ar_eye_itching", Boolean.class));
        symptomsSign.setArBlinking(rs.getObject("ar_blinking", Boolean.class));
        symptomsSign.setArSleepDisturbance(rs.getObject("ar_sleep_disturbance", Boolean.class));
        symptomsSign.setArDailyActivityLimitation(rs.getObject("ar_daily_activity_limitation", Boolean.class));
        symptomsSign.setArSchoolWorkPerformanceDecline(rs.getObject("ar_school_work_performance_decline", Boolean.class));
        symptomsSign.setAdChronicRecurrentPruriticRash(rs.getObject("ad_chronic_recurrent_pruritic_rash", Boolean.class));
        symptomsSign.setAdFlexuralOrFacialDermatitisHistory(rs.getObject("ad_flexural_or_facial_dermatitis_history", Boolean.class));
        symptomsSign.setAdDrySkin(rs.getObject("ad_dry_skin", Boolean.class));
        symptomsSign.setAdXerosis(rs.getObject("ad_xerosis", Boolean.class));
        symptomsSign.setAdRetroauricularFissures(rs.getObject("ad_retroauricular_fissures", Boolean.class));
        symptomsSign.setAdIchthyosis(rs.getObject("ad_ichthyosis", Boolean.class));
        symptomsSign.setAdPalmarHyperlinearity(rs.getObject("ad_palmar_hyperlinearity", Boolean.class));
        symptomsSign.setAdKeratosisPilaris(rs.getObject("ad_keratosis_pilaris", Boolean.class));
        symptomsSign.setAdSkinInfectionTendency(rs.getObject("ad_skin_infection_tendency", Boolean.class));
        symptomsSign.setAdDennieMorganInfraorbitalFold(rs.getObject("ad_dennie_morgan_infraorbital_fold", Boolean.class));
        symptomsSign.setAdPeriorbitalDarkening(rs.getObject("ad_periorbital_darkening", Boolean.class));
        symptomsSign.setAdFacialPallor(rs.getObject("ad_facial_pallor", Boolean.class));
        symptomsSign.setAdPityriasisAlba(rs.getObject("ad_pityriasis_alba", Boolean.class));
        symptomsSign.setAdAnteriorNeckFolds(rs.getObject("ad_anterior_neck_folds", Boolean.class));
        symptomsSign.setSymptomSeverity(rs.getString("symptom_severity"));
        symptomsSign.setSymptomFrequency(rs.getString("symptom_frequency"));
        symptomsSign.setSymptomStartDate(rs.getDate("symptom_start_date"));
        symptomsSign.setSymptomTriggersGeneral(rs.getString("symptom_triggers_general"));
        symptomsSign.setSignDescription(rs.getString("sign_description"));
        symptomsSign.setArNasalMucosaSwelling(rs.getObject("ar_nasal_mucosa_swelling", Boolean.class));
        symptomsSign.setArNasalMucosaPaleOrCongested(rs.getObject("ar_nasal_mucosa_pale_or_congested", Boolean.class));
        symptomsSign.setArNasalDischargeObserved(rs.getObject("ar_nasal_discharge_observed", Boolean.class));
        symptomsSign.setArTurbinateHypertrophy(rs.getObject("ar_turbinate_hypertrophy", Boolean.class));
        symptomsSign.setArAllergicShiners(rs.getObject("ar_allergic_shiners", Boolean.class));
        symptomsSign.setArAllergicSalute(rs.getObject("ar_allergic_salute", Boolean.class));
        symptomsSign.setArAllergicCrease(rs.getObject("ar_allergic_crease", Boolean.class));
        symptomsSign.setAsthmaAuscultationWheezing(rs.getObject("asthma_auscultation_wheezing", Boolean.class));
        symptomsSign.setEnvironmentalFactorsNotes(rs.getString("environmental_factors_notes"));
        symptomsSign.setCreatedAt(rs.getTimestamp("created_at"));
        symptomsSign.setUpdatedAt(rs.getTimestamp("updated_at"));
        return symptomsSign;
    }
}
