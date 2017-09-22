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
    String repolink = "";

    ArrayList<Gitget> gitdata;

    ListView lisv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectlist);

         Intent i = getIntent();
         schkey = i.getStringExtra("search");
         repolink = i.getStringExtra("repolink");


        String key = "";
        if(schkey != "" && schkey !=  null)
        {
            key  =  schkey;
            key  = key.replace(" ","+");
            apilnk = "https://api.github.com/search/repositories?q="+key;
        }



        System.out.println("From user repository !    "+repolink);

        String[] links = {apilnk,repolink};


        new GetJSON().execute(links);

        //Toast.makeText(getApplicationContext(),"Key : "+schkey,Toast.LENGTH_LONG).show();

    }

    private class GetJSON extends AsyncTask<String, Void, Void>
    {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            gitdata = new ArrayList<>();
            Toast.makeText(getApplicationContext(),"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(String... strings) {


            if(strings[1] != null || strings[1] != "")
            {

                //gitdata = new ArrayList<>();
                String lk = strings[1];

                Httphandler sh = new Httphandler();

                System.out.println("User Repos............"+lk);

                String jsonstr = sh.makeServiceCall(lk);

                System.out.println("JSON String     "+ jsonstr);

                if (jsonstr!=null)
                {
                    try
                    {
                        JSONArray repoarray = new JSONArray(jsonstr);

                        System.out.println("Main JSON REPO Array     "+repoarray.toString());

                        for (int i=0;i<repoarray.length();i++)
                        {
                            JSONObject singleobj = repoarray.getJSONObject(i);

                            System.out.println("Single Repos in  "+singleobj.getString("full_name"));

                            gitdata.add(new Gitget(singleobj.getString("full_name"),singleobj.getString("description"),singleobj.getString("url")));
                        }



                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

            }

                String lk = strings[0];

                Httphandler sh = new Httphandler();

                System.out.println("Link............"+lk);

                String jsonstr = sh.makeServiceCall(lk);

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
