package com.bignerdranch.android.addnote;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by User on 11/30/2017.
 */

public class AddnoteFragment extends Fragment{

    private static final String TAG = "AddnoteFragment";
    private static final String DIALOG_DELETE = "DialogDelete";

    private RecyclerView mRecyclerView;
    private List<Note> mNotes;
    private AddnoteAdapter mAdapter;


    public static AddnoteFragment newInstance() {

        Bundle args = new Bundle();

        AddnoteFragment fragment = new AddnoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        updateItems();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateItems();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addnote,container,false);


        mRecyclerView = (RecyclerView)view.findViewById(R.id.fragment_addnote_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        setAdapter();

        return view;
    }

    private void setAdapter(){
        mAdapter = new AddnoteAdapter(mNotes);
        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper.Callback callback =
                new ItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void updateUI(){
        if(mAdapter == null){
            mAdapter = new AddnoteAdapter(mNotes);
            mRecyclerView.setAdapter(mAdapter);
            Log.d(TAG,"if");
        }
        else{
            mAdapter.setNotes(mNotes);
            mAdapter.notifyDataSetChanged();
            Log.d(TAG,"else");
        }
    }

    private void updateItems(){
        new AddnoteTask().execute();
    }

    //--------------------menu begin----------------------------------------------------------------

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_addnote,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_add_note){
            Note note = new Note();
            AddnoteLab.get(getActivity()).addNote(note);
            Intent intent = AddnotePagerActivity.newIntent(getActivity(),note.getId());
            startActivity(intent);
            //updateItems();
            return true;
        }
        else {
            return false;
        }
    }

    //--------------------menu end------------------------------------------------------------------


    //----------------------------this is the place where i read adapter----------------------------

    private class AddnoteAdapter extends RecyclerView.Adapter<AddnoteHolder> implements ItemTouchHelperCallback.ItemTouchHelperAdapter{

        private List<Note> mNotes;

        public AddnoteAdapter(List<Note> notes){
            mNotes = notes;
        }

        @Override
        public AddnoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.item_view,parent,false);
            return new AddnoteHolder(view);
        }

        @Override
        public void onBindViewHolder(AddnoteHolder holder, int position) {
            holder.bindItems(mNotes.get(position));
        }

        @Override
        public int getItemCount() {
            return mNotes.size();
        }

        @Override
        public void onItemDismiss(int position) {
            /*
            AddnoteLab.get(getActivity()).removeNote(mNotes.get(position).getId());
            mNotes.remove(position);
            notifyItemRemoved(position);
            */

            final int a = position;
            FragmentManager manager = getFragmentManager();
            DeleteNoteDialog dialog = new DeleteNoteDialog();

            dialog.setCallbackAdapter(new DeleteNoteDialog.CallbackAdapter() {
                @Override
                public void state(boolean b) {
                    if(b){
                        AddnoteLab.get(getActivity()).removeNote(mNotes.get(a).getId());
                        mNotes.remove(a);
                        notifyItemRemoved(a);
                        return;
                    }
                    notifyDataSetChanged();
                }
            });
            dialog.show(manager,DIALOG_DELETE);

        }

        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mNotes, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mNotes, i, i - 1);
                }
            }
            new AddnoteUpdateTask().execute(mNotes);
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        public void setNotes(List<Note> mNotes) {
            this.mNotes = mNotes;
            notifyDataSetChanged();
        }
    }

    private class AddnoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Note mNote;

        private TextView mTextViewTitle;
        private TextView mTextViewNote;
        private CardView mCardView;


        public AddnoteHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTextViewTitle = (TextView)itemView.findViewById(R.id.item_view_title);
            mTextViewNote = (TextView)itemView.findViewById(R.id.item_view_note);
            mCardView = (CardView)itemView.findViewById(R.id.item_view_card_view);
        }

        public void bindItems(Note note){
            mNote = note;
            mTextViewTitle.setText(note.getTitle());
            mTextViewNote.setText(note.getNote());
            mCardView.setCardBackgroundColor(Color.parseColor(mNote.getColor()));
        }

        @Override
        public void onClick(View view) {
            Intent intent = AddnotePagerActivity.newIntent(getActivity(),mNote.getId());
            startActivity(intent);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private class AddnoteTask extends AsyncTask<Void,Void,List<Note>>{

        @Override
        protected List<Note> doInBackground(Void... voids) {
            return AddnoteLab.get(getActivity()).getNotes();
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            mNotes = notes;
            updateUI();
        }

    }

    private class AddnoteUpdateTask extends AsyncTask<List<Note>,Void,Void>{


        @Override
        protected Void doInBackground(List<Note>[] lists) {
            List<Note> mList = lists[0];
            AddnoteLab.get(getActivity()).updateAllNotes(mList);
            return null;
        }
    }

    //----------------------------this is the end place where i read adapter------------------------

}
