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

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.iterationtechnology.jalarambook.R;
import com.iterationtechnology.jalarambook.model.Message;
import com.iterationtechnology.jalarambook.network.GetProductDataService;
import com.iterationtechnology.jalarambook.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetActivity extends AppCompatActivity {

    EditText txtResetNewPassword,txtResetConformPassword;
    Button btnRePswLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        final GetProductDataService productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);
        final AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        final String email = getIntent().getExtras().getString("email");

        txtResetNewPassword =(EditText) findViewById(R.id.txtResetNewPassword);
        txtResetConformPassword =(EditText) findViewById(R.id.txtResetConformPassword);

        awesomeValidation.addValidation(this, R.id.txtResetNewPassword, "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})", R.string.Psw);
        awesomeValidation.addValidation(this, R.id.txtResetConformPassword, "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})", R.string.Psw);

        btnRePswLink =(Button)findViewById(R.id.btnRePswLink);
        btnRePswLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NewPassword = txtResetNewPassword.getText().toString();
                String ConformPassword = txtResetConformPassword.getText().toString();

                final ProgressDialog dialog = new ProgressDialog(ResetActivity.this);
                dialog.setMessage("Loading...");
                dialog.setCancelable(true);
                dialog.show();

                if (NewPassword.equals(ConformPassword))
                {
                    Call<Message> ResetPasswordCall = productDataService.getResetPasswordData(email,NewPassword);
                    ResetPasswordCall.enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            dialog.dismiss();
                            String status = response.body().getStatus();
                            String message = response.body().getMessage();
                            if(status.equals("1"))
                            {
                                Log.d("message",""+message);
                                Intent i = new Intent(ResetActivity.this, SignInActivity.class);
                                startActivity(i);
                            }
                            else
                            {
                                Log.d("message",""+message);
                            }
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            Toast.makeText(ResetActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    dialog.dismiss();
                    Toast.makeText(ResetActivity.this,"Conform Password not Match",Toast.LENGTH_SHORT).show();
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
