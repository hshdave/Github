package com.a1694158.harshkumar.github;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import com.google.gson.JsonParser;
import com.google.gson.stream.MalformedJsonException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Projectlist extends AppCompatActivity {

    String apilnk = "";
    String schkey = "";
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    ArrayList<Gitget> gitdata;

    ListView lisv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectlist);

         Intent i = getIntent();
         schkey = i.getStringExtra("search");

        //Toast.makeText(getApplicationContext(),"Key : "+schkey,Toast.LENGTH_LONG).show();

        new GetJSON().execute();

    }

    private class GetJSON extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Toast.makeText(getApplicationContext(),"Json Data is downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Httphandler sh = new Httphandler();

            String key =  schkey.toString();

            key  = key.replace(" ","+");

            apilnk = "https://api.github.com/search/repositories?q="+key;


            System.out.println("Link............"+apilnk);

            String jsonstr = sh.makeServiceCall(apilnk);

            System.out.println("JSON String     "+ jsonstr);


            if (jsonstr!=null)
            {
                try
                {
                    JSONObject jsonObject =  new JSONObject(jsonstr);
                    System.out.println("Main JASON OBJECT     "+jsonObject.toString());

//                    Toast.makeText(getApplicationContext(),"Total Result Found : "+jsonObject.getInt("total_count"),Toast.LENGTH_LONG).show();

                    //Main Array
                    JSONArray items = jsonObject.getJSONArray("items");

                 gitdata = new ArrayList<Gitget>();


                    for (int i=0;i<items.length();i++)
                    {
                        JSONObject c = items.getJSONObject(i);
                        gitdata.add(new Gitget(c.getString("full_name"),c.getString("description"),c.getString("url")));
                    }



                }catch (JSONException e)
                {
                    Log.e("JSON Parsing error: ", e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Couldn't get json from server. Check LogCat for possible errors!",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lisv = (ListView) findViewById(R.id.project_list);

            lisv.setAdapter(new Projectlistadapter(gitdata,Projectlist.this));

        }
    }
}
