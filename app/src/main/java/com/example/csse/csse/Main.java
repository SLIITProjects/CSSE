package com.example.csse.csse;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rengwuxian.materialedittext.MaterialEditText;
//09867534
import org.json.JSONObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Main extends AppCompatActivity {
    RequestQueue rq;

    Button btnRegister,btnSignIn,btnPass;
    RelativeLayout rootLayout;
    String name,nic,phone,dob,pass;

    //Rest API url
    String url = "http://10.0.2.2:8081/passports/";

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

        rq = Volley.newRequestQueue(this);

        //Initialize ui component
        btnSignIn = (Button)findViewById(R.id.btn_signIn);
        btnRegister = (Button)findViewById(R.id.btn_register);
        btnPass = (Button)findViewById(R.id.btn_pass);
        rootLayout = (RelativeLayout)findViewById(R.id.rootLayout);


        //Go to Sign in menu when click sign in button
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(Main.this,Sign_in.class);
                startActivity(signIn);
            }
        });

        //Go to Sign up menu when click sign up button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(Main.this,Sign_up.class);
                startActivity(signUp);
            }
        });

        //open modal when click 7day pass button
        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterPassport();
            }
        });
    }

    //Function to get json data from rest api
    private void enterPassport() {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("7-Day pass");

            LayoutInflater inflater = LayoutInflater.from(this);
            View register_layout = inflater.inflate(R.layout.layout_passport,null);

            final MaterialEditText passnumber = register_layout.findViewById(R.id.editPassport);

            dialog.setView(register_layout);
            dialog.setPositiveButton("ENTER", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //regular expression to check passport Number
                    String passpattern = "^(?!^0+$)[1-zA-Z0-9]{7,20}$";
                    Pattern pattern = Pattern.compile(passpattern);
                    Matcher matcher = pattern.matcher(passnumber.getText().toString());

                    if(TextUtils.isEmpty(passnumber.getText().toString())){
                        Snackbar.make(rootLayout,"Please enter passport number",Snackbar.LENGTH_SHORT)
                                .show();
                        return;
                    }
                    else if(passnumber.getText().toString().length()!=8){
                        Snackbar.make(rootLayout,"Invalid passport number",Snackbar.LENGTH_SHORT)
                                .show();
                        return;
                    }else{
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url+passnumber.getText().toString(), null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try{
                                    //Get specific details from the user
                                    name = response.getString("fname");
                                    nic = response.getString("nic");
                                    dob = response.getString("dob");
                                    phone = response.getString("phone");
                                    pass = response.getString("passNum");

                                    //send the details through the activity
                                    Intent signUp = new Intent(Main.this,Sign_up.class);
                                    signUp.putExtra("NIC",nic);
                                    signUp.putExtra("Name",name);
                                    signUp.putExtra("Phone",phone);
                                    signUp.putExtra("DOB",dob);
                                    signUp.putExtra("Pass",pass);
                                    startActivity(signUp);

                                }catch(Exception e){
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Snackbar.make(rootLayout,"Passport number not found!",Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                        });
                        rq.add(jsonObjectRequest);


                    }

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



}
