package com.example.csse.csse.DialogBox;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

public class PaymentSuccess extends AppCompatDialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        android.support.v7.app.AlertDialog.Builder builder =new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Payemant Deatails")
                .setMessage("Your Payment have been Success")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        return builder.create();


    }
}


