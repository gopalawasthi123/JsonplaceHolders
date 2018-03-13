package com.example.gopalawasthi.jsonplaceholders;

import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Gopal Awasthi on 13-03-2018.
 */

public class CommentsAsynchronous extends AsyncTask<String,Void,ArrayList<Comments>> {

    public interface  mycommntslistener{
        void onpostcomment(ArrayList<Comments> arrayList);
    }

    mycommntslistener mycommntslistener;

    public CommentsAsynchronous(CommentsAsynchronous.mycommntslistener mycommntslistener) {
        this.mycommntslistener = mycommntslistener;
    }

    @Override
    protected ArrayList<Comments> doInBackground(String... strings) {
        String urlstring = strings[0];
        try {
            URL url = new URL(urlstring);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.connect();

            InputStream inputStream = httpsURLConnection.getInputStream();
            String result = "";
            Scanner scanner = new Scanner(inputStream);
            while(scanner.hasNext()){
                result = result.concat(scanner.next());
            }

            try {
                ArrayList<Comments> arrayList =fetchcommentsfrominternet(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Comments> fetchcommentsfrominternet(String result) throws JSONException {
        ArrayList<Comments> postscomments =  new ArrayList<>();
        JSONArray jsonArray = new JSONArray(result);
        for(int i= 0 ; i< jsonArray.length() ;i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String comment = jsonObject.getString("name");
            int posts_id = jsonObject.getInt("postId");
            int id = jsonObject.getInt("id");
            Comments comments = new Comments(comment,posts_id,id);
            postscomments.add(comments);

        }
        return postscomments;
    }

    @Override
    protected void onPostExecute(ArrayList<Comments> comments)  {
        super.onPostExecute(comments);
       mycommntslistener.onpostcomment(comments);
    }
}
