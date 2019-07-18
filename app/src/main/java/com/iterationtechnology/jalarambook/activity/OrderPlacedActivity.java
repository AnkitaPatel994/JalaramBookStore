package com.iterationtechnology.jalarambook.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.iterationtechnology.jalarambook.R;
import com.iterationtechnology.jalarambook.model.Message;
import com.iterationtechnology.jalarambook.network.GetProductDataService;
import com.iterationtechnology.jalarambook.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderPlacedActivity extends AppCompatActivity {

    String invoice_no;
    GetProductDataService productDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);
        invoice_no = getIntent().getExtras().getString("invoice_no");
        String ordersta = getIntent().getExtras().getString("ordersta");

        TextView textView2 = (TextView)findViewById(R.id.textView2);
        textView2.setText(ordersta);

        TextView txtInvoiceNoOP = (TextView)findViewById(R.id.txtInvoiceNoOP);
        txtInvoiceNoOP.setText("INVOICE ID : "+invoice_no);

        Button btnCancelOrder = (Button)findViewById(R.id.btnCancelOrder);
        Button btnConShopping = (Button)findViewById(R.id.btnConShopping);

        btnConShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderPlacedActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(OrderPlacedActivity.this,android.R.style.Theme_Light_NoTitleBar);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.delete_wishlist_pro_dialog);
                dialog.setCancelable(true);

                TextView txtWishlistCancel = (TextView)dialog.findViewById(R.id.txtWishlistCancel);
                TextView txtWishlistRemove = (TextView)dialog.findViewById(R.id.txtWishlistRemove);

                txtWishlistCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                txtWishlistRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    Call<Message> DeleteOrderCall = productDataService.getDeleteOrderData(invoice_no);
                    DeleteOrderCall.enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            String Status = response.body().getStatus();
                            String message = response.body().getMessage();
                            if (Status.equals("1"))
                            {
                                Log.d("message",""+message);
                                Intent i = new Intent(OrderPlacedActivity.this,MyOrderActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else
                            {
                                Log.d("message",""+message);
                            }
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            Toast.makeText(OrderPlacedActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    }
                });

                dialog.show();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    /*private void CancelOrder(String order_id) {

        Call<Message> DeleteOrderCall = productDataService.getDeleteOrderData(order_id);
        DeleteOrderCall.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                String Status = response.body().getStatus();
                String message = response.body().getMessage();
                if (Status.equals("1"))
                {
                    Log.d("message",""+message);
                    Intent i = new Intent(OrderPlacedActivity.this,OrderPlacedActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Log.d("message",""+message);
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(OrderPlacedActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }*/

}
