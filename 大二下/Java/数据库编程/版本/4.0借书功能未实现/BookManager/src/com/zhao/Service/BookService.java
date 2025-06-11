package com.zhao.Service;

import com.zhao.dao.QueryDao;
import com.zhao.po.Book;
import com.zhao.po.BookQueryCondition;

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
        // TODO: 实现添加图书的功能
        return false;
    }

    // 更新图书信息
    public boolean updateBook(Book book) {
        // TODO: 实现更新图书信息的功能
        return false;
    }

    // 删除图书
    public boolean deleteBook(String isbn) {
        // TODO: 实现删除图书的功能
        return false;
    }

    public List<Book> getBook(BookQueryCondition contioin){
        List<Book> books=new ArrayList<Book>();
        //ISBN Title Authors Publisher EditionNumber PublicationDate Type
        books=queryDao.queryBooks(contioin.getISBN(),contioin.getTitle(),contioin.getAuthors(),contioin.getPublisher(),contioin.getEditionNumber(),contioin.getPublicationDate(),contioin.getType());

        return books;
    }
}
