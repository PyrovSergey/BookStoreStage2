package com.test.bookstorestage2;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import com.test.bookstorestage2.BookContract.BookEntry;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "MyLOG";

    private RecyclerView recyclerView;
    private LinearLayoutManager verticalLinearLayoutManager;
    private RecyclerAdapter adapter;

    public static List<Book> bookList;

    private BookDbHelper mBookDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mBookDbHelper = new BookDbHelper(this);

        insertData();

        bookList = Data.getBooksData(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        verticalLinearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(verticalLinearLayoutManager);

        adapter = new RecyclerAdapter(bookList, this);
        recyclerView.setAdapter(adapter);

    }

    private void insertData() {
        SQLiteDatabase database = mBookDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BookEntry.COLUMN_BOOK_NAME, getString(R.string.pippi_longstocking));
        contentValues.put(BookEntry.COLUMN_BOOK_PRICE, getString(R.string.five));
        contentValues.put(BookEntry.COLUMN_BOOK_QUANTITY, 2);
        contentValues.put(BookEntry.COLUMN_BOOK_SUPPLIER_NAME, getString(R.string.supplier_name));
        contentValues.put(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE_NUMBER, getString(R.string.supplier_phone));
        database.insert(BookEntry.TABLE_NAME, null, contentValues);
    }
}

