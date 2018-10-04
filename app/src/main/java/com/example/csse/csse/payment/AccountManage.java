package com.example.csse.csse.payment;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.csse.csse.Model.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountManage {

    private FirebaseDatabase db;
    private DatabaseReference accounts;
    private DatabaseReference users;

    Account ac;
    String x;

    public AccountManage() {

        db = FirebaseDatabase.getInstance();
        accounts = db.getReference("Accounts");
    }


    public void updateBalance(final String cardNo, final float amount){

        accounts.child(cardNo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //   Account ac=new Account();
                ac=dataSnapshot.getValue(Account.class);
                //  us=dataSnapshot.getValue(User.class);
                //    Log.d("TAGMY","id111111111111111111111111111111   "+ac.getLoanAmnt());
                float tot=amount+ac.getCrediteBal();
                ac.setCrediteBal(tot);
                accounts.child(cardNo).setValue(ac);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String isOnLoan(String cardNo){

        final String[] y = new String[1];

        accounts.child(cardNo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //   Account ac=new Account();
                ac=dataSnapshot.getValue(Account.class);
                //  us=dataSnapshot.getValue(User.class);
                //    Log.d("TAGMY","id111111111111111111111111111111   "+ac.getLoanAmnt());

                 y[0] =ac.getLoanFlag();
                Log.d("loannn",y[0]);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return y[0];
    }
}
