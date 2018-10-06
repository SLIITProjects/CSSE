package com.example.csse.csse.payment;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
    private DatabaseReference upDateBal;
    Account ac;


    public PaymentMethod(){

    }


    @Override
    public boolean makePay(final String cardno, final float amnt) {
        db = FirebaseDatabase.getInstance();
        accounts = db.getReference("Accounts");

        upDateBal = db.getReference().child("Accounts").child(cardno);

        accounts.child(cardno).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ac = dataSnapshot.getValue(Account.class);
                float abal = ac.getCrediteBal();
                Log.d("dd", String.valueOf(abal));

                if (abal > amnt) {

                    boolean x=true;
       //             Log.d("dd(if)", String.valueOf(x));

                    float newBal=abal-amnt;
                    Log.d("dd(new bal)", String.valueOf(newBal));
                    upDateBal.child("crediteBal").setValue(newBal);

                }
                else{

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

return true;
    }
}
