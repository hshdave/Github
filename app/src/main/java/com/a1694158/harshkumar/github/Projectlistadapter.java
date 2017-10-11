package com.a1694158.harshkumar.github;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Harsh on 9/3/2017.
 */

public class Projectlistadapter extends BaseAdapter {

    ArrayList<Gitget> gitlist;
    Context c;
    LayoutInflater inflater;


    public Projectlistadapter(ArrayList<Gitget> gitlist, Context c) {
        this.gitlist = gitlist;
        this.c = c;
    }

    @Override
    public int getCount() {
        return gitlist.size();
    }

    @Override
    public Object getItem(int i) {
        return gitlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if (inflater == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null) {
            view = inflater.inflate(R.layout.listcontent, viewGroup, false);
        }


        final TextView txt_v = (TextView) view.findViewById(R.id.list_txtname);
        txt_v.setText(gitlist.get(i).getFull_name());


        txt_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c, txt_v.getText().toString(), Toast.LENGTH_LONG).show();


                String repourl = gitlist.get(i).getRespourl();

                Log.d("Click Repo URL    ", repourl);

                Intent i = new Intent(c, Repo_details.class);
                i.putExtra("repourl", repourl);
                c.startActivity(i);

            }
        });


        return view;
    }
}
