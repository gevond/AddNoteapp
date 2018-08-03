package com.bignerdranch.android.addnote;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by User on 11/30/2017.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
        if(fragment == null){
            fragment = getFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content,fragment);
            ft.commit();
        }
    }
    protected abstract Fragment getFragment();
}
