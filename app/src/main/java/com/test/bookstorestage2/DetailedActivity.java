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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailedActivity extends AppCompatActivity {

    public static final String DETAIL = "detail";
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

        intent = getIntent();
        if (intent != null) {
            this.book = (Book) intent.getParcelableExtra(DETAIL);
            if (book != null) {
                isNewBook = false;
                editBookTitle.setText(book.getBookName());
                editBookPrice.setText(book.getPrice());
                editBookSupplierName.setText(book.getSupplierName());
                editBookSupplierPhoneNumber.setText(book.getSupplierPhone());
                currentDetailQuantity = book.getQuantity();
                editBookQuantity.setText(String.valueOf(currentDetailQuantity));
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailedActivity.this);
                    builder.setTitle(R.string.delete)
                            .setMessage(R.string.are_you_sure)
                            .setIcon(R.drawable.ic_delete_blue_900_48dp)
                            .setCancelable(false)
                            .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton(R.string.ok,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            Data.deleteData(book.getId());
                                            finish();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                break;
            case R.id.button_save:
                String title = editBookTitle.getText().toString();
                if (TextUtils.isEmpty(title)) {
                    getToast(getString(R.string.fill_out_the_name_of_the_book));
                    break;
                }

                String price = editBookPrice.getText().toString();
                if (TextUtils.isEmpty(price)) {
                    getToast(getString(R.string.fill_the_price_of_the_book));
                    break;
                }

                String supplierName = editBookSupplierName.getText().toString();
                if (TextUtils.isEmpty(supplierName)) {
                    getToast(getString(R.string.fill_out_the_name_of_the_book_supplier));
                    break;
                }

                String supplierPhone = editBookSupplierPhoneNumber.getText().toString();
                if (TextUtils.isEmpty(supplierPhone)) {
                    getToast(getString(R.string.fill_out_the_phone_number_of_the_bookseller));
                    break;
                }

                String stringQuantity = editBookQuantity.getText().toString();
                if (TextUtils.isEmpty(String.valueOf(stringQuantity))) {
                    getToast(getString(R.string.fill_out_the_quantity_of_books));
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
                    intentCall.setData(Uri.parse(getString(R.string.tel) + editBookSupplierPhoneNumber.getText().toString()));
                    startActivity(intentCall);
                } else {
                    getToast(getString(R.string.enter_the_supplier_phone_number));
                }
                break;
        }
    }

    private void getToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
