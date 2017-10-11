package com.a1694158.harshkumar.github;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Harsh on 9/20/2017.
 */

public class Flwlistadatpter extends BaseAdapter {

    Context c;
    ArrayList<Users> flwlst;
    LayoutInflater inflater;


    public Flwlistadatpter(Context c, ArrayList<Users> flwlst) {
        this.c = c;
        this.flwlst = flwlst;
    }

    @Override
    public int getCount() {
        return flwlst.size();
    }

    @Override
    public Object getItem(int i) {
        return flwlst.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if (inflater == null)
        {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null)
        {
            view = inflater.inflate(R.layout.listcontent,viewGroup,false);
        }

        TextView txtv = (TextView) view.findViewById(R.id.list_txtname);

        txtv.setText(flwlst.get(i).getName());

        final String path = flwlst.get(i).getPath();

        txtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(c, Repo_owner.class);
                i.putExtra("jsonstr", path);

                c.startActivity(i);

            }
        });

        return view;
    }
}
