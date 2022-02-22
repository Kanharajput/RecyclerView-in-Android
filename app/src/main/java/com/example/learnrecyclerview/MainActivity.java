package com.example.learnrecyclerview;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnrecyclerview.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements WordListAdapter.OnItemClicked {
    private RecyclerView mRecyclerView;
    private WordListAdapter mWordListAdapter;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private final LinkedList<String> mWordList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override  // adding new word to the lsit
            public void onClick(View view) {
                int wordListSize = mWordList.size() + 1;
                mWordList.add("+"+"word"+wordListSize);    // adding a new string element
                mRecyclerView.getAdapter().notifyItemInserted(wordListSize);  // notify dataset changed
                mRecyclerView.smoothScrollToPosition(wordListSize);   // scroll screen where we added new data
            }
        });

        // always do this operations after inflating the main layout
        createDataSet();   // Let's create a data set by calling this method
        mRecyclerView  = findViewById(R.id.recycler_view);
        // lets create an adapter
        mWordListAdapter = new WordListAdapter(this,mWordList,this);
        mRecyclerView.setAdapter(mWordListAdapter);     // connect the adapter to the view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));    // pass a layout manager
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //reseting the data back to initial state
        if (id == R.id.action_reset) {
            mWordList.clear();
            createDataSet();   // Let's create a data set by calling this method
            mWordListAdapter = new WordListAdapter(this,mWordList,this);
            mRecyclerView.setAdapter(mWordListAdapter);     // connect the adapter to the view
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));    // pass a layout manager
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // creating a data set
    private void createDataSet() {
        for(int i=1; i<20; i++) {
            mWordList.add("Word " + i);
        }
    }

    @Override  // do what you wanna do when clicked to an item
    public void onItemClick(int position) {
        // use this position to get the affected item in the wordlist
        String element = mWordList.get(position);
        // change the data in the wordList
        mWordList.set(position,"cicked!"+element);
        // notify the adapter that the data has been chagned
        // so recyclerVeiw can update it
        mWordListAdapter.notifyDataSetChanged();
    }
}