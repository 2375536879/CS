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
    // 请根据您的数据库配置修改以下连接信息
    private static final String DB_URL = "jdbc:mysql://localhost:3306/allergic_disease_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "123456";

    /**
     * 获取数据库连接
     */
    private static Connection getConnection() throws SQLException {
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


    // =================================================================
    // == METHODS FOR BioSampleManageUI
    // =================================================================

    public static DefaultTableModel searchBioSamples(String id, String patientId, String visitId, String sampleType, String consentId, String orderBy) {
        StringBuilder sql = new StringBuilder("SELECT id, patient_id, visit_id, sample_type, collection_datetime, storage_temperature_celsius, consent_id, lab_processing_notes FROM biosamples WHERE 1=1");
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

    public static String addBioSample(String patientId, String sampleType) {
        String sql = "INSERT INTO biosamples (patient_id, sample_type, collection_datetime) VALUES (?, ?, NOW())";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(patientId));
            pstmt.setString(2, sampleType);
            pstmt.executeUpdate();
            return "生物样本添加成功！";
        } catch (SQLException | NumberFormatException e) {
            return "添加生物样本失败: " + e.getMessage();
        }
    }

    public static String updateBioSample(String sampleId, String column, String data) {
        List<String> validColumns = Arrays.asList("patient_id", "visit_id", "sample_type", "storage_temperature_celsius", "consent_id", "lab_processing_notes");
        if (!validColumns.contains(column)) {
            return "更新失败: 无效的列名: " + column;
        }
        String sql = String.format("UPDATE biosamples SET %s = ? WHERE id = ?", column);
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Handle null and empty strings for numeric/date fields if necessary
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
        // Note: The schema in docx and sql file for microbiome_data are different.
        // This code follows the schema in the SQL file (附件1).
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

    public static DefaultTableModel searchFollowUpVisits(String visitId, String patientId, String providerName) {
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
        sql.append(" ORDER BY visit_datetime DESC");
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
}
