package com.a1694158.harshkumar.github;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Repo_owner extends AppCompatActivity {


    String nm = "Name : ";
    String ty = "Type : ";
    String jsnurl = "";
    TextView  ownnm,type;
    ImageView img_own;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_owner);

        ownnm = (TextView) findViewById(R.id.txt_ownm);
        type = (TextView) findViewById(R.id.txt_owtype);
        img_own = (ImageView) findViewById(R.id.img_own);

        Intent i = getIntent();
        jsnurl = i.getStringExtra("jsonstr");

        new fetchjson().execute(jsnurl);

    }

    public class fetchjson extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            System.out.println("Repo owner json.....  "+s);

            if (s!=null&&!s.isEmpty())
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(s);

                    String ownnmls = jsonObject.getString("name");

                    System.out.println("own name     "+ownnmls);

                    if(ownnmls.equals("null"))
                    {
                        nm+= jsonObject.getString("login");
                    }
                    else {
                        nm += jsonObject.getString("name");
                    }
                    ownnm.setText(nm);

                    ty += jsonObject.getString("type");
                    type.setText(ty);

                    String avurl = jsonObject.getString("avatar_url");

                    System.out.println("Avatar url   "+avurl);

                    Picasso.with(Repo_owner.this).load(avurl).noFade().into(img_own);



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

        }

        @Override
        protected String doInBackground(String... strings) {


            String jsonurl = strings[0];

            Httphandler sh = new Httphandler();

            System.out.println("From Owner Details............."+jsonurl);
            String jsonstr = sh.makeServiceCall(jsonurl);
            return jsonstr;
        }
    }
}
