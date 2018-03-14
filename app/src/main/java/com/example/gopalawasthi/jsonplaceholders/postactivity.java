package com.example.gopalawasthi.jsonplaceholders;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;


public class postactivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<String> postsArrayList;
    ArrayAdapter<String>postsArrayAdapter;
    ItemOpenHelper itemOpenHelper ;
    ListView listView;
    ProgressBar progressBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postactivity);
       listView = findViewById(R.id.postlist);
       itemOpenHelper = ItemOpenHelper.getInstance(this);
       postsArrayList = new ArrayList<>();
      // postsArrayList = fetchdatafromdatabase();
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
                     SQLiteDatabase database = itemOpenHelper.getWritableDatabase();
                      ContentValues contentValues = new ContentValues();
                      contentValues.put(Contracts.Posts.POST,myposts.getPost());
                      contentValues.put(Contracts.Posts.POST_ID,myposts.getId());
                      contentValues.put(Contracts.Posts.USER_ID,myposts.getUser_id());
                     long id = database.insert(Contracts.Posts.TABLE_NAME,null,contentValues);
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
        listView.setOnItemClickListener(this);
        asynchronous.execute(stringurl);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //
            }
        });


    }



    private ArrayList<String> fetchdatafromdatabase() {
        SQLiteDatabase sqLiteDatabase = itemOpenHelper.getReadableDatabase();
        ArrayList<String > postlist = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(Contracts.Posts.TABLE_NAME,null,null,null,null,null,null,null);
        while(cursor.moveToNext()){
            String posts = cursor.getString(cursor.getColumnIndex(Contracts.Posts.POST));
            postlist.add(posts);

        }
        return postlist ;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        int pos = (int) postsArrayAdapter.getItemId(position) + 1;
        Intent intent = new Intent(this,CommentsActivity.class);
        intent.putExtra("posts_id",pos);
        startActivity(intent);

    }
}
