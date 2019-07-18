package com.iterationtechnology.jalarambook.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iterationtechnology.jalarambook.R;
import com.iterationtechnology.jalarambook.model.SendOtp;
import com.iterationtechnology.jalarambook.network.GetProductDataService;
import com.iterationtechnology.jalarambook.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailOTPActivity extends AppCompatActivity {

    EditText txtEEmail,txtEOTP;
    Button btnEResendOTP,btnEVerification,btnESubmitOTP;
    String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_otp);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        final GetProductDataService productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);
        final String mobile = getIntent().getExtras().getString("mobile");

        txtEEmail = (EditText)findViewById(R.id.txtEEmail);
        txtEOTP = (EditText)findViewById(R.id.txtEOTP);
        btnEResendOTP = (Button) findViewById(R.id.btnEResendOTP);
        btnEVerification = (Button) findViewById(R.id.btnEVerification);
        btnESubmitOTP = (Button) findViewById(R.id.btnESubmitOTP);

        txtEOTP.setVisibility(View.GONE);
        btnEResendOTP.setVisibility(View.GONE);
        btnESubmitOTP.setVisibility(View.GONE);

        btnEResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEEmail.setText("");
                btnEVerification.setVisibility(View.VISIBLE);
                txtEOTP.setVisibility(View.GONE);
                btnEResendOTP.setVisibility(View.GONE);
                btnESubmitOTP.setVisibility(View.GONE);
            }
        });

        btnEVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = txtEEmail.getText().toString();

                final ProgressDialog dialog = new ProgressDialog(EmailOTPActivity.this);
                dialog.setMessage("Loading...");
                dialog.setCancelable(true);
                dialog.show();

                Call<SendOtp> SendOtpCall = productDataService.getSendOtpData(email);
                SendOtpCall.enqueue(new Callback<SendOtp>() {
                    @Override
                    public void onResponse(Call<SendOtp> call, Response<SendOtp> response) {
                        dialog.dismiss();
                        String status = response.body().getStatus();
                        String message = response.body().getMessage();
                        if(status.equals("1"))
                        {
                            Log.d("message",""+message);
                            otp = response.body().getOtp();
                            btnEVerification.setVisibility(View.GONE);
                            txtEOTP.setVisibility(View.VISIBLE);
                            btnEResendOTP.setVisibility(View.VISIBLE);
                            btnESubmitOTP.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            Log.d("message",""+message);
                        }
                    }

                    @Override
                    public void onFailure(Call<SendOtp> call, Throwable t) {
                        Toast.makeText(EmailOTPActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnESubmitOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String motp = txtEOTP.getText().toString();
                final ProgressDialog dialog = new ProgressDialog(EmailOTPActivity.this);
                dialog.setMessage("Loading...");
                dialog.setCancelable(true);
                dialog.show();
                if (motp.equals(otp))
                {
                    dialog.dismiss();
                    Intent i = new Intent(EmailOTPActivity.this, SignUpActivity.class);
                    i.putExtra("mobile",mobile);
                    i.putExtra("email",txtEEmail.getText().toString());
                    startActivity(i);
                }
                else
                {
                    dialog.dismiss();
                    Toast.makeText(EmailOTPActivity.this,"Not Send",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}
