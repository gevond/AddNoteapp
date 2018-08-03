package com.bignerdranch.android.addnote;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddnoteActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return AddnoteFragment.newInstance();
    }
}
