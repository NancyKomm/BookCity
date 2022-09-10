package com.example.bookcity;

public class Book {

    protected String title;
    protected String imgPath;
    protected String author;
    protected String info;
    protected String pages;
    protected String language;
    protected String price;
    protected int quantity;

    public Book(String title, String imgPath, String author, String info, String pages, String language, String price, int quantity){
        this.title = title;
        this.imgPath = imgPath;
        this.author = author;
        this.info = info;
        this.pages = pages;
        this.language = language;
        this.price = price;
        this.quantity = quantity;
    }

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
