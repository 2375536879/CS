package com.zhao.Service;

import com.zhao.dao.QueryDao;
import com.zhao.po.Book;
import com.zhao.po.BookQueryCondition;

import java.util.ArrayList;
import java.util.List;

public class BookService {

    QueryDao queryDao = new QueryDao();

    public List<Book> getBook(BookQueryCondition contioin){
        List<Book> books=new ArrayList<Book>();
        //ISBN Title Authors Publisher EditionNumber PublicationDate Type
        books=queryDao.queryBooks(contioin.getISBN(),contioin.getTitle(),contioin.getAuthors(),contioin.getPublisher(),contioin.getEditionNumber(),contioin.getPublicationDate(),contioin.getType());

        return books;
    }



}
