package com.example.csse.csse.payment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.csse.csse.R;

import org.w3c.dom.Text;

public class RechargeM extends AppCompatActivity {

        private EditText mobileNo;
        private EditText amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_m);

        mobileNo=(EditText)findViewById(R.id.mno);
        amount=(EditText)findViewById(R.id.aMont);
    }





}
