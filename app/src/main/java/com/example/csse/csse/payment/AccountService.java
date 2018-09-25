package com.example.csse.csse.payment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csse.csse.R;

public class AccountService extends AppCompatActivity {

    //Button for Recharge Account
        private Button Raccount;
//        private Button x;

        private TextView cardNo;
        private TextView Balance;
        private TextView loytyPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Raccount=(Button) findViewById(R.id.Rmbutton);

        cardNo=(TextView) findViewById(R.id.acno);
        Balance=(TextView) findViewById(R.id.bal);
        loytyPoint=(TextView) findViewById(R.id.lPoint);

        openRecharge();  //recharge method
        setStatus();
    }
    public void setStatus(){
        cardNo.setText("333");

        Balance.setText("365");
        loytyPoint.setText("3");
    }



        public void openRecharge(){

            Raccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(AccountService.this,RechargeM.class);
                    startActivity(intent);
                }
            });

        }


    public void showAlertLoan(View view) {
        AlertDialog.Builder alert =new AlertDialog.Builder(this);
        alert.setTitle("Loan Requset");
        alert.setMessage("Do You need 30 LKR Loan");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                boolean b=isOnLoan("45");

                if(b==true)
                Toast.makeText(AccountService.this, "Succefully", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(AccountService.this, "You are already got a Loan", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Toast.makeText(AccountService.this, "You fkk", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }
    public boolean updateBalance(String cardNo, float amount){

            return true;

    }

    public boolean isBalanceSufficient(String cardNumber){
        return true;
    }
    public boolean isOnLoan(String cardNo)
    {
        if (cardNo=="55")
        return true;
        else
            return false;
    }
    public  void applyForLoan(String cardNo){

    }

    public void recharge(String cardNo,float amount,String paymentMethod){

    }



}
