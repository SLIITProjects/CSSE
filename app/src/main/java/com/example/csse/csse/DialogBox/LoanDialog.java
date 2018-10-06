package com.example.csse.csse.DialogBox;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

public class LoanDialog extends AppCompatDialogFragment{

    private Loandlisttner lp;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        android.support.v7.app.AlertDialog.Builder builder =new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle("test")
                .setMessage("ddcdc")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        lp.update();
                    }
                });
        return builder.create();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            lp=(Loandlisttner)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    public interface Loandlisttner{
         void update();
  }
}
