package com.example.csse.csse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csse.csse.Common.Common;
import com.example.csse.csse.Model.Account;
import com.example.csse.csse.Model.Card;
import com.example.csse.csse.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.concurrent.TimeUnit;

public class InitLoan extends AppCompatActivity {
    MaterialEditText editCode,edtPhone;
    Button verify,send;
    TextView txt;
    String nic;
    String currentUsercard;
    String phoneNumber, otp;
    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_loan);

        findViews();
        StartFirebaseLogin();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                phoneNumber=intent.getStringExtra("Mobile") ;
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+94"+phoneNumber,                     // Phone number to verify
                        60,                           // Timeout duration
                        TimeUnit.SECONDS,                // Unit of timeout
                        InitLoan.this,        // Activity (for callback binding)
                        mCallback);                      // OnVerificationStateChangedCallbacks
            }
        });

       verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp=editCode.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
                SigninWithPhone(credential);
            }
        });
    }
    private void SigninWithPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        final DatabaseReference table_card = database.getReference("Card");
                        final DatabaseReference table_account = database.getReference("Accounts");
                        if (task.isSuccessful()) {
                            Intent intent = getIntent();
                            nic=intent.getStringExtra("NIC");
                            currentUsercard=generateCardNumber();

                            table_card.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.child(nic).exists()){
                                       finish();
                                    }else{
                                        Card card = new Card("Mobile",nic,otp,currentUsercard);
                                        table_card.child(nic).setValue(card);

                                        Account account = new Account(nic,currentUsercard, 100,0,"0",0);
                                        table_account.child(nic).setValue(account);

                                        Intent signIn = new Intent(InitLoan.this,Sign_in.class);
                                        startActivity(signIn);
                                    }
                                }



                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(InitLoan.this, "Cannot sign up!!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });

                            finish();
                        } else {
                            Toast.makeText(InitLoan.this,"Incorrect OTP",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void findViews() {
        send=findViewById(R.id.btn_send);
        verify=findViewById(R.id.btn_verify);
        editCode=findViewById(R.id.editOtp);
        txt = findViewById(R.id.edit_txt);
    }
    private void StartFirebaseLogin() {
        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(InitLoan.this,"verification completed",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(InitLoan.this,"verification failed",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                //super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                Toast.makeText(InitLoan.this,"Code sent",Toast.LENGTH_SHORT).show();
            }
        };
    }
    private String generateCardNumber(){
        long timeSeed = System.nanoTime(); // to get the current date time value

        double randSeed = Math.random() * 1000; // random number generation

        long midSeed = (long) (timeSeed * randSeed); // mixing up the time and

        String s = midSeed + "";
        String subStr = s.substring(0, 9);
        return subStr;
    }

}
