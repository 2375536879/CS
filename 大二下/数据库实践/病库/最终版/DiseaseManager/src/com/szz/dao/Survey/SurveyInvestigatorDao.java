package com.szz.dao.Survey;

import com.szz.model.Survey.SurveyInvestigator;
import com.szz.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SurveyInvestigatorDao {
    
    // 获取所有流调员信息
    public List<SurveyInvestigator> getAllSurveyInvestigators() throws Exception {
        String sql = "SELECT * FROM survey_investigators ORDER BY created_at DESC";
        List<SurveyInvestigator> investigators = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                investigators.add(mapResultSetToSurveyInvestigator(rs));
            }
        }

        return investigators;
    }

    // 映射ResultSet到SurveyInvestigator对象
    private SurveyInvestigator mapResultSetToSurveyInvestigator(ResultSet rs) throws SQLException {
        SurveyInvestigator investigator = new SurveyInvestigator();
        investigator.setId(rs.getInt("id"));
        investigator.setSurveyParticipantId(rs.getInt("survey_participant_id"));
        investigator.setInvestigatorName(rs.getString("investigator_name"));
        investigator.setInvestigatorTitle(rs.getString("investigator_title"));
        investigator.setCreatedAt(rs.getTimestamp("created_at"));
        investigator.setUpdatedAt(rs.getTimestamp("updated_at"));
        return investigator;
    }
}
