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
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by User on 12/15/2017.
 */

public class DateDialog extends DialogFragment{

    private static final String KEY_FOR_DATE_DIALOG = "key_for_date_dialog_one";
    public static final String EXTRA_DATE = "datedialog.myclass.java";
    private DatePicker mDatePicker;


    public static DateDialog newInstance(Date date) {

        Bundle args = new Bundle();
        args.putSerializable(KEY_FOR_DATE_DIALOG,date);
        DateDialog fragment = new DateDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.date_dialog,null);

        Date date = (Date)getArguments().getSerializable(KEY_FOR_DATE_DIALOG);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDatePicker = (DatePicker)view.findViewById(R.id.date_dialog_date);
        mDatePicker.init(year,month,day,null);

        Date currentDate = Calendar.getInstance().getTime();

        mDatePicker.setMinDate(currentDate.getTime() + (1000 * 60 * 60 * 24));

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(getResources().getString(R.string.date_dialog))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();
                        Date date = new GregorianCalendar(year, month, day).
                                getTime();
                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();
    }


    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}
