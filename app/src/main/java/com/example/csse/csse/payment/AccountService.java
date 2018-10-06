package com.example.csse.csse.payment;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csse.csse.DialogBox.LoanDialog;
import com.example.csse.csse.Model.Account;
import com.example.csse.csse.Model.PaymentHistroty;
import com.example.csse.csse.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.csse.csse.R;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountService extends AppCompatActivity {

    //Button for Recharge Account
        private Button Raccount,LoanB;
        private TextView cardNo,Balance,loytyPoint,Uname,Ul;

    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference accounts;
    private DatabaseReference getUser;

    private DatabaseReference payemthis;

    Account ac;
    User us;
    AccountManage a;

    private String userKey="968765466V";
    private String ucard;
    private String avlBal;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        a=new AccountManage();
        ac=new Account();
        db = FirebaseDatabase.getInstance();
        accounts = db.getReference("Accounts");

//        users=db.getReference("User");
        payemthis=db.getReference("PaymentHistory");
        Raccount=(Button) findViewById(R.id.Rmbutton);
        LoanB=(Button) findViewById(R.id.getloanb);
        cardNo=(TextView) findViewById(R.id.acno);
        Balance=(TextView) findViewById(R.id.bal);
        loytyPoint=(TextView) findViewById(R.id.lPoint);
        Ul=(TextView)findViewById(R.id.UnameText);



        LoanB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                a.isOnLoan(userKey,Ul);
              //  openLoanDialog();

             showAlertLoan();
            }
        });

        openRecharge();  //recharge method
       // setStatus();
        setDetailsStatus(userKey); //set details
    }

//    private void showAccountStatus(DataSnapshot dataSnapshot) {
//        for(DataSnapshot ds :dataSnapshot.getChildren()){
//           Account ac=new Account();
//           ac.setCardNo(ds.getValue(Account.class).getCardNo());
//            //String amount = (String) dataSnapshot.child("cardNo").getValue().toString();
//
//
//         //   Log.d(tag,"fff"+ac.getCardNo());
//         //   ac.setCrediteBal();
//           // ac.setLoPoint();
//                //ac=ds.getValue(Account.class);
//                ac.getCardNo();
//            Log.d("TAGMY","id111111111111111111111111111111"+ac.getCardNo());
//            cardNo.setText(ac.getCardNo());
//        }
//
//    }

//    public void setStatus(){
//      //  cardNo.setText("333");
//
//        Balance.setText("365");
//        loytyPoint.setText("3");
//    }

   public void openLoanDialog(){
    LoanDialog lnd =new LoanDialog();
    lnd.show(getSupportFragmentManager(),"example");
//
      // update();
       a.isOnLoan("968765466V",Uname);
       Log.d("loannn","huhuhu");


   }

    public void openRecharge(){

            Raccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(AccountService.this,RechargeM.class);
                    intent.putExtra("postId",userKey);
                    intent.putExtra("postCard",ucard);
                    intent.putExtra("postBal",avlBal);
                    startActivity(intent);
                }
            });

        }

        public void test(){
        //    String id =accounts.push().getKey();
               // String id="TuaePLTgskOMUuBAGWZPHEShEsl1";
               // final String userID =FirebaseAuth.getInstance().getCurrentUser().getUid();
             //   Account account=new Account(id,"abc1","aoo1",35.0f,3.5f,"1",10.0f);
          //      accounts.child(id).setValue(account);

            }

    public void showAlertLoan() {

        AlertDialog.Builder alert =new AlertDialog.Builder(this);
        alert.setTitle("Loan Requset");
        alert.setMessage("Do You need 30 LKR Loan");
        alert.setCancelable(false);

        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(Ul.getText().toString().equalsIgnoreCase("1")){
                  // a.updateRechargeBalance(userKey,20);
                    Toast.makeText(AccountService.this, "Sorry Your Already got a Loan", Toast.LENGTH_SHORT).show();
                }
                else if(Ul.getText().toString().equalsIgnoreCase("0")){
                    float avaiBal=Float.valueOf(Balance.getText().toString());
                    a.updateLoanBalance(userKey, 30,avaiBal);
                    Toast.makeText(AccountService.this, "Succussfully Your getting 30LKR Thank you", Toast.LENGTH_SHORT).show();

                }


            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Toast.makeText(AccountService.this, "Cancel", Toast.LENGTH_SHORT).show();
                //finish();
            }
        });
        alert.create().show();
    }

    public boolean isBalanceSufficient(String cardNumber){
        return true;
    }

    public  void applyForLoan(String cardNo){

    }

    public void recharge(String cardNo,float amount,String paymentMethod){

    }

    private void setDetailsStatus(String id){


        accounts.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Account ac=new Account();
               ac=dataSnapshot.getValue(Account.class);
              //  us=dataSnapshot.getValue(User.class);
             Log.d("TAGMY","id111111111111111111111111111111   "+ac.getLoanAmnt());
                ucard=ac.getCardNo();
                avlBal= String.valueOf(ac.getCrediteBal());
                cardNo.setText(ac.getCardNo());
                Balance.setText(String.valueOf(ac.getCrediteBal()));
                loytyPoint.setText(String.valueOf(ac.getLoPoint()));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void paymethodGet(View view) {

        boolean b;

     //   IPaymentMethod p =new LoyaltyPoints(new PaymentMethod());
        IPaymentMethod p =new PaymentMethod();
        b=p.makePay(userKey,10.5f);

     //   Log.d("ddreal", String.valueOf(b));


    }



}
