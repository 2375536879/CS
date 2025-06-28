package com.szz.dao.Clinical;

import com.szz.model.Clinical.ImagingStudy;
import ADMS.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImagingStudyDao {
    public List<ImagingStudy> getImagingStudiesByVisitId(int visitId) throws SQLException {
        List<ImagingStudy> studies = new ArrayList<>();
        String sql = "SELECT * FROM imaging_studies WHERE visit_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, visitId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ImagingStudy study = new ImagingStudy();
                study.setId(rs.getInt("id"));
                study.setVisitId(rs.getInt("visit_id"));
                study.setStudyName(rs.getString("study_name"));
                study.setStudyDate(rs.getDate("study_date"));
                study.setReportSummary(rs.getString("report_summary"));
                study.setImagePathOrIdentifier(rs.getString("image_path_or_identifier"));
                study.setCreatedAt(rs.getTimestamp("created_at"));
                study.setUpdatedAt(rs.getTimestamp("updated_at"));
                studies.add(study);
            }
        }

        return studies;
    }
}