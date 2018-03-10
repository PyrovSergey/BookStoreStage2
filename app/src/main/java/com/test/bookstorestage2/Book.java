package com.test.bookstorestage2;


import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String bookName;
    private String price;
    private int quantity;
    private String supplierName;
    private String supplierPhone;

    public Book(int id, String bookName, String price, int quantity, String supplierName, String supplierPhone) {
        this.id = id;
        this.bookName = bookName;
        this.price = price;
        this.quantity = quantity;
        this.supplierName = supplierName;
        this.supplierPhone = supplierPhone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public String getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

}
