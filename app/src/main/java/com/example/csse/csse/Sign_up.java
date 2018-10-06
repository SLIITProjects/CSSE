package com.example.csse.csse;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class Sign_up extends AppCompatActivity {
    MaterialEditText editMobile,editName,editDob,editPassword,editNIC,edtotp;
    Button signUp;
    String passNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Intent intent = getIntent();
        passNumber = intent.getStringExtra("Pass");

        //initialize views
        editName = (MaterialEditText)findViewById(R.id.editName);
        editMobile = (MaterialEditText)findViewById(R.id.editMobile);
        editDob = (MaterialEditText)findViewById(R.id.editDob);
        editPassword = (MaterialEditText)findViewById(R.id.editPassword);
        editNIC = (MaterialEditText)findViewById(R.id.editNIC);
        signUp = (Button)findViewById(R.id.btn_register);

        //get data from home activity and set then in to views(For local customers)
         editName.setText(intent.getStringExtra("Name"));
         editMobile.setText(intent.getStringExtra("Phone"));
         editDob.setText(intent.getStringExtra("DOB"));
         editNIC.setText(intent.getStringExtra("NIC"));
         editPassword.setText("");


        //initialize firebase database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mdialog = new ProgressDialog(Sign_up.this);
                mdialog.setMessage("Please waiting....");
                mdialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(editMobile.getText().toString().length()!=10){
                            mdialog.dismiss();
                            Toast.makeText(Sign_up.this, "Invalid phone number!", Toast.LENGTH_SHORT).show();
                        }if(editNIC.getText().toString().trim().matches("^[0-9]{9}[vVxX]$")){
                            mdialog.dismiss();
                            Toast.makeText(Sign_up.this, "Invalid NIC!", Toast.LENGTH_SHORT).show();
                        }
                        if (dataSnapshot.child(editNIC.getText().toString()).exists()){
                            mdialog.dismiss();
                            Toast.makeText(Sign_up.this, "User already exists!", Toast.LENGTH_SHORT).show();
                        }else if( editName.getText().toString().isEmpty()|| editMobile.getText().toString().isEmpty() ||
                                editDob.getText().toString().isEmpty() || editNIC.getText().toString().isEmpty() ||
                                editPassword.getText().toString().isEmpty() ){
                            mdialog.dismiss();
                            Toast.makeText(Sign_up.this, "Fields must not be empty!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            mdialog.dismiss();
                            User user = new User(editName.getText().toString(),editDob.getText().toString(),editNIC.getText().toString(),
                                    editPassword.getText().toString(),editMobile.getText().toString());

                            table_user.child(editNIC.getText().toString()).setValue(user);
                            Intent Init = new Intent(Sign_up.this,InitLoan.class);
                            Init.putExtra("Mobile",editMobile.getText().toString());
                            Init.putExtra("NIC",editNIC.getText().toString());
                            startActivity(Init);
                        }
                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Sign_up.this, "Cannot sign up!!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });


            }
        });
    }
}
