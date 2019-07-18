package com.iterationtechnology.jalarambook.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import com.iterationtechnology.jalarambook.R;

public class PDFActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        String invoice_no = getIntent().getExtras().getString("invoice_no");

        WebView wvPDF = (WebView) findViewById(R.id.wvPDF);
        wvPDF.getSettings().setJavaScriptEnabled(true);
        String pdf = "http://jalarambookstore.com/order_details.php?order_no="+invoice_no;
        wvPDF.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdf);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
