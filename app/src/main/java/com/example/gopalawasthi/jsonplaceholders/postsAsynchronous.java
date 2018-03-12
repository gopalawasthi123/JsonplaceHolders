package com.example.gopalawasthi.jsonplaceholders;

import android.os.AsyncTask;
import android.util.Log;

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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
