package com.zhao.dao;

import com.zhao.po.Record;
import com.zhao.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RecordDao {
    
    // 检查图书是否已被借出
    public boolean isBookBorrowed(String isbn) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean isBorrowed = false;

        try {
            conn = JDBCUtil.getInstance().getConnection();
            String sql = "SELECT COUNT(*) FROM record WHERE ISBN = ? AND ReturnDate IS NULL";
            ps = conn.prepareStatement(sql);
            ps.setString(1, isbn);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                isBorrowed = rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.getInstance().closeConnection(rs, ps, conn);
        }
        return isBorrowed;
    }

    // 获取读者当前借阅数量
    public int getCurrentBorrowCount(int readerId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;

        try {
            conn = JDBCUtil.getInstance().getConnection();
            String sql = "SELECT COUNT(*) FROM record WHERE ReaderID = ? AND ReturnDate IS NULL";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, readerId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.getInstance().closeConnection(rs, ps, conn);
        }
        return count;
    }

    // 添加借阅记录
    public boolean addBorrowRecord(Record record) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = JDBCUtil.getInstance().getConnection();
            String sql = "INSERT INTO record (ISBN, ReaderID, BorrowingDate) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, record.getISBN());
            ps.setInt(2, record.getReaderID());
            ps.setString(3, record.getBorrowingDate());
            
            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.getInstance().closeConnection(null, ps, conn);
        }
        return success;
    }

    // 更新还书记录
    public boolean returnBook(String isbn, int readerId, String returnDate) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = JDBCUtil.getInstance().getConnection();
            String sql = "UPDATE record SET ReturnDate = ? WHERE ISBN = ? AND ReaderID = ? AND ReturnDate IS NULL";
            ps = conn.prepareStatement(sql);
            ps.setString(1, returnDate);
            ps.setString(2, isbn);
            ps.setInt(3, readerId);
            
            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.getInstance().closeConnection(null, ps, conn);
        }
        return success;
    }

    // 获取读者的借阅记录
    public List<Record> getReaderRecords(int readerId) {
        List<Record> records = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtil.getInstance().getConnection();
            String sql = "SELECT * FROM record WHERE ReaderID = ? ORDER BY BorrowingDate DESC";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, readerId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Record record = new Record();
                record.setRecordID(rs.getInt("RecordID"));
                record.setISBN(rs.getString("ISBN"));
                record.setReaderID(rs.getInt("ReaderID"));
                record.setBorrowingDate(rs.getString("BorrowingDate"));
                record.setReturnDate(rs.getString("ReturnDate"));
                records.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.getInstance().closeConnection(rs, ps, conn);
        }
        return records;
    }
} 