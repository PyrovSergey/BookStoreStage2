package com.test.bookstorestage2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailedActivity extends AppCompatActivity {

    @BindView(R.id.edit_book_title)
    EditText editBookTitle;
    @BindView(R.id.edit_book_price)
    EditText editBookPrice;
    @BindView(R.id.edit_book_supplier_name)
    EditText editBookSupplierName;
    @BindView(R.id.edit_book_supplier_phone_number)
    EditText editBookSupplierPhoneNumber;
    @BindView(R.id.edit_book_quantity)
    EditText editBookQuantity;
    @BindView(R.id.button_back)
    FloatingActionButton buttonBack;
    @BindView(R.id.button_delete)
    FloatingActionButton buttonDelete;
    @BindView(R.id.button_save)
    FloatingActionButton buttonSave;
    @BindView(R.id.button_call)
    FloatingActionButton buttonCall;
    @BindView(R.id.button_plus)
    ImageButton buttonPlus;
    @BindView(R.id.button_minus)
    ImageButton buttonMinus;

    private Book book;
    private Intent intent;
    private boolean isNewBook = true;
    private int currentDetailQuantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        ButterKnife.bind(this);

        intent = getIntent();
        if (intent != null) {
            this.book = (Book) intent.getSerializableExtra("detail");
            if (book != null) {
                isNewBook = false;
                editBookTitle.setText(book.getBookName());
                editBookPrice.setText(book.getPrice());
                editBookSupplierName.setText(book.getSupplierName());
                editBookSupplierPhoneNumber.setText(book.getSupplierPhone());
                editBookQuantity.setText(String.valueOf(book.getQuantity()));
            } else {
                buttonDelete.setVisibility(View.INVISIBLE);
            }
        }
    }


    @OnClick({R.id.button_back, R.id.button_delete, R.id.button_save, R.id.button_call, R.id.button_plus, R.id.button_minus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_back:
                finish();
                break;
            case R.id.button_delete:
                if (book != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
                    builder.setTitle("Delete")
                            .setMessage("Are you sure you want to delete?")
                            .setIcon(R.drawable.ic_delete_white_48dp)
                            .setCancelable(false)
                            .setNegativeButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            Data.deleteData(book.getId());
                                            //finish();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                break;
            case R.id.button_save:
                String title = editBookTitle.getText().toString();
                if (TextUtils.isEmpty(title)) {
                    getToast("Fill out the name of the book!");
                    break;
                }

                String price = editBookPrice.getText().toString();
                if (TextUtils.isEmpty(price)) {
                    getToast("Fill the price of the book!");
                    break;
                }

                String supplierName = editBookSupplierName.getText().toString();
                if (TextUtils.isEmpty(supplierName)) {
                    getToast("Fill out the name of the book supplier!");
                    break;
                }

                String supplierPhone = editBookSupplierPhoneNumber.getText().toString();
                if (TextUtils.isEmpty(supplierPhone)) {
                    getToast("Fill out the phone number of the bookseller!");
                    break;
                }

                String stringQuantity = editBookQuantity.getText().toString();
                if (TextUtils.isEmpty(String.valueOf(stringQuantity))) {
                    getToast("Fill out the quantity of books!");
                    break;
                }
                int quantity = Integer.parseInt(stringQuantity);
                if (isNewBook) {
                    Data.insertData(title, price, quantity, supplierName, supplierPhone);
                } else {
                    Data.updateData(book.getId(), title, price, quantity, supplierName, supplierPhone);
                }
                finish();
                break;
            case R.id.button_plus:
                currentDetailQuantity++;
                editBookQuantity.setText(String.valueOf(currentDetailQuantity));
                break;
            case R.id.button_minus:
                currentDetailQuantity--;
                if (currentDetailQuantity < 0) {
                    currentDetailQuantity = 0;
                }
                editBookQuantity.setText(String.valueOf(currentDetailQuantity));
                break;
            case R.id.button_call:
                if (!TextUtils.isEmpty(editBookSupplierPhoneNumber.getText().toString())) {
                    Intent intentCall = new Intent(Intent.ACTION_DIAL);
                    intentCall.setData(Uri.parse("tel:" + editBookSupplierPhoneNumber.getText().toString()));
                    startActivity(intentCall);
                }
                break;
        }
    }

    private void getToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

}
