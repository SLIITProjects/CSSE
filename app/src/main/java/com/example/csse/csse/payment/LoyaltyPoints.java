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

    Account ac;
boolean b;

    private float lpoint;
    public String cno;
    private float amt;





    public float getLpoint() {
        return lpoint;
        //db input card no;
    }

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

    public void Loyty(){
        accounts.child(cno).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ac = dataSnapshot.getValue(Account.class);
                float lop = ac.getLoPoint();
                Log.d("dd", String.valueOf(lop));

                float newLopoint =setLpoint(amt);
                Log.d("dd", String.valueOf(lop+newLopoint));

                ac.setLoPoint(lop+newLopoint);
                accounts.child(cno).setValue(ac);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public boolean  makePay(final String cardno, final float amnt) {

        cno=cardno;
        amt=amnt;
        b=tempPay.makePay(cardno,amnt);


        Loyty();


        // TODO Auto-generated method stub
        return false;

    }


}
