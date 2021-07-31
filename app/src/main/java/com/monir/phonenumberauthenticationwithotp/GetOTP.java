package com.monir.phonenumberauthenticationwithotp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;
import com.monir.phonenumberauthenticationwithotp.databinding.ActivityGetOtpBinding;

public class GetOTP extends AppCompatActivity {

    private CountryCodePicker ccp;
    private EditText editTextPhoneNumber;
    private Button buttonGetOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_otp);

        ccp = findViewById(R.id.cpp);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        buttonGetOTP = findViewById(R.id.buttonGetOTP);

        ccp.registerCarrierNumberEditText(editTextPhoneNumber);



        buttonGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetOTP.this,VerifyOTP.class);
                intent.putExtra("mobile",ccp.getFullNumberWithPlus().trim());
                startActivity(intent);
            }
        });

    }
}