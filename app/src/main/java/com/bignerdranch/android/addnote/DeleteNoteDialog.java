package com.bignerdranch.android.addnote;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Window;

import java.util.UUID;

/**
 * Created by User on 12/11/2017.
 */

public class DeleteNoteDialog extends android.support.v4.app.DialogFragment{

    public interface CallbackAdapter{
        public void state(boolean b);
    }
    private CallbackAdapter mCallbackAdapter;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == DialogInterface.BUTTON_POSITIVE){
                    mCallbackAdapter.state(true);
                    return;
                }
                mCallbackAdapter.state(false);
            }
        };
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.delete)
                .setPositiveButton(android.R.string.ok,listener)
                .setNegativeButton(android.R.string.no,listener)
                .create();
    }

    public void setCallbackAdapter(CallbackAdapter mCallbackAdapter) {
        this.mCallbackAdapter = mCallbackAdapter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCallbackAdapter.state(false);
    }
}
