package com.example.gopalawasthi.jsonplaceholders;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> commentsarraylist;
    ArrayAdapter<String> commentsArrayAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        listView = findViewById(R.id.commentslist);
        progressBar = findViewById(R.id.progressbar);
        commentsarraylist = new ArrayList<>();
        commentsArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,commentsarraylist);
        listView.setAdapter(commentsArrayAdapter);
        Intent intent = getIntent();
        final int a =  intent.getIntExtra("posts_id",-1);
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        String urlstring = "https://jsonplaceholder.typicode.com/comments?postId="+ a;
            CommentsAsynchronous commentsAsynchronous = new CommentsAsynchronous(new CommentsAsynchronous.mycommntslistener() {
                @Override
                public void onpostcomment(ArrayList<Comments> arrayList) {
                    if(arrayList!=null){
                        for(int i=0 ;i < arrayList.size() ;i++){
                        Comments comments = arrayList.get(i);
                        String b = comments.getComment_name();
                        commentsarraylist.add(b);
                    }
                    commentsArrayAdapter.notifyDataSetChanged();
                } else{
                        Snackbar.make(listView,"tryagain!",Snackbar.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }
            });
            commentsAsynchronous.execute(urlstring);
    }
}
