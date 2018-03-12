package com.example.gopalawasthi.jsonplaceholders;

import android.os.AsyncTask;
import android.util.Log;

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
 * Created by Gopal Awasthi on 11-03-2018.
 */

public class postsAsynchronous extends AsyncTask<String,Void,ArrayList<posts>> {
  public interface postInterface{
      void ondownloadingpost(ArrayList<posts> arrayList);
  }
   postInterface mylistener;

    public postsAsynchronous(postInterface mylistener) {
        this.mylistener = mylistener;

    }

    @Override
    protected ArrayList<posts> doInBackground(String... strings) {
    String stringurl = strings[0];
        try {
            URL url = new URL(stringurl);
            HttpsURLConnection httpsURLConnection =(HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");

            httpsURLConnection.connect();
            InputStream inputStream = httpsURLConnection.getInputStream();
            String result = "";
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()){

                result = result.concat(scanner.next());
            }
            Log.d("mypost",result);

            ArrayList<posts> used = new ArrayList<>();
            try {
                ArrayList<posts> arrayList = parseusersPosts(result);
                used.addAll(arrayList);
                httpsURLConnection.disconnect();
                return used;

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

    private ArrayList<posts> parseusersPosts(String result) throws JSONException {
        ArrayList<posts> mylist = new ArrayList<>();
      JSONArray jsonArray = new JSONArray(result);
     for (int i =0 ; i< jsonArray.length() ; i++) {
         JSONObject jsonObject = jsonArray.getJSONObject(i);
         String userpost = jsonObject.getString("body");
         int user_id = jsonObject.getInt("userId");
         int id = jsonObject.getInt("id");
        posts posts1 = new posts(user_id,id,userpost);
         mylist.add(posts1);

     }
     return mylist;
    }


    @Override
    protected void onPostExecute(ArrayList<posts> arrayList) {
        super.onPostExecute(arrayList);
        mylistener.ondownloadingpost(arrayList);
    }
}
