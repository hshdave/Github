package com.a1694158.harshkumar.github;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class loginActivity extends AppCompatActivity {

    EditText edt_search;
    Button btn_search;
    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_search = (EditText) findViewById(R.id.projrct_sch);
        btn_search = (Button) findViewById(R.id.sch_btn);


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = edt_search.getText().toString();

                Intent i = new Intent(loginActivity.this, Projectlist.class);
                i.putExtra("search", key);

                startActivity(i);
            }
        });


    }

}
