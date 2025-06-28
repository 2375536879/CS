package ADMS;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * 数据库管理类，处理所有JDBC操作
 */
public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_disease?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "123456";

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    /**
     * 将ResultSet转换为DefaultTableModel
     */
    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }
    /**
     * Helper method to set a PreparedStatement parameter, handling empty strings as NULL.
     */
    private static void setStringOrNull(PreparedStatement pstmt, int index, String value) throws SQLException {
        if (value == null || value.trim().isEmpty()) {
            pstmt.setNull(index, Types.VARCHAR);
        } else {
            pstmt.setString(index, value);
        }
    }

    private static void setIntOrNull(PreparedStatement pstmt, int index, String value) throws SQLException {
        if (value == null || value.trim().isEmpty()) {
            pstmt.setNull(index, Types.INTEGER);
        } else {
            pstmt.setInt(index, Integer.parseInt(value));
        }
    }

    private static void setDecimalOrNull(PreparedStatement pstmt, int index, String value) throws SQLException {
        if (value == null || value.trim().isEmpty()) {
            pstmt.setNull(index, Types.DECIMAL);
        } else {
            pstmt.setBigDecimal(index, new java.math.BigDecimal(value));
        }
    }

    // =================================================================
    // == METHODS FOR BioSampleManageUI
    // =================================================================

    public static DefaultTableModel searchBioSamples(String id, String patientId, String visitId, String sampleType, String consentId, String orderBy) {
        StringBuilder sql = new StringBuilder("SELECT * FROM biosamples WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (id != null && !id.trim().isEmpty()) {
            sql.append(" AND id = ?");
            params.add(id);
        }
        if (patientId != null && !patientId.trim().isEmpty()) {
            sql.append(" AND patient_id = ?");
            params.add(patientId);
        }
        if (visitId != null && !visitId.trim().isEmpty()) {
            sql.append(" AND visit_id = ?");
            params.add(visitId);
        }
        if (sampleType != null && !sampleType.trim().isEmpty()) {
            sql.append(" AND sample_type LIKE ?");
            params.add("%" + sampleType + "%");
        }
        if (consentId != null && !consentId.trim().isEmpty()) {
            sql.append(" AND consent_id LIKE ?");
            params.add("%" + consentId + "%");
        }
        sql.append(" ORDER BY ").append(orderBy);
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }
    
    /**
     * Adds a new biological sample with detailed information.
     */
    public static String addBioSample(String patientId, String visitId, String sampleType, String collectionDateTime, String collectionSite, String preprocessingMethod, String storageTemp, String freezeCycles, String storageDays, String rnaIntegrity, String dnaConcentration, String linkedSummary, String consentId, String notes) {
        String sql = "INSERT INTO biosamples (patient_id, visit_id, sample_type, collection_datetime, collection_site, preprocessing_method, storage_temperature_celsius, freeze_thaw_cycles, storage_duration_days, rna_integrity_index, dna_concentration_ng_ul, linked_clinical_phenotype_summary, consent_id, lab_processing_notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setIntOrNull(pstmt, 1, patientId);
            setIntOrNull(pstmt, 2, visitId);
            setStringOrNull(pstmt, 3, sampleType);
            setStringOrNull(pstmt, 4, collectionDateTime);
            setStringOrNull(pstmt, 5, collectionSite);
            setStringOrNull(pstmt, 6, preprocessingMethod);
            setDecimalOrNull(pstmt, 7, storageTemp);
            setIntOrNull(pstmt, 8, freezeCycles);
            setIntOrNull(pstmt, 9, storageDays);
            setDecimalOrNull(pstmt, 10, rnaIntegrity);
            setDecimalOrNull(pstmt, 11, dnaConcentration);
            setStringOrNull(pstmt, 12, linkedSummary);
            setStringOrNull(pstmt, 13, consentId);
            setStringOrNull(pstmt, 14, notes);
            
            pstmt.executeUpdate();
            return "生物样本添加成功！";
        } catch (SQLException | NumberFormatException e) {
            return "添加生物样本失败: " + e.getMessage();
        }
    }

    public static String updateBioSample(String sampleId, String column, String data) {
        List<String> validColumns = Arrays.asList("patient_id", "visit_id", "sample_type", "collection_datetime", "collection_site", "preprocessing_method", "storage_temperature_celsius", "freeze_thaw_cycles", "storage_duration_days", "rna_integrity_index", "dna_concentration_ng_ul", "linked_clinical_phenotype_summary", "consent_id", "lab_processing_notes");
        if (!validColumns.contains(column)) {
            return "更新失败: 无效的列名: " + column;
        }
        String sql = String.format("UPDATE biosamples SET %s = ? WHERE id = ?", column);
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (data == null || data.trim().isEmpty()) {
                pstmt.setNull(1, Types.NULL);
            } else {
                pstmt.setString(1, data);
            }
            pstmt.setInt(2, Integer.parseInt(sampleId));
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0 ? "样本修改成功!" : "修改样本失败!";
        } catch (SQLException | NumberFormatException e) {
            return "修改样本失败: " + e.getMessage();
        }
    }

    public static String deleteBioSample(String sampleId) {
        String sql = "DELETE FROM biosamples WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(sampleId));
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0 ? "样本删除成功！" : "未找到该ID的样本。";
        } catch (SQLException e) {
            return "删除样本失败: " + e.getMessage() + " (该样本可能关联了组学数据)";
        }
    }

    public static DefaultTableModel getGenomicData(String bioSampleId) {
        String sql = "SELECT * FROM genomic_data WHERE biosample_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(bioSampleId));
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }

    public static DefaultTableModel getProteomicData(String bioSampleId) {
        String sql = "SELECT * FROM proteomic_data WHERE biosample_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(bioSampleId));
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }
    
    public static DefaultTableModel getMetabolomicData(String bioSampleId) {
        String sql = "SELECT * FROM metabolomic_data WHERE biosample_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(bioSampleId));
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }

    public static DefaultTableModel getMicrobiomeData(String bioSampleId) {
        String sql = "SELECT * FROM microbiome_data WHERE biosample_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(bioSampleId));
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }

    // =================================================================
    // == METHODS FOR FollowUpManageUI
    // =================================================================

    public static DefaultTableModel searchFollowUpVisits(String visitId, String patientId, String providerName, String orderBy) {
        StringBuilder sql = new StringBuilder("SELECT * FROM followup_visits WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (visitId != null && !visitId.trim().isEmpty()) {
            sql.append(" AND id = ?");
            params.add(visitId);
        }
        if (patientId != null && !patientId.trim().isEmpty()) {
            sql.append(" AND patient_id = ?");
            params.add(patientId);
        }
        if (providerName != null && !providerName.trim().isEmpty()) {
            sql.append(" AND provider_name LIKE ?");
            params.add("%" + providerName + "%");
        }
        sql.append(" ORDER BY ").append(orderBy);
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }

    /**
     * Adds a new follow-up visit record.
     */
    public static String addFollowUpVisit(String patientId, String hospitalPatientId, String visitDateTime, String isInitial, String height, String weight, String providerName, String providerTitle, String homeAddress) {
        String sql = "INSERT INTO followup_visits (patient_id, hospital_patient_id_followup, visit_datetime, is_initial_visit, height_cm, weight_kg, provider_name, provider_title, home_address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setIntOrNull(pstmt, 1, patientId);
            setStringOrNull(pstmt, 2, hospitalPatientId);
            setStringOrNull(pstmt, 3, visitDateTime);
            setIntOrNull(pstmt, 4, isInitial);
            setDecimalOrNull(pstmt, 5, height);
            setDecimalOrNull(pstmt, 6, weight);
            setStringOrNull(pstmt, 7, providerName);
            setStringOrNull(pstmt, 8, providerTitle);
            setStringOrNull(pstmt, 9, homeAddress);
            
            pstmt.executeUpdate();
            return "随访记录添加成功！";
        } catch (SQLException | NumberFormatException e) {
            return "添加随访记录失败: " + e.getMessage();
        }
    }

    /**
     * Updates a follow-up visit record.
     */
    public static String updateFollowUpVisit(String visitId, String column, String data) {
        List<String> validColumns = Arrays.asList("patient_id", "hospital_patient_id_followup", "visit_datetime", "is_initial_visit", "height_cm", "weight_kg", "provider_name", "provider_title", "home_address");
        if (!validColumns.contains(column)) {
            return "更新失败: 无效的列名: " + column;
        }
        String sql = String.format("UPDATE followup_visits SET %s = ? WHERE id = ?", column);
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setStringOrNull(pstmt, 1, data); // Simplified: assumes all can be set as string
            setIntOrNull(pstmt, 2, visitId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0 ? "随访记录修改成功!" : "修改随访记录失败!";
        } catch (SQLException | NumberFormatException e) {
            return "修改随访记录失败: " + e.getMessage();
        }
    }

    /**
     * Deletes a follow-up visit record.
     */
    public static String deleteFollowUpVisit(String visitId) {
        String sql = "DELETE FROM followup_visits WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(visitId));
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0 ? "随访记录删除成功！" : "未找到该ID的记录。";
        } catch (SQLException e) {
            return "删除失败: " + e.getMessage() + " (请先删除关联的子表数据)";
        }
    }
    
    public static DefaultTableModel getFollowUpSymptoms(int visitId) {
        String sql = "SELECT * FROM followup_symptoms WHERE followup_visit_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, visitId);
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }

    public static DefaultTableModel getFollowUpSigns(int visitId) {
        String sql = "SELECT * FROM followup_signs WHERE followup_visit_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, visitId);
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }
    
    public static DefaultTableModel getFollowUpLabTests(int visitId) {
        String sql = "SELECT * FROM followup_lab_tests WHERE followup_visit_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, visitId);
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }
    
    public static DefaultTableModel getFollowUpPulmonaryFunctionTests(int visitId) {
        String sql = "SELECT * FROM followup_pulmonary_function_tests WHERE followup_visit_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, visitId);
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }
    
    public static DefaultTableModel getFollowUpFeNOTests(int visitId) {
        String sql = "SELECT * FROM followup_feno_tests WHERE followup_visit_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, visitId);
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }
    
    public static DefaultTableModel getFollowUpNasoendoscopy(int visitId) {
        String sql = "SELECT * FROM followup_nasoendoscopy WHERE followup_visit_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, visitId);
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }
    
    public static DefaultTableModel getFollowUpHearingTests(int visitId) {
        String sql = "SELECT * FROM followup_hearing_tests WHERE followup_visit_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, visitId);
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }

    public static DefaultTableModel getFollowUpNasalResistanceTests(int visitId) {
        String sql = "SELECT * FROM followup_nasal_resistance_tests WHERE followup_visit_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, visitId);
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }
    
    public static DefaultTableModel getFollowUpImagingStudies(int visitId) {
        String sql = "SELECT * FROM followup_imaging_studies WHERE followup_visit_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, visitId);
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }
    

    public static DefaultTableModel getFollowUpDiagnoses(int visitId) {
        String sql = "SELECT * FROM followup_diagnoses WHERE followup_visit_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, visitId);
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }
    
    public static DefaultTableModel getFollowUpMedications(int visitId) {
        String sql = "SELECT * FROM followup_medications WHERE followup_visit_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, visitId);
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }
    
    public static DefaultTableModel getFollowUpPastMedicationHistory(int visitId) {
        String sql = "SELECT * FROM followup_past_medication_history WHERE followup_visit_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, visitId);
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }
    
    public static DefaultTableModel getFollowUpOtherTreatments(int visitId) {
        String sql = "SELECT * FROM followup_other_treatments WHERE followup_visit_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, visitId);
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }

    public static DefaultTableModel getFollowUpQuestionnaires(int visitId) {
        String sql = "SELECT * FROM followup_questionnaires WHERE followup_visit_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, visitId);
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }
    
    public static DefaultTableModel getFollowUpSummaryInfo(int visitId) {
        String sql = "SELECT * FROM followup_summary_info WHERE followup_visit_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, visitId);
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }
    
    public static DefaultTableModel getFollowUpCosts(int visitId) {
        String sql = "SELECT * FROM followup_costs WHERE followup_visit_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, visitId);
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }
    
    public static DefaultTableModel getFollowUpAdverseDrugReactions(int visitId) {
        String sql = "SELECT * FROM followup_adverse_drug_reactions WHERE followup_visit_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, visitId);
            return buildTableModel(pstmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return new DefaultTableModel();
        }
    }
}
