package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.View;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller.ListChatAdapter;
import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller.User;
import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gerard on 30/03/17.
 */
public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.etMessage) EditText message;
    @BindView(R.id.btnSend) Button send;
    @BindView(R.id.chatList) ListView chatList;

    ListChatAdapter chatAdapter;
    Intent obtainIntent = getIntent();
    User newUser;

    //TODO: arrays de mostra, borrar en un futur
    private final static String name[] = {"Gerard","Miquel","Gerard","Miquel","Gerard","Miquel","Gerard","Miquel","Gerard","Miquel","Gerard","Miquel"};
    private final static String body[] = {"Que vols?","FIFA?","No... Que em guanyes","Online?","Venga va!","El negre aquest es el puto millor jugador del joc","Que vols?","FIFA?","No... Que em guanyes","Online?","Venga va!","El negre aquest es el puto millor jugador del joc"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        ButterKnife.bind(this);

        //TODO: demoment el fem amb la sala per defecte, 'dam'
        if(obtainIntent!=null) {
            newUser = new User(obtainIntent.getStringExtra("name"),"dam");
        }

        chatAdapter = new ListChatAdapter(this, name, body);
        chatList.setAdapter(chatAdapter);




    }
}
