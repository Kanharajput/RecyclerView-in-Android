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
    private OnItemClicked onItemClicked;

    // get the data from mainActivity and the layout inflator
    public WordListAdapter(Context context, LinkedList<String> wordList, OnItemClicked onItemClicked) {
        mInflater = LayoutInflater.from(context);
        mwordList = wordList;      // not created a new linkedlist mean both wordList and mwordList point to same object
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override   // this method is called to create a view
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.wordlist,parent,false);
        return new WordViewHolder(itemView,onItemClicked);
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
        OnItemClicked onItemClicked;

        public WordViewHolder(@NonNull View itemView, OnItemClicked onItemClicked) {
            super(itemView);
            wordView = itemView.findViewById(R.id.word); // reference so we can bind data with view in onBindViewHolder method
            this.onItemClicked = onItemClicked;    //  pointing to the same object
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClicked.onItemClick(getAdapterPosition());
        }
    }

    // interface to detect clicks on items
    public interface OnItemClicked {
        public void onItemClick(int position);  // one can also define this method here just make the interface default
    }
}