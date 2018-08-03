package com.bignerdranch.android.addnote;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by User on 12/15/2017.
 */

public class TimeDialog extends DialogFragment {

    private static final String KEY_FOR_TIME_DIALOG = "key_for_time_dialog_one";
    public static final String EXTRA_TIME = "timedialog.myclass.java";
    private TimePicker mTimePicker;

    protected String getTitle() {
        return getResources().getString(R.string.time_dialog);
    }

    public static TimeDialog newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_FOR_TIME_DIALOG,date);
        TimeDialog fragment = new TimeDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.time_dialog,null);

        Date date = (Date)getArguments().getSerializable(KEY_FOR_TIME_DIALOG);

        mTimePicker = (TimePicker)view.findViewById(R.id.time_dialog_time);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);


        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);


        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(min);
        mTimePicker.setIs24HourView(true);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(getTitle())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int hour = mTimePicker.getCurrentHour();
                        int min = mTimePicker.getCurrentMinute();
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR_OF_DAY,hour);
                        cal.set(Calendar.MINUTE,min);
                        Date date = cal.getTime();
                        sendResult(Activity.RESULT_OK,date);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, date);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
