package com.zhao.po;

public class Record {
    private int RecordID;
    private String ISBN;
    private int ReaderID;
    private String BorrowingDate;
    private String ReturnDate;

    public int getRecordID() {
        return RecordID;
    }

    public void setRecordID(int recordID) {
        RecordID = recordID;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getReaderID() {
        return ReaderID;
    }

    public void setReaderID(int readerID) {
        ReaderID = readerID;
    }

    public String getBorrowingDate() {
        return BorrowingDate;
    }

    public void setBorrowingDate(String borrowingDate) {
        BorrowingDate = borrowingDate;
    }

    public String getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(String returnDate) {
        ReturnDate = returnDate;
    }
} 