package com.example.csse.csse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.csse.csse.Model.Journey;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add_Journey extends AppCompatActivity {

 EditText editName;
 Button button;
 Spinner spinner;
  DatabaseReference databaseJourney;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add__journey);

    databaseJourney=FirebaseDatabase.getInstance().getReference("Start_End_Journey");

    editName=(EditText)findViewById(R.id.editTextName);
    button=(Button) findViewById(R.id.confirm);
    spinner=(Spinner) findViewById(R.id.spinner);





    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
addJourney();
      }
    });




  }


  private void addJourney(){

    String name=editName.getText().toString().trim();
    String genere=spinner.getSelectedItem().toString();
String id=databaseJourney.push().getKey();



    Journey journey=new Journey(id,name,genere);

databaseJourney.child(id).setValue(journey);

    Toast.makeText(this, "Journey Added", Toast.LENGTH_SHORT).show();
  }

}
