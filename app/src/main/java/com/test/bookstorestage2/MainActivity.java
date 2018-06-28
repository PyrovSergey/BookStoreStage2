package com.test.bookstorestage2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    //public static final String LOG_TAG = "MyLOG";//
    @BindView(R.id.button_add)
    FloatingActionButton buttonAdd;
    @BindView(R.id.text_instruction)
    TextView textInstruction;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    public static List<Book> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bookList = Data.getBooksData(this);
        if (bookList.size() > 0) {
            textInstruction.setVisibility(View.INVISIBLE);
        } else {
            textInstruction.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager verticalLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(verticalLinearLayoutManager);

        RecyclerAdapter adapter = new RecyclerAdapter(bookList, this);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.button_add)
    public void onViewClicked() {
        Intent intent = new Intent(this, DetailedActivity.class);
        startActivity(intent);
    }
}

