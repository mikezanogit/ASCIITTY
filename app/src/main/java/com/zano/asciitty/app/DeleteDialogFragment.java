package com.zano.asciitty.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by mamanzan on 6/16/2014.
 */
public class DeleteDialogFragment extends DialogFragment {

    private String mMessage;
    private IDeleteDialogActions mActions;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActions = (IDeleteDialogActions)activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(R.string.deleteTitle);
        builder.setMessage(R.string.deleteMessage);
        builder.setPositiveButton(R.string.dialogDelete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mActions.positiveDeleteClick();
            }
        });

        builder.setNegativeButton(R.string.dialogCancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mActions.negativeDeleteClick();
            }
        });


        return builder.create();
    }

    public Dialog create(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.deleteTitle);
        builder.setMessage(R.string.deleteMessage);
        builder.setPositiveButton(R.string.dialogDelete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mActions.positiveDeleteClick();
            }
        });

        builder.setNegativeButton(R.string.dialogCancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mActions.negativeDeleteClick();
            }
        });


        return builder.create();
    }
}
