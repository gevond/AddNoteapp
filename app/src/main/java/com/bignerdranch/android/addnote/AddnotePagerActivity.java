package com.bignerdranch.android.addnote;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

/**
 * Created by User on 12/2/2017.
 */

public class AddnotePagerActivity extends SingleFragmentActivity{

    private static final String KEY_NOTE_UUID = "note_uuid_addnotepageractivity";

    private UUID id;

    public static Intent newIntent(Context context,UUID id){
        Intent intent = new Intent(context,AddnotePagerActivity.class);
        intent.putExtra(AddnotePagerActivity.KEY_NOTE_UUID,id);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        id = (UUID)getIntent().getSerializableExtra(AddnotePagerActivity.KEY_NOTE_UUID);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment getFragment() {
        return AddnotePagerFragmnet.newInstance(id);
    }

}
