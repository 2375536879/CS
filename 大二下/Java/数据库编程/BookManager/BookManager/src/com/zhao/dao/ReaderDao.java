package com.zhao.dao;

import com.zhao.po.Reader;
import com.zhao.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReaderDao {

    // 获取读者的借阅限制
    public int getReaderBorrowLimit(int readerId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int limit = 0;

        try {
            conn = JDBCUtil.getInstance().getConnection();
            String sql = "SELECT Limits FROM reader WHERE ReaderID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, readerId);
            rs = ps.executeQuery();

            if (rs.next()) {
                limit = rs.getInt("Limits");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.getInstance().closeConnection(rs, ps, conn);
        }
        return limit;
    }

    // 添加读者（不插入 ReaderID，由数据库自动生成）
    public boolean addReader(Reader reader) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = JDBCUtil.getInstance().getConnection();
            String sql = "INSERT INTO reader (FirstName, LastName, Address, PhoneNumber, Limits) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, reader.getFirstName());
            ps.setString(2, reader.getLastName());
            ps.setString(3, reader.getAddress());
            ps.setString(4, reader.getPhone());
            ps.setInt(5, reader.getLimits());

            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.getInstance().closeConnection(null, ps, conn);
        }
        return success;
    }

    // 更新读者信息
    public boolean updateReader(Reader reader) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = JDBCUtil.getInstance().getConnection();
            String sql = "UPDATE reader SET FirstName = ?, LastName = ?, Address = ?, PhoneNumber = ?, Limits = ? WHERE ReaderID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, reader.getFirstName());
            ps.setString(2, reader.getLastName());
            ps.setString(3, reader.getAddress());
            ps.setString(4, reader.getPhone());
            ps.setInt(5, reader.getLimits());
            ps.setInt(6, reader.getId());

            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.getInstance().closeConnection(null, ps, conn);
        }
        return success;
    }

    // 删除读者
    public boolean deleteReader(int readerId) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = JDBCUtil.getInstance().getConnection();
            String sql = "DELETE FROM reader WHERE ReaderID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, readerId);

            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.getInstance().closeConnection(null, ps, conn);
        }
        return success;
    }

    // 获取读者信息
    public Reader getReader(int readerId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Reader reader = null;

        try {
            conn = JDBCUtil.getInstance().getConnection();
            String sql = "SELECT * FROM reader WHERE ReaderID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, readerId);
            rs = ps.executeQuery();

            if (rs.next()) {
                reader = new Reader();
                reader.setId(rs.getInt("ReaderID"));
                reader.setFirstName(rs.getString("FirstName"));
                reader.setLastName(rs.getString("LastName"));
                reader.setAddress(rs.getString("Address"));
                reader.setPhone(rs.getString("PhoneNumber"));
                reader.setLimits(rs.getInt("Limits"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.getInstance().closeConnection(rs, ps, conn);
        }
        return reader;
    }

    // 检查读者是否有未归还的图书
    public boolean hasUnreturnedBooks(int readerId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean hasUnreturned = false;

        try {
            conn = JDBCUtil.getInstance().getConnection();
            String sql = "SELECT COUNT(*) FROM record WHERE ReaderID = ? AND ReturnDate IS NULL";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, readerId);
            rs = ps.executeQuery();

            if (rs.next()) {
                hasUnreturned = rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.getInstance().closeConnection(rs, ps, conn);
        }
        return hasUnreturned;
    }
}