package com.monir.phonenumberauthenticationwithotp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.monir.phonenumberauthenticationwithotp.databinding.ActivityVerifyOtpBinding;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {
    private EditText editTextCode;
    private Button buttonVerifyCode;
    FirebaseAuth auth;
    String otpID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        String mobileNo = getIntent().getStringExtra("mobile");
        auth = FirebaseAuth.getInstance();

        editTextCode = findViewById(R.id.editTextCode);
        buttonVerifyCode = findViewById(R.id.buttonVerifyCode);
        manageOtp(mobileNo);


        buttonVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editTextCode.getText().toString().trim())){
                    Toast.makeText(VerifyOTP.this, "Please Enter 6 digit code", Toast.LENGTH_SHORT).show();
                }else if(editTextCode.getText().toString().length() !=6){
                    Toast.makeText(VerifyOTP.this, "Please Enter 6 digit code", Toast.LENGTH_SHORT).show();
                }else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpID,editTextCode.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }

    private void manageOtp(String mobileNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobileNo,
                60,
                TimeUnit.SECONDS,
                this,
                mCallBacks);
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            otpID = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull @org.jetbrains.annotations.NotNull PhoneAuthCredential phoneAuthCredential) {
              signInWithPhoneAuthCredential(phoneAuthCredential);
        }

        @Override
        public void onVerificationFailed(@NonNull @org.jetbrains.annotations.NotNull FirebaseException e) {
            Toast.makeText(VerifyOTP.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
         auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                 if(task.isSuccessful()){
                     startActivity(new Intent(VerifyOTP.this,MainActivity.class));
                 }else {
                     Toast.makeText(VerifyOTP.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                 }
             }
         });
    }
}