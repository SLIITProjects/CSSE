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
        private Button Raccount;
        private Button LoanB;

        private TextView cardNo,Balance,loytyPoint,Uname;


   private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference accounts;
    private DatabaseReference users;
    private DatabaseReference payemthis;
  //  PaymentHistroty ps;
   Account ac;
User us;
    AccountManage a;
 // private String ud="-LNJ1nEQIhIoWiED8RVt";
private String userKey="968765466V";
   private String ucard;
//    private String userKey="5588";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
//ps=new PaymentHistroty();
a=new AccountManage();
       // accounts=FirebaseDatabase.getInstance().getReference("accounts");
        db = FirebaseDatabase.getInstance();
        accounts = db.getReference("Accounts");
       // FirebaseUser user=auth.getCurrentUser();
       // userId=user.getUid();
        users=db.getReference("User");
        payemthis=db.getReference("PaymentHistory");
        Raccount=(Button) findViewById(R.id.Rmbutton);
        LoanB=(Button) findViewById(R.id.getloanb);
        cardNo=(TextView) findViewById(R.id.acno);
        Balance=(TextView) findViewById(R.id.bal);
        loytyPoint=(TextView) findViewById(R.id.lPoint);
        Uname=(TextView)findViewById(R.id.UnameText);

   // ac=new Account();
        LoanB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
//    LoanDialog lnd =new LoanDialog();
//    lnd.show(getSupportFragmentManager(),"example");
//

       a.isOnLoan("968765466V");
       Log.d("loannn","huhuhu");


   }

    public void openRecharge(){

            Raccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(AccountService.this,RechargeM.class);
                    intent.putExtra("postId",userKey);
                    intent.putExtra("postCard",ucard);
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
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


              //  if(b==true) {
                   // test();
                  //  String id =accounts.push().getKey();
                   String id="968765200V";
                    // final String userID =FirebaseAuth.getInstance().getCurrentUser().getUid();
                  //  Account account=new Account(id,"abc2",35.0f,3.5f,"1",10.0f);
                // accounts.child(id).setValue(account);
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                Time time = new Time();
                time.setToNow();
                String t=time.hour+":"+time.minute;
                PaymentHistroty ps =new PaymentHistroty(id,"abc2",date,t,"1(Colombo-Kandy)",150);
                payemthis.child(id).setValue(ps);
                    Toast.makeText(AccountService.this, "Succefully", Toast.LENGTH_SHORT).show();
              //  }
              // / else
                  //  Toast.makeText(AccountService.this, "You are already got a Loan", Toast.LENGTH_SHORT).show();
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
    public boolean isBalanceSufficient(String cardNumber){
        return true;
    }
//    public boolean isOnLoan(String cardNo) {
//
//        if (cardNo=="55")
//        return true;
//        else
//            return false;
//    }
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

        Log.d("ddreal", String.valueOf(b));


    }

}
