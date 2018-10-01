package com.example.csse.csse.payment;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.csse.csse.Model.Account;
import com.example.csse.csse.Model.User;
import com.example.csse.csse.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class RechargeM extends AppCompatActivity {

    private FirebaseDatabase db;
    private DatabaseReference user;


    private EditText mobileNo, amount;
    private Button getB;
    String userKey="968765466V";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_m);


     //   userKey = getIntent().getExtras().getString("postId");

        db = FirebaseDatabase.getInstance();
        user = db.getReference("User");

        mobileNo = (EditText) findViewById(R.id.mno);
        amount = (EditText) findViewById(R.id.aMont);
        getB = (Button) findViewById(R.id.abutton);

        getB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  showVerification();
            }
        });
    }


    public void showVerification() {

        String umobile = mobileNo.getText().toString();
        final String uamount = amount.getText().toString();

        // Log.d("Tag",mobileNo.getText().toString());

        //mobileNoVerify(userKey, umobile);

      //  final String uno = uMno;
        user.child(userKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    User u =new User();
                    Account ac=new Account();


                u = dataSnapshot.getValue(User.class);
                String umNo = u.getMobile();
                ac.updateBal(Float.parseFloat(uamount));
                Log.d("Tagg  0000  ", umNo);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

//    public boolean mobileNoVerify(String UID, String uMno) {
//
//
//
//    }

}
