package com.zhao.Service;

import com.zhao.dao.QueryDao;
import com.zhao.po.Book;
import com.zhao.po.BookQueryCondition;
import com.zhao.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private QueryDao queryDao;

    public BookService() {
        this.queryDao = new QueryDao();
    }

    // 查询图书
    public List<Book> queryBooks(BookQueryCondition condition) {
        return queryDao.queryBooks(
            condition.getISBN(),
            condition.getTitle(),
            condition.getAuthors(),
            condition.getPublisher(),
            condition.getEditionNumber(),
            condition.getPublicationDate(),
            condition.getType()
        );
    }

    // 添加图书
    public boolean addBook(Book book) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = JDBCUtil.getInstance().getConnection();
            String sql = "INSERT INTO books (ISBN, Title, Authors, Publisher, EditionNumber, PublicationDate, Type) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, book.getISBN());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthors());
            ps.setString(4, book.getPublisher());
            ps.setInt(5, book.getEditionNumber());
            ps.setString(6, book.getPublicationDate());
            ps.setString(7, book.getType());

            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.getInstance().closeConnection(null, ps, conn);
        }
        return success;
    }

    // 更新图书信息
    public boolean updateBook(Book book) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = JDBCUtil.getInstance().getConnection();
            String sql = "UPDATE books SET Title = ?, Authors = ?, Publisher = ?, EditionNumber = ?, PublicationDate = ?, Type = ? WHERE ISBN = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthors());
            ps.setString(3, book.getPublisher());
            ps.setInt(4, book.getEditionNumber());
            ps.setString(5, book.getPublicationDate());
            ps.setString(6, book.getType());
            ps.setString(7, book.getISBN());

            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.getInstance().closeConnection(null, ps, conn);
        }
        return success;
    }

    // 删除图书
    public boolean deleteBook(String isbn) {
        // 首先检查图书是否被借出
        if (isBookBorrowed(isbn)) {
            return false;
        }

        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = JDBCUtil.getInstance().getConnection();
            String sql = "DELETE FROM books WHERE ISBN = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, isbn);

            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.getInstance().closeConnection(null, ps, conn);
        }
        return success;
    }

    // 检查图书是否被借出
    private boolean isBookBorrowed(String isbn) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean isBorrowed = false;

        try {
            conn = JDBCUtil.getInstance().getConnection();
            String sql = "SELECT COUNT(*) FROM record WHERE ISBN = ? AND ReturnDate IS NULL";
            ps = conn.prepareStatement(sql);
            ps.setString(1, isbn);
            var rs = ps.executeQuery();
            
            if (rs.next()) {
                isBorrowed = rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.getInstance().closeConnection(null, ps, conn);
        }
        return isBorrowed;
    }

    public List<Book> getBook(BookQueryCondition contioin){
        List<Book> books=new ArrayList<Book>();
        books=queryDao.queryBooks(contioin.getISBN(),contioin.getTitle(),contioin.getAuthors(),contioin.getPublisher(),contioin.getEditionNumber(),contioin.getPublicationDate(),contioin.getType());
        return books;
    }
}
