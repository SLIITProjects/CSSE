package com.example.csse.csse.payment;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.example.csse.csse.Model.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountManage {

    private FirebaseDatabase db;
    private DatabaseReference accounts;
    private DatabaseReference updateAcc;
    private DatabaseReference getAcc;
    private DatabaseReference users;

    Account ac;
    String x;

    public AccountManage() {

        db = FirebaseDatabase.getInstance();
        accounts=db.getReference("Accounts");
    }

    public void updateRechargeBalance(String cardNo,float reBal,float avlBal){

        updateAcc = db.getReference().child("Accounts").child(cardNo);

        float tot=reBal+ avlBal;
        updateAcc.child("crediteBal").setValue(tot);

    }

    public void updateLoanBalance(final String cardNo, final float amount, float aval){

        updateAcc = db.getReference().child("Accounts").child(cardNo);

        float t=amount+aval;
        updateAcc.child("crediteBal").setValue(t);
        updateAcc.child("loanFlag").setValue("1");
        updateAcc.child("loanAmnt").setValue(30);

    }


    public void isOnLoan(String cardNo, final TextView textView){

        final String[] y = new String[1];

        accounts.child(cardNo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //   Account ac=new Account();
                ac=dataSnapshot.getValue(Account.class);
                //  us=dataSnapshot.getValue(User.class);
                //    Log.d("TAGMY","id111111111111111111111111111111   "+ac.getLoanAmnt());

                 y[0] =ac.getLoanFlag();
             //   Log.d("loannn",y[0]);
                textView.setText(y[0]);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

      //  return y[0];
    }


    public void addonLoan(final String cardNo, final float loanAmount){

        accounts.child(cardNo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //   Account ac=new Account();
                ac=dataSnapshot.getValue(Account.class);
                float tot=loanAmount+ac.getCrediteBal();
                float lonbal=ac.getLoanAmnt();
                float totloan=loanAmount+lonbal;
                ac.setCrediteBal(tot);
                ac.setLoanFlag("1");

                accounts.child(cardNo).setValue(ac);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }





}
