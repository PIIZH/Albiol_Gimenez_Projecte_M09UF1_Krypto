package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity{

    @BindView(R.id.etUsername) EditText etUsername;
    @BindView(R.id.etChatRoom) EditText etChatRoom;
    @BindView(R.id.swDefaultChatRoom) Switch swDefaultChatRoom;
    @BindView(R.id.txvHelp) TextView txvHelp;
    @BindView(R.id.btnHelp) Button btnHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);

        btnHelp.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        txvHelp.setVisibility(View.VISIBLE);
                        return true;

                    case MotionEvent.ACTION_UP:
                        txvHelp.setVisibility(View.INVISIBLE);
                        return true;
                }

                return false;
            }

        });
    }


    @OnClick(R.id.swDefaultChatRoom)
    public void changeEtChatRoom(){

        etChatRoom.setText(null);

        if(!swDefaultChatRoom.isChecked()){
            etChatRoom.setHint("Enter a ChatRoom");
            etChatRoom.setFocusable(true);
        }
        else {
           etChatRoom.setHint("Default ChatRoom");
           etChatRoom.setFocusable(false);
       }

    }


    @OnClick(R.id.btnStartChat)
    public void intentChatActivity(View view){

        Intent chatAct_intent = new Intent(this, ChatActivity.class);

        if(etUsername.getText().length() > 0){

            chatAct_intent.putExtra("user",etUsername.getText());

            if(etChatRoom.isFocusable() && etUsername.getText().length() > 0){
                chatAct_intent.putExtra("chatRoom",etChatRoom.getText());
            }
            else {
                chatAct_intent.putExtra("chatRoom","dam");
            }

            startActivity(chatAct_intent);
        }
        else {
            Toast.makeText(this,"You need to specify a username to access to the chat",
                            Toast.LENGTH_LONG).show();
        }

    }

}
