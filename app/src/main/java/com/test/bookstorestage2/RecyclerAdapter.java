package com.test.bookstorestage2;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Book> bookList;
    private Context context;

    public RecyclerAdapter(List<Book> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Book book = bookList.get(position);
        Picasso.with(context).load(R.drawable.placeholder).into(holder.booksImage);
        holder.textViewBookTitle.setText(book.getBookName());
        holder.textViewTotalLeft.setText(String.valueOf(book.getQuantity()));
        holder.textViewBookPrice.setText(book.getPrice());
        /// тут мы повесим слушателей на кнопки
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView booksImage;
        TextView textViewBookTitle;
        TextView textViewTotalLeft;
        TextView textViewBookPrice;
        ImageButton imageButtonSettingDetail;
        ImageButton imageButtonButtonSale;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            booksImage = view.findViewById(R.id.card_image);
            textViewBookTitle = view.findViewById(R.id.card_book_title);
            textViewTotalLeft = view.findViewById(R.id.total_left);
            textViewBookPrice = view.findViewById(R.id.card_book_price);
            imageButtonSettingDetail = view.findViewById(R.id.card_button_setting);
            imageButtonButtonSale = view.findViewById(R.id.card_button_sale);
        }
    }
}