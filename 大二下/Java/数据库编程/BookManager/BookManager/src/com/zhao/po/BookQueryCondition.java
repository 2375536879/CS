package com.zhao.po;

public class BookQueryCondition {
    private String ISBN;
    private String Title;
    private String Authors;
    private String Publisher;
    private int EditionNumber=1;
    private String PublicationDate; // 使用 String 以方便处理日期格式
    private String Type;

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthors() {
        return Authors;
    }

    public void setAuthors(String authors) {
        Authors = authors;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public int getEditionNumber() {
        return EditionNumber;
    }

    public void setEditionNumber(Integer editionNumber) {
        EditionNumber = editionNumber;
    }

    public String getPublicationDate() {
        return PublicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        PublicationDate = publicationDate;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
