package com.example.csse.csse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add_Journey extends AppCompatActivity {

  private Button mAddJourney;
  FirebaseDatabase databae = FirebaseDatabase.getInstance();
  final DatabaseReference table_user = databae.getReference("User");
  private FirebaseDatabase mRef;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add__journey);


    mAddJourney =(Button)findViewById(R.id.confirm);


  }
}
