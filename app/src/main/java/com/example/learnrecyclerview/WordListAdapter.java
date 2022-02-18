package com.example.learnrecyclerview;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private LinkedList<String> mwordList;
    private LayoutInflater mInflater;

    // get the data from mainActivity and the layout inflator
    public WordListAdapter(Context context, LinkedList<String> wordList) {
        mInflater = LayoutInflater.from(context);
        mwordList = wordList;
    }

    @NonNull
    @Override   // this method is called to create a view
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.wordlist,parent,false);
        return new WordViewHolder(itemView,this);
    }

    @Override   // used to bind data with the view referenced in the WordViewHolder constructor
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        String data = mwordList.get(position);
        holder.wordView.setText(data);
    }

    @Override   // necessary to return the size so may be recycleView iterate that much times to show all view with data
    public int getItemCount() {
        return mwordList.size();
    }

    // referencing the view
    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView wordView;
        WordListAdapter mWordListAdapter;
        public WordViewHolder(@NonNull View itemView,WordListAdapter mwordListAdapter) {
            super(itemView);
            wordView = itemView.findViewById(R.id.word);
            this.mWordListAdapter = mwordListAdapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // get the position of the item that was clicked
            int mPosition = getLayoutPosition();
            // use this position to get the affected item in the wordlist
            String element = mwordList.get(mPosition);
            // change the data in the wordList
            mwordList.set(mPosition,"cicked!"+element);
            // notify the adapter that the data has been chagned
            // so recyclerVeiw can update it
            mWordListAdapter.notifyDataSetChanged();
        }
    }
}