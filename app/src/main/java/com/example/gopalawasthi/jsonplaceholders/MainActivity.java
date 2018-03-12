package com.example.gopalawasthi.jsonplaceholders;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
 ListView listView;
 ArrayList <String> myusers;
 ArrayAdapter<String> arrayAdapter;
 ProgressBar progressBar;

 @Override
    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                progressBar = findViewById(R.id.progress);
                listView = findViewById(R.id.listview);
                myusers = new ArrayList<>();
                arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myusers);
                listView.setAdapter(arrayAdapter);

                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      fetchdatafromnetwork();
                    }
                });
                listView.setOnItemClickListener(this);
    }

    private void fetchdatafromnetwork() {
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    String url = "https://jsonplaceholder.typicode.com/users";
    Asynchronous asynchronous = new Asynchronous(new Asynchronous.myinterface() {
        @Override
        public void ondownloadcomplete(ArrayList<Users> users) {
            if(users!=null){
               for(int i =0 ; i< users.size() ; i++){
                   Users users1 = users.get(i);
                   myusers.add(users1.getUsername());

               }
               arrayAdapter.notifyDataSetChanged();
            }else{
                Snackbar.make(listView,"tryagain",Snackbar.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
    });
    asynchronous.execute(url);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,postactivity.class);
                int a =(int)  id;
            int ids  =(int) arrayAdapter.getItemId(position);
        intent.putExtra("id",ids);
        startActivity(intent);

    }
}