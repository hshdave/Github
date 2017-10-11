package com.a1694158.harshkumar.github;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Repo_owner extends AppCompatActivity {


    String nm = "Name : ";
    String ty = "Type : ";
    String jsnurl = "";
    TextView ownnm, type, pubrepos, disflw;
    ImageView img_own;

    String jsn = "";

    ListView lstflw;
    ArrayList<Users> flwlst;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_owner);

        ownnm = (TextView) findViewById(R.id.txt_ownm);
        type = (TextView) findViewById(R.id.txt_owtype);
        img_own = (ImageView) findViewById(R.id.img_own);
        lstflw = (ListView) findViewById(R.id.lst_follow);
        pubrepos = (TextView) findViewById(R.id.txt_pubrepo);
        disflw = (TextView) findViewById(R.id.dis_flw);

        Intent i = getIntent();
        jsnurl = i.getStringExtra("jsonstr");

        System.out.println("JSON ME!......"+jsnurl);

        getJson(jsnurl);
    }
    public void getJson(String jsnul)
   {
       try
       {
           String s = new AsyncData().execute(jsnul).get();

           if (s!=null&&!s.isEmpty()) {
               try {
                   JSONObject jsonObject = new JSONObject(s);

                   String ownnmls = jsonObject.getString("name");

                   System.out.println("own name     " + ownnmls);

                   if (ownnmls.equals("null")) {
                       nm += jsonObject.getString("login");
                   } else {
                       nm += jsonObject.getString("name");
                   }
                   ownnm.setText(nm);

                   ty += jsonObject.getString("type");
                   type.setText(ty);

                   String avurl = jsonObject.getString("avatar_url");

                   System.out.println("Avatar url   " + avurl);

                   Picasso.with(Repo_owner.this).load(avurl).noFade().into(img_own);


                   System.out.println("Followers url.... " + jsonObject.getString("followers_url"));

                    jsn = jsonObject.getString("followers_url");

                   flwData();

                   pubrepos.setTextColor(getColor(R.color.colorPrimary));
                   pubrepos.setText(jsonObject.getString("public_repos"));

                   final String repos =  jsonObject.getString("repos_url");

                   pubrepos.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           Intent i = new Intent(Repo_owner.this,Projectlist.class);
                           i.putExtra("repolink",repos);
                           startActivity(i);
                       }
                   });


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

       }catch (Exception e)
       {
           e.printStackTrace();
       }

   }

    public void flwClick() {
        disflw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Repo_owner.this, Followers.class);
                i.putParcelableArrayListExtra("flwlst", flwlst);
                startActivity(i);
            }
        });
    }

    public void flwData() throws InterruptedException, java.util.concurrent.ExecutionException {
        try {
            flwlst = new ArrayList<>();
            String flws = new AsyncData().execute(jsn).get();
            System.out.println("Check me " + flws);

            if (flws != null && !flws.isEmpty()) {

                JSONArray mainArray = new JSONArray(flws);

                System.out.println("Followers in Array  " + mainArray.toString());

                disflw.setTextColor(getColor(R.color.colorPrimary));
                disflw.setText(String.valueOf(mainArray.length()));


                if (mainArray.length() == 0) {
                    Toast.makeText(getApplicationContext(), "No Followers!", Toast.LENGTH_LONG).show();
                } else {
                    for (int i = 0; i < mainArray.length(); i++) {
                        JSONObject jobj = mainArray.getJSONObject(i);

                        flwlst.add(new Users(jobj.getString("login"), jobj.getString("url")));
                    }
                    flwClick();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
