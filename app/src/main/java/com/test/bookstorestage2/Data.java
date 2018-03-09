package com.test.bookstorestage2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.test.bookstorestage2.BookContract.BookEntry;

import java.util.ArrayList;
import java.util.List;

public final class Data {

    private static BookDbHelper mBookDbHelper;

    private static Data data;

    private static List<Book> books;

    private Data() {

    }

    public static List<Book> getBooksData(Context context) {
        if (data == null) {
            data = new Data();
        }
        if (books == null) {
            books = new ArrayList<>();
        } else {
            books.clear();
        }

        mBookDbHelper = new BookDbHelper(context);

        SQLiteDatabase database = mBookDbHelper.getReadableDatabase();
        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_BOOK_NAME,
                BookEntry.COLUMN_BOOK_PRICE,
                BookEntry.COLUMN_BOOK_QUANTITY,
                BookEntry.COLUMN_BOOK_SUPPLIER_NAME,
                BookEntry.COLUMN_BOOK_SUPPLIER_PHONE_NUMBER
        };

        Cursor cursor = database.query(
                BookEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE_NUMBER);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentPrice = cursor.getString(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                String currentSupplierPhone = cursor.getString(supplierPhoneColumnIndex);
                books.add(new Book(currentId, currentName, currentPrice, currentQuantity, currentSupplierName, currentSupplierPhone));
            }
        } finally {
            cursor.close();
        }
        return books;
    }

    public static void insertData(String bookName, String bookPrice, int quantity, String supplierName, String supplierPhoneNumber) {
        SQLiteDatabase database = mBookDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BookEntry.COLUMN_BOOK_NAME, bookName);
        contentValues.put(BookEntry.COLUMN_BOOK_PRICE, bookPrice);
        contentValues.put(BookEntry.COLUMN_BOOK_QUANTITY, quantity);
        contentValues.put(BookEntry.COLUMN_BOOK_SUPPLIER_NAME, supplierName);
        contentValues.put(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE_NUMBER, supplierPhoneNumber);
        database.insert(BookEntry.TABLE_NAME, null, contentValues);
    }

    public static Book getBook(int id) {
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public static void deleteData(long id) {
        SQLiteDatabase database = mBookDbHelper.getWritableDatabase();
        database.delete(BookEntry.TABLE_NAME, BookEntry._ID + " = " + id, null);
    }

    public static void updateData(long id, String bookName, String bookPrice, int quantity, String supplierName, String supplierPhoneNumber) {
        SQLiteDatabase database = mBookDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BookEntry.COLUMN_BOOK_NAME, bookName);
        contentValues.put(BookEntry.COLUMN_BOOK_PRICE, bookPrice);
        contentValues.put(BookEntry.COLUMN_BOOK_QUANTITY, quantity);
        contentValues.put(BookEntry.COLUMN_BOOK_SUPPLIER_NAME, supplierName);
        contentValues.put(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE_NUMBER, supplierPhoneNumber);
        database.update(BookEntry.TABLE_NAME, contentValues, BookEntry._ID + " = " + id, null);
    }
}
