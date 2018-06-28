package com.test.bookstorestage2;


import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.bookName);
        dest.writeString(this.price);
        dest.writeInt(this.quantity);
        dest.writeString(this.supplierName);
        dest.writeString(this.supplierPhone);
    }

    protected Book(Parcel in) {
        this.id = in.readInt();
        this.bookName = in.readString();
        this.price = in.readString();
        this.quantity = in.readInt();
        this.supplierName = in.readString();
        this.supplierPhone = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

}
