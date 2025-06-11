package com.zhao.dao;

import com.zhao.po.Book;
import com.zhao.po.User;
import com.zhao.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QueryDao {

    public List<Book> queryBooks(String ISBN, String Title, String Authors, String Publisher, Integer EditionNumber, String PublicationDate, String Type) {
        List<Book> bookList = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtil.getInstance().getConnection();

            StringBuilder sql = new StringBuilder("SELECT * FROM books WHERE 1=1");

            if (ISBN != null && !ISBN.isEmpty()) {
                sql.append(" AND ISBN LIKE ?");
            }
            if (Title != null && !Title.isEmpty()) {
                sql.append(" AND Title LIKE ?");
            }
            if (Authors != null && !Authors.isEmpty()) {
                sql.append(" AND Authors LIKE ?");
            }
            if (Publisher != null && !Publisher.isEmpty()) {
                sql.append(" AND Publisher LIKE ?");
            }
            if (EditionNumber != null && EditionNumber > 0) {
                sql.append(" AND EditionNumber = ?");
            }
            if (PublicationDate != null && !PublicationDate.isEmpty()) {
                sql.append(" AND PublicationDate = ?");
            }
            if (Type != null && !Type.isEmpty()) {
                sql.append(" AND Type LIKE ?");
            }

            ps = conn.prepareStatement(sql.toString());

            int index = 1; // 参数索引
            if (ISBN != null && !ISBN.isEmpty()) {
                ps.setString(index++, "%" + ISBN + "%");
            }
            if (Title != null && !Title.isEmpty()) {
                ps.setString(index++, "%" + Title + "%");
            }
            if (Authors != null && !Authors.isEmpty()) {
                ps.setString(index++, "%" + Authors + "%");
            }
            if (Publisher != null && !Publisher.isEmpty()) {
                ps.setString(index++, "%" + Publisher + "%");
            }
            if (EditionNumber != null && EditionNumber > 0) {
                ps.setInt(index++, EditionNumber);
            }
            if (PublicationDate != null && !PublicationDate.isEmpty()) {
                ps.setString(index++, PublicationDate);
            }
            if (Type != null && !Type.isEmpty()) {
                ps.setString(index++, "%" + Type + "%");
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setISBN(rs.getString("ISBN"));
                book.setTitle(rs.getString("Title"));
                book.setAuthors(rs.getString("Authors"));
                book.setPublisher(rs.getString("Publisher"));
                book.setEditionNumber(rs.getInt("EditionNumber"));
                book.setPublicationDate(rs.getString("PublicationDate"));
                book.setType(rs.getString("Type"));
                bookList.add(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.getInstance().closeConnection(rs, ps, conn);
        }

        return bookList;
    }


}
