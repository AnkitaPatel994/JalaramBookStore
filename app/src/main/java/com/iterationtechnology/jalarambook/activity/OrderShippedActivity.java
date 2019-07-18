package com.iterationtechnology.jalarambook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iterationtechnology.jalarambook.R;

public class OrderShippedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_shipped);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextView txtInvoiceNoOS = (TextView)findViewById(R.id.txtInvoiceNoOS);
        TextView txtShippingIdOS = (TextView)findViewById(R.id.txtShippingIdOS);
        TextView txtTotalOS = (TextView)findViewById(R.id.txtTotalOS);

        String invoice_no = getIntent().getExtras().getString("invoice_no");
        txtInvoiceNoOS.setText("INVOICE ID : "+invoice_no);

        String shipping_id = getIntent().getExtras().getString("shipping_id");
        txtShippingIdOS.setText("Shipping ID : "+shipping_id);

        String rs = getApplicationContext().getResources().getString(R.string.RS);
        String total = getIntent().getExtras().getString("total");
        txtTotalOS.setText("Order Total : "+rs+" "+total);

        Button btnShippingConShopping = (Button)findViewById(R.id.btnShippingConShopping);
        btnShippingConShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderShippedActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

}
