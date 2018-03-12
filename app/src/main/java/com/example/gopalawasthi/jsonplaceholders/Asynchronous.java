package com.example.gopalawasthi.jsonplaceholders;

import android.net.sip.SipAudioCall;
import android.net.sip.SipSession;
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

public class Asynchronous extends AsyncTask<String, Void, ArrayList<Users>> {

     public  interface  myinterface{
         void ondownloadcomplete(ArrayList<Users> users);
     }
 private  myinterface listener;
    public Asynchronous(myinterface listener) {
        this.listener = listener;

    }

    @Override
    protected ArrayList<Users> doInBackground(String... strings) {
        String stringurl = strings[0];

        try {
            URL url = new URL(stringurl);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");

            httpsURLConnection.connect();
            InputStream inputStream = httpsURLConnection.getInputStream();
            String result= "";
            Scanner scanner = new Scanner(inputStream);
            while(scanner.hasNext()){

                result = result.concat(scanner.next());
            }
            Log.d("fetch",result);

            try {
                ArrayList<Users > arrayList = parseUsers(result);
                httpsURLConnection.disconnect();
                return  arrayList;

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

    private ArrayList<Users> parseUsers(String result) throws JSONException {
        ArrayList <Users> arrayList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(result);
        for(int i =0 ; i< jsonArray.length() ; i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String name = jsonObject.getString("name");
            int id = jsonObject.getInt("id");
            Users users = new Users(id,name);
            arrayList.add(users);

        }
        return arrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<Users> users) {
        super.onPostExecute(users);
        listener.ondownloadcomplete(users);
    }
}
