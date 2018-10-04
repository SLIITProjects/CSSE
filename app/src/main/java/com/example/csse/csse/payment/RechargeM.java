package com.example.csse.csse.payment;

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

import com.example.csse.csse.Model.Account;
import com.example.csse.csse.Model.User;
import com.example.csse.csse.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class RechargeM extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference user;
    private String codesend;

    private  TextView card;
    private EditText mobileNo, amount,code;
    private Button getB,rechB;
    private String userKey;
    private String cardno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_m);


       userKey = getIntent().getExtras().getString("postId");
       cardno =getIntent().getExtras().getString("postCard");

        db = FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        user = db.getReference("User");

        card=(TextView)findViewById(R.id.accno);
        mobileNo = (EditText) findViewById(R.id.mno);
        amount = (EditText) findViewById(R.id.aMont);
        code=(EditText)findViewById(R.id.codeEnterd);
        getB = (Button) findViewById(R.id.abutton);
        rechB =(Button) findViewById(R.id.rechargeB);

      card.setText(cardno);

        getB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                senverificationcode();
            }
        });

        rechB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verifyRecharge();
                verifyCode();
            }
        });
    }

//    private void verifyRecharge() {
//
//        String enterdCode=code.getText().toString();
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codesend, enterdCode);
//        signInWithPhoneAuthCredential(credential);
//    }
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//
//                            Toast.makeText(getApplicationContext(),"Recharge Successfull",Toast.LENGTH_LONG).show();
//                        } else {
//
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                // The verification code entered was invalid
//                                Toast.makeText(getApplicationContext(),"Sorry Error",Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    }
//                });
//    }
//
//
//    private void senverificationcode() {
//
//        String umobile = mobileNo.getText().toString();
//        final String uamount = amount.getText().toString();
//    Log.d("tt",umobile);
//        if(umobile.isEmpty()){
//            mobileNo.setError("Number is Required");
//            mobileNo.requestFocus();
//            return;
//        }
//
//        if(umobile.length()<10){
//            mobileNo.setError("Please Enter Valid Mobile No");
//            mobileNo.requestFocus();
//            return;
//        }
//
//
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                umobile,        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                this,               // Activity (for callback binding)
//                mCallbacks);        // OnVerificationStateChangedCallbacks
//
//
//    }
//    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        @Override
//        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//
//        }
//
//        @Override
//        public void onVerificationFailed(FirebaseException e) {
//
//        }
//
//        @Override
//        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//            codesend=s;
//        }
//    };
//
//}

    private void verifyCode(){
        String enterdCode=code.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codesend, enterdCode);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            float amnt= Float.parseFloat(amount.getText().toString());

                                AccountManage ac=new AccountManage();
                                ac.updateBalance(userKey,amnt);

                              Log.d("ee", "Succss Pay");
                              Toast.makeText(RechargeM.this, "success pay", Toast.LENGTH_LONG).show();
                          //  Intent intent = new Intent(OtpActivity.this, MainActivity.class);
                         //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                         //   startActivity(intent);

                        } else {
                            Toast.makeText(RechargeM.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            Log.d("ee", task.getException().getMessage());
                        }
                    }

                });
    }

    private void senverificationcode(){

        String umobile = mobileNo.getText().toString();

        if(umobile.isEmpty()){
            mobileNo.setError("Number is Required");
            mobileNo.requestFocus();
            return;
        }

        if(umobile.length()<10){
            mobileNo.setError("Please Enter Valid Mobile No");
            mobileNo.requestFocus();
            return;
        }


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                umobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codesend = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null){
              //  progressBar.setVisibility(View.VISIBLE);
                verifyCode();
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(RechargeM.this, e.getMessage(),Toast.LENGTH_LONG).show();
            Log.d("ee",e.getMessage());
        }
    };
}