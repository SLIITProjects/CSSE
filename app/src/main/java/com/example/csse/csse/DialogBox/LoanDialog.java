package com.example.csse.csse.DialogBox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.Toast;

import com.example.csse.csse.Model.Account;
import com.example.csse.csse.payment.AccountService;

public class LoanDialog extends AppCompatDialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        android.support.v7.app.AlertDialog.Builder builder =new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle("test")
                .setMessage("ddcdc")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();


    }
}
