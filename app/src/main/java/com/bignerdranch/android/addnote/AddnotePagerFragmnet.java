package com.bignerdranch.android.addnote;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by User on 12/2/2017.
 */

public class AddnotePagerFragmnet extends Fragment{

    private static final String ARG_NOTE_ID = "note_id_addnotepagerfragment";
    private static final String DIALOG_DELETE = "DialogDelete";
    private static final String DATE_DIALOG = "date";
    private static final String TIME_DIALOG = "time";

    private static final int REQUEST_DATE = 1;
    private static final int REQUEST_TIME = 2;

    private EditText mEditTextTitle;
    private EditText mEditTextNote;
    private Button mButtonAlarmDate;
    private Button mButtonAlarmTime;
    private Switch mSwitchAlarm;
    private CardView mCardView1;
    private CardView mCardView2;
    private CardView mCardView3;
    private TextView mTextViewDate;
    private Note mNote;

    private RadioButton mRad1;
    private RadioButton mRad2;
    private RadioButton mRad3;
    private RadioButton mRad4;
    private RadioButton mRad5;
    private RadioButton mRad6;

    private RadioGroup mRadioGroup;

    public static AddnotePagerFragmnet newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTE_ID,id);
        AddnotePagerFragmnet fragment = new AddnotePagerFragmnet();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID id = (UUID)getArguments().getSerializable(ARG_NOTE_ID);
        mNote = AddnoteLab.get(getActivity()).getNoteById(id);
    }

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addnote_pager,container,false);

        mEditTextTitle = (EditText)view.findViewById(R.id.note_title);
        mEditTextNote = (EditText)view.findViewById(R.id.note_text);
        mSwitchAlarm = (Switch)view.findViewById(R.id.fragment_addnote_pager_date_switch);
        mButtonAlarmDate = (Button)view.findViewById(R.id.fragment_addnote_pager_date_button);
        mButtonAlarmTime = (Button)view.findViewById(R.id.fragment_addnote_pager_time_button);
        mCardView1 = (CardView)view.findViewById(R.id.card_1);
        mCardView2 = (CardView)view.findViewById(R.id.card_2);
        mCardView3 = (CardView)view.findViewById(R.id.card_3);
        mTextViewDate = (TextView)view.findViewById(R.id.create_date);

        mRad1 = (RadioButton)view.findViewById(R.id.pager_radio_1);
        mRad2 = (RadioButton)view.findViewById(R.id.pager_radio_2);
        mRad3 = (RadioButton)view.findViewById(R.id.pager_radio_3);
        mRad4 = (RadioButton)view.findViewById(R.id.pager_radio_4);
        mRad5 = (RadioButton)view.findViewById(R.id.pager_radio_5);
        mRad6 = (RadioButton)view.findViewById(R.id.pager_radio_6);

        mRadioGroup = (RadioGroup)view.findViewById(R.id.radio_group);

        if(mNote.getColor().equals("#99173C")){
            mRad1.setChecked(true);
        }
        else if(mNote.getColor().equals("#07A0C3")){
            mRad2.setChecked(true);
        }
        else if(mNote.getColor().equals("#F0C808")){
            mRad3.setChecked(true);
        }
        else if(mNote.getColor().equals("#086788")){
            mRad4.setChecked(true);
        }
        else if(mNote.getColor().equals("#5EFC8D")){
            mRad5.setChecked(true);
        }
        else if(mNote.getColor().equals("#8377D1")){
            mRad6.setChecked(true);
        }

        mEditTextTitle.setText(mNote.getTitle());
        mEditTextNote.setText(mNote.getNote());

        setButtonsEneble(mNote.isAlarm());

        updateButtons();
        Calendar cal = Calendar.getInstance();
        cal.setTime(mNote.getDate());
        String currentDate24Hrs = (String) DateFormat.format(
                "MM/dd/yyyy kk:mm", cal.getTime());
        mSwitchAlarm.setChecked(mNote.isAlarm());
        mTextViewDate.setText(getResources().getString(R.string.note_creating_date)+" "+currentDate24Hrs);


        updateBackground();


        //------------------listeners begin---------------------------------------------------------

        mEditTextTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNote.setTitle(charSequence.toString());
                updateNote(mNote);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEditTextNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNote.setNote(charSequence.toString());
                updateNote(mNote);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mButtonAlarmDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DateDialog dialog = DateDialog.newInstance(mNote.getNotDateNotification());
                dialog.setTargetFragment(AddnotePagerFragmnet.this,REQUEST_DATE);
                dialog.show(fm,DATE_DIALOG);
            }
        });

        mButtonAlarmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                TimeDialog dialog = TimeDialog.newInstance(mNote.getNotDateNotification());
                dialog.setTargetFragment(AddnotePagerFragmnet.this,REQUEST_TIME);
                dialog.show(fm,TIME_DIALOG);
            }
        });

        mSwitchAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mNote.setAlarm(b);
                setButtonsEneble(b);
                updateNote(mNote);
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.pager_radio_1){
                    mNote.setColor("#99173C");
                    updateBackground();
                    updateNote(mNote);
                }
                else if(i == R.id.pager_radio_2){
                    mNote.setColor("#07A0C3");
                    updateBackground();
                    updateNote(mNote);
                }
                else if(i == R.id.pager_radio_3){
                    mNote.setColor("#F0C808");
                    updateBackground();
                    updateNote(mNote);
                }
                else if(i == R.id.pager_radio_4){
                    mNote.setColor("#086788");
                    updateBackground();
                    updateNote(mNote);
                }
                else if(i == R.id.pager_radio_5){
                    mNote.setColor("#5EFC8D");
                    updateBackground();
                    updateNote(mNote);
                }
                else if(i == R.id.pager_radio_6){
                    mNote.setColor("#8377D1");
                    updateBackground();
                    updateNote(mNote);
                }
            }
        });

        //------------------listeners end-----------------------------------------------------------

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_addnote_pager_fragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_delete_note){
            /*
            AddnoteLab.get(getActivity()).removeNote(mNote.getId());
            getActivity().finish();
            */
            FragmentManager fm = getFragmentManager();
            DeleteNoteDialog dialog = new DeleteNoteDialog();
            dialog.setCallbackAdapter(new DeleteNoteDialog.CallbackAdapter() {
                @Override
                public void state(boolean b) {
                    if(b){
                        AddnoteLab.get(getActivity()).removeNote(mNote.getId());
                        getActivity().finish();
                    }
                }
            });
            dialog.show(fm,DIALOG_DELETE);
            return true;
        }else{
            return false;
        }
    }

    private void updateNote(Note note){
        AddnoteLab.get(getActivity()).updateNote(note);
    }
    private void updateBackground(){
        int color = Color.parseColor(mNote.getColor());
        mCardView1.setCardBackgroundColor(color);
        mCardView2.setCardBackgroundColor(color);
        mCardView3.setCardBackgroundColor(color);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_DATE){
            Date date = (Date) data
                    .getSerializableExtra(DateDialog.EXTRA_DATE);
            Calendar cal =Calendar.getInstance();
            cal.setTime(date);
            mNote.setNotDateNotification(cal.getTime());
        }
        else if(requestCode == REQUEST_TIME){
            Date date = (Date)data
                    .getSerializableExtra(TimeDialog.EXTRA_TIME);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            mNote.getNotDateNotification().setHours(cal.get(Calendar.HOUR_OF_DAY));
            mNote.getNotDateNotification().setMinutes(cal.get(Calendar.MINUTE));
        }
        AddnoteLab.get(getActivity()).updateNote(mNote);
        updateButtons();
    }
    private void updateButtons(){
        String buttonDate = (String) DateFormat.format("yyyy-MM-dd ",mNote.getNotDateNotification());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mNote.getNotDateNotification());
        String currentDate24Hrs = (String) DateFormat.format(
                "kk:mm", calendar.getTime());
        mButtonAlarmDate.setText(buttonDate);
        mButtonAlarmTime.setText(currentDate24Hrs);
    }
    private void setButtonsEneble(boolean b){
        mButtonAlarmDate.setEnabled(b);
        mButtonAlarmTime.setEnabled(b);
    }
}
