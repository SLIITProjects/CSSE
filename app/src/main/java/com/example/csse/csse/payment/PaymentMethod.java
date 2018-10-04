package com.example.csse.csse.payment;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.example.csse.csse.DialogBox.LoanDialog;
import com.example.csse.csse.Model.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentMethod implements IPaymentMethod{

    private FirebaseDatabase db;
    private DatabaseReference accounts;

    Account ac;

    private boolean b;

    public PaymentMethod(){


    }

    public void setval(boolean b){
        this.b=b;
    }

    public boolean getval(){
        return b;
    }

    @Override
    public boolean makePay(final String cardno, final float amnt) {
        db = FirebaseDatabase.getInstance();
        accounts = db.getReference("Accounts");




        accounts.child(cardno).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  boolean[] i = {false};
                ac = dataSnapshot.getValue(Account.class);
                float abal = ac.getCrediteBal();
                Log.d("dd", String.valueOf(abal));
                if (abal > amnt) {
                   // i[0] = true;
                    boolean x=true;
                    setval(x);
                    Log.d("dd(if)", String.valueOf(x));

                    float newBal=abal-amnt;
                    Log.d("dd(new bal)", String.valueOf(newBal));
                    ac.setCrediteBal(newBal);
                    accounts.child(cardno).setValue(ac);
                    //Log.d("dd(newbal)", String.valueOf());



                }
                else{

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.d("ddout", String.valueOf(getval()));
        return b;

    }
}
