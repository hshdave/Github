package com.a1694158.harshkumar.github;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class Followers extends AppCompatActivity {

    Flwlistadatpter flwadapter;
    ListView lstflw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listitemlayout);

        lstflw = (ListView) findViewById(R.id.lst_follow);

        Intent i = getIntent();
        ArrayList<Users> flwlst = i.getParcelableArrayListExtra("flwlst");

        flwadapter = new Flwlistadatpter(Followers.this, flwlst);

        lstflw.setAdapter(flwadapter);

    }

}
