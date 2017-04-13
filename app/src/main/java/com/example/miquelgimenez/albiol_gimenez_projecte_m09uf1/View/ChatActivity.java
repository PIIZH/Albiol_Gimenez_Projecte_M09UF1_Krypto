package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.View;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller.ListChatAdapter;
import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller.User;
import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gerard on 30/03/17.
 */
public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.etMessage) EditText message;
    @BindView(R.id.btnSend) Button send;
    @BindView(R.id.chatList) ListView chatList;

//    User newUser = new User();
    public ListChatAdapter chatAdapter;
    public String username;
    public String roomChat;

    protected ArrayList<String> name = new ArrayList<>();
    protected ArrayList<String> body = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        ButterKnife.bind(this);

        Intent obtainIntent = getIntent();

        if(obtainIntent != null) {
            username = obtainIntent.getStringExtra(MainActivity.extraUsername);
//            roomChat = obtainIntent.getStringExtra(MainActivity.extraChatRoom);
            roomChat = "dam";

            //TODO: realment es necesari?  Deixar demoment per si al fer el server ens cal
////            newUser = new User(obtainIntent.getStringExtra("name"),"dam");
//            newUser.setUsername(obtainIntent.getStringExtra("name"));
////            newUser.setRoomChat(obtainIntent.getStringExtra("room"));
//            newUser.setRoomChat("dam");
        }

        updateChat();

    }

    /**
     * Update the adapter
     */
    private void updateChat() {

        chatAdapter = new ListChatAdapter(this, name, body);
        chatList.setAdapter(chatAdapter);

    }

    /**
     * Click event to send the message
     *
     * @param   {View}  view
     */
    @OnClick(R.id.btnSend)
    public void sendMessage(View view) {

        name.add(username);
        body.add(message.getText().toString());

        message.setText("");

        updateChat();

    }
}
