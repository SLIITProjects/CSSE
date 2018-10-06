package com.example.csse.csse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.csse.csse.Common.Common;
import com.example.csse.csse.Model.Card;
import com.example.csse.csse.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

//In this class, get user NIC and password then check it with the database and login user
public class Sign_in extends AppCompatActivity {
    MaterialEditText editUsername,editPassword;
    Button btnSignIn;
    User user;
    Card card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editPassword = (MaterialEditText)findViewById(R.id.editPassword);
        editUsername = (MaterialEditText)findViewById(R.id.editUsername);
        btnSignIn = (Button)findViewById(R.id.btn_signIn);

        //initialize firebase database
        FirebaseDatabase databae = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = databae.getReference("User");
        final DatabaseReference table_card = databae.getReference("Card");

        //When user click sign in button it will redirect to the main screen.
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mdialog = new ProgressDialog(Sign_in.this);
                mdialog.setMessage("Please waiting....");
                mdialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshotuser) {

                        //check if user exist in the database
                        if (dataSnapshotuser.child(editUsername.getText().toString()).exists()) {
                            mdialog.dismiss();
                            //Get User Information

                            user = dataSnapshotuser.child(editUsername.getText().toString()).getValue(User.class);

                            user.setNic(editUsername.getText().toString());
                            if (user.getPassword().equals(editPassword.getText().toString())) {
                                table_card.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.child(editUsername.getText().toString()).exists()){
                                            Intent homeIntent = new Intent(Sign_in.this,Home.class);
                                            Common.currentUser = user;
                                            startActivity(homeIntent);
                                        }else{
                                            Intent LoanIntent = new Intent(Sign_in.this,InitLoan.class);
                                            LoanIntent.putExtra("Mobile",user.getMobile());
                                            LoanIntent.putExtra("NIC",editUsername.getText().toString());
                                            startActivity(LoanIntent);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            } else {
                                Toast.makeText(Sign_in.this, "Wrong Password or Phone number!", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            mdialog.dismiss();
                            Toast.makeText(Sign_in.this, "User does not exist!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
