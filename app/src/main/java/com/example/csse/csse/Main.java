package com.example.csse.csse;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.csse.csse.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Main extends AppCompatActivity {

    Button btnRegister,btnSignIn;
    RelativeLayout rootLayout;

    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference users;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                                        .setDefaultFontPath("fonts/Proxima_Nova.ttf")
                                        .setFontAttrId(R.attr.fontPath)
                                        .build());


        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        btnRegister = (Button)findViewById(R.id.btn_register);
        btnSignIn = (Button)findViewById(R.id.btn_signIn);
        rootLayout = (RelativeLayout)findViewById(R.id.rootLayout);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterDialog();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog();
            }
        });
    }

    private void showLoginDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("SIGN IN");
        dialog.setMessage("Please use email and password to login");

        LayoutInflater inflater = LayoutInflater.from(this);
        View login_layout = inflater.inflate(R.layout.layout_login,null);

        final MaterialEditText u_password = login_layout.findViewById(R.id.u_pass);
        final MaterialEditText u_email = login_layout.findViewById(R.id.u_email);

        dialog.setView(login_layout);

        dialog.setPositiveButton("SIGN IN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(TextUtils.isEmpty(u_password.getText().toString())){
                    Snackbar.make(rootLayout,"Please enter Password",Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if(TextUtils.isEmpty(u_email.getText().toString())){
                    Snackbar.make(rootLayout,"Please enter Email",Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if(u_password.getText().toString().length()<6){
                    Snackbar.make(rootLayout,"Password is too short",Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                auth.signInWithEmailAndPassword(u_email.getText().toString(),u_password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                           startActivity(new Intent(Main.this,Welcome.class));
                           finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(rootLayout,"Fail "+e.getMessage(),Snackbar.LENGTH_SHORT)
                                .show();
                    }
                });


            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void showRegisterDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("REGISTER");
        dialog.setMessage("Please fill the form to register");

        LayoutInflater inflater = LayoutInflater.from(this);
        View register_layout = inflater.inflate(R.layout.layout_register,null);

        final MaterialEditText u_fname = register_layout.findViewById(R.id.u_name);
        final MaterialEditText u_password = register_layout.findViewById(R.id.u_pass);
        final MaterialEditText u_phone = register_layout.findViewById(R.id.u_mobile);
        final MaterialEditText u_nic = register_layout.findViewById(R.id.u_nic);
        final MaterialEditText u_dob = register_layout.findViewById(R.id.u_dob);
        final MaterialEditText u_email = register_layout.findViewById(R.id.u_email);

        dialog.setView(register_layout);

        dialog.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(TextUtils.isEmpty(u_fname.getText().toString())){
                    Snackbar.make(rootLayout,"Please enter Full name",Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if(TextUtils.isEmpty(u_password.getText().toString())){
                    Snackbar.make(rootLayout,"Please enter Password",Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if(TextUtils.isEmpty(u_phone.getText().toString())){
                    Snackbar.make(rootLayout,"Please enter Phone number",Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if(TextUtils.isEmpty(u_nic.getText().toString())){
                    Snackbar.make(rootLayout,"Please enter NIC",Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if(TextUtils.isEmpty(u_dob.getText().toString())){
                    Snackbar.make(rootLayout,"Please enter DOB",Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if(TextUtils.isEmpty(u_email.getText().toString())){
                    Snackbar.make(rootLayout,"Please enter Email",Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                auth.createUserWithEmailAndPassword(u_email.getText().toString(),u_password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        User user = new User();
                        user.setEmail(u_email.getText().toString());
                        user.setPassword(u_password.getText().toString());
                        user.setDob(u_dob.getText().toString());
                        user.setMobile(u_phone.getText().toString());
                        user.setNic(u_nic.getText().toString());
                        final String userID =FirebaseAuth.getInstance().getCurrentUser().getUid() ;
                        users.child(userID).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Snackbar.make(rootLayout,"Registered Successfully",Snackbar.LENGTH_SHORT)
                                        .show();

                                showloanDialog(userID);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(rootLayout,"Fail "+e.getMessage(),Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(rootLayout,"Fail "+e.getMessage(),Snackbar.LENGTH_SHORT)
                                .show();
                    }
                });
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showloanDialog(String userID) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("DEPOSIT");
        dialog.setMessage("Deposit initial amount to continue");

        LayoutInflater inflater = LayoutInflater.from(this);
        View loan_layout = inflater.inflate(R.layout.layout_loan,null);

        dialog.setView(loan_layout);

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

    }


}
