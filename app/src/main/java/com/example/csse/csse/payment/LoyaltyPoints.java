package com.example.csse.csse.payment;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.csse.csse.Model.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoyaltyPoints extends BonusDecorator{

    private FirebaseDatabase db;
    private DatabaseReference accounts;
    private DatabaseReference updateAcc;

    Account ac;


    public String cno;
    private float amt;




    public float setLpoint(float amount) {

        if(amount>=100){
            return 3.0f;
        }
        else if(100>amount && amount>50)
        {
            return 2.0f;
        }
        else
        return 1.0f;


    }

    public LoyaltyPoints(IPaymentMethod mpay) {
        super(mpay);
        db = FirebaseDatabase.getInstance();
        accounts = db.getReference("Accounts");
    }

    public boolean  makePay(final String cardno, final float amnt) {

        cno=cardno;
        amt=amnt;

        tempPay.makePay(cardno,amnt);
        db = FirebaseDatabase.getInstance();

        updateAcc = db.getReference().child("Accounts").child(cardno);
        accounts.child(cno).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ac = dataSnapshot.getValue(Account.class);
                float lop = ac.getLoPoint();
                Log.d("dd", String.valueOf(lop));

                float newLopoint =setLpoint(amt);
                Log.d("dd", String.valueOf(lop+newLopoint));

                updateAcc.child("loPoint").setValue(newLopoint+lop);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // TODO Auto-generated method stub
        return true;


    }


}
