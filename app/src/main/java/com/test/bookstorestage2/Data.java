package com.test.bookstorestage2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        books = new ArrayList<>();
        mBookDbHelper = new BookDbHelper(context);

        SQLiteDatabase database = mBookDbHelper.getReadableDatabase();
        String[] projection = {
                BookContract.BookEntry._ID,
                BookContract.BookEntry.COLUMN_BOOK_NAME,
                BookContract.BookEntry.COLUMN_BOOK_PRICE,
                BookContract.BookEntry.COLUMN_BOOK_QUANTITY,
                BookContract.BookEntry.COLUMN_BOOK_SUPPLIER_NAME,
                BookContract.BookEntry.COLUMN_BOOK_SUPPLIER_PHONE_NUMBER
        };

        Cursor cursor = database.query(
                BookContract.BookEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int idColumnIndex = cursor.getColumnIndex(BookContract.BookEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_SUPPLIER_PHONE_NUMBER);

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
            //Log.e(LOG_TAG, stringBuilder.toString());
            cursor.close();
        }
        return books;
    }

    public static void insertData(String bookName, String bookPrice, int quantity, String supplierName, String supplierPhoneNumber) {
        SQLiteDatabase database = mBookDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BookContract.BookEntry.COLUMN_BOOK_NAME, bookName);
        contentValues.put(BookContract.BookEntry.COLUMN_BOOK_PRICE, bookPrice);
        contentValues.put(BookContract.BookEntry.COLUMN_BOOK_QUANTITY, quantity);
        contentValues.put(BookContract.BookEntry.COLUMN_BOOK_SUPPLIER_NAME, supplierName);
        contentValues.put(BookContract.BookEntry.COLUMN_BOOK_SUPPLIER_PHONE_NUMBER, supplierPhoneNumber);
        database.insert(BookContract.BookEntry.TABLE_NAME, null, contentValues);
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
}
