package com.a1694158.harshkumar.github;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Repo_details extends AppCompatActivity {

    TextView txtname, txtfname, txtowner, txtdesc, txtlang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_details);

        txtname = (TextView) findViewById(R.id.txt_nm);
        txtfname = (TextView) findViewById(R.id.txt_fnm);
        txtowner = (TextView) findViewById(R.id.txt_own);
        txtdesc = (TextView) findViewById(R.id.txt_desc);
        txtlang = (TextView) findViewById(R.id.txt_lang);

        Intent i = getIntent();

        String repourl = i.getStringExtra("repourl");

        new Fetchjson().execute(repourl);
    }


    public class Fetchjson extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {


            String jsonurl = strings[0];

            Httphandler sh = new Httphandler();


            System.out.println("From Repo Details............." + jsonurl);
            String jsonstr = sh.makeServiceCall(jsonurl);

            return jsonstr;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s != null && !s.isEmpty()) {
                try {
                    final JSONObject jsonObject = new JSONObject(s);
                    txtname.setText(jsonObject.getString("name"));
                    txtfname.setText(jsonObject.getString("full_name"));
                    txtdesc.setText(jsonObject.getString("description"));
                    txtlang.setText(jsonObject.getString("language"));

                    final JSONObject ownerobj = jsonObject.getJSONObject("owner");

                    txtowner.setText(ownerobj.getString("login"));

                    String ow = txtowner.getText().toString();

                    if (!ow.isEmpty() && ow != "" && ow != null) {
                        txtowner.setClickable(true);
                        txtowner.setTextColor(getColor(R.color.colorPrimary));

                        txtowner.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                try {

                                    Intent i = new Intent(Repo_details.this, Repo_owner.class);
                                    String ownurl = ownerobj.getString("url").toString();

                                    i.putExtra("jsonstr", ownurl);
                                    startActivity(i);

                                } catch (JSONException e) {
                                    Log.e("JSON Parsing error: ", e.getMessage());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }


                            }
                        });
                    }


                } catch (JSONException e) {
                    Log.e("JSON Parsing error: ", e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }
    }


}
