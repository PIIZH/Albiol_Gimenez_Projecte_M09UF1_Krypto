package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.View;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller.User;
import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.R;

import butterknife.ButterKnife;

/**
 * Created by gerard on 30/03/17.
 */

public class ChatActivity extends AppCompatActivity{
    Intent obtainIntent = getIntent();
    User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        ButterKnife.bind(this);

        if(obtainIntent!=null){
            newUser = new User(obtainIntent.getStringExtra("name"),"dam");
        }

    }
}
