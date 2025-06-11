package com.zhao.Service;

import com.zhao.dao.ReaderDao;
import com.zhao.dao.RecordDao;
import com.zhao.po.Record;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecordService {
    private RecordDao recordDao;
    private ReaderDao readerDao;

    public RecordService() {
        this.recordDao = new RecordDao();
        this.readerDao = new ReaderDao();
    }

    // 借书
    public String borrowBook(String isbn, int readerId) {
        // 检查图书是否已被借出
        if (recordDao.isBookBorrowed(isbn)) {
            return "该书已被借出";
        }

        // 检查读者借阅权限
        int currentBorrowCount = recordDao.getCurrentBorrowCount(readerId);
        int borrowLimit = readerDao.getReaderBorrowLimit(readerId);
        
        if (currentBorrowCount >= borrowLimit) {
            return "已超过您的最大借阅数目";
        }

        // 创建借阅记录
        Record record = new Record();
        record.setISBN(isbn);
        record.setReaderID(readerId);
        record.setBorrowingDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        // 添加借阅记录
        if (recordDao.addBorrowRecord(record)) {
            return "借书成功";
        } else {
            return "借书失败，请稍后重试";
        }
    }

    // 还书
    public String returnBook(String isbn, int readerId) {
        String returnDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        
        if (recordDao.returnBook(isbn, readerId, returnDate)) {
            return "还书成功";
        } else {
            return "还书失败，请稍后重试";
        }
    }

    // 获取读者的借阅记录
    public List<Record> getReaderRecords(int readerId) {
        return recordDao.getReaderRecords(readerId);
    }
} 