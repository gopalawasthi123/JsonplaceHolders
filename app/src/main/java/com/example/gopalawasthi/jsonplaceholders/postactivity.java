package com.example.gopalawasthi.jsonplaceholders;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;


public class postactivity extends AppCompatActivity {

    ArrayList<String> postsArrayList;
    ArrayAdapter<String>postsArrayAdapter;
    ListView listView;
    ProgressBar progressBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postactivity);
       listView = findViewById(R.id.postlist);
       postsArrayList = new ArrayList<>();
       postsArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,postsArrayList);
        progressBar = findViewById(R.id.progress);
        listView.setAdapter(postsArrayAdapter);

        listView.setVisibility(View.GONE);
     progressBar.setVisibility(View.VISIBLE);

     Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        int a=   intent.getIntExtra("id",-1);

        String stringurl = "https://jsonplaceholder.typicode.com/posts?userId="+a;
        postsAsynchronous asynchronous = new postsAsynchronous(new postsAsynchronous.postInterface() {
            @Override
            public void ondownloadingpost(ArrayList<posts> arrayList) {
                if(arrayList!=null){
                  for(int i =0 ;i <arrayList.size() ; i++){
                      posts myposts = arrayList.get(i);
                      postsArrayList.add(myposts.getPost());
                      Log.d("myposts",arrayList.toString());

                  }

                    postsArrayAdapter.notifyDataSetChanged();

                } else {
                    Snackbar.make(listView, "tryagain", Snackbar.LENGTH_SHORT).show();
                }
                listView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

            }
        });
        asynchronous.execute(stringurl);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
