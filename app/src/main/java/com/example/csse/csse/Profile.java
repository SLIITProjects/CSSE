package com.example.csse.csse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.csse.csse.Common.Common;

public class Profile extends AppCompatActivity {

    TextView name,location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        location = (TextView)findViewById(R.id.location);
        location.setText("Kandy,Sri lanka");
    }
}
