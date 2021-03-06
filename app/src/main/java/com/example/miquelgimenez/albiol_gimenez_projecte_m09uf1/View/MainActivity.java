package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    @BindView(R.id.swEncryptionMode) Switch swEncryptionMode;
    @BindView(R.id.txvHelp) TextView txvHelp;
    @BindView(R.id.btnHelp) Button btnHelp;
    @BindView(R.id.tvMode) TextView mode;

    public static final String extraUsername = "com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Username";
    public static final String encryptMode = "com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.ChatRoom";

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


    @OnClick(R.id.swEncryptionMode)
    public void changeEtChatRoom(){

        etChatRoom.setText(null);

        if(!swEncryptionMode.isChecked()){
            //etChatRoom.setHint("Enter a ChatRoom");
            //etChatRoom.setFocusable(true);
            mode.setText("Symmetric");
        }
        else {
            //etChatRoom.setHint("Default ChatRoom");
            //etChatRoom.setFocusable(false);
            mode.setText("Asymetric");
       }

    }


    @OnClick(R.id.btnStartChat)
    public void intentChatActivity(View view){

        Intent chatAct_intent = new Intent(this, ChatActivity.class);

        if(etUsername.getText().length() > 0){

            chatAct_intent.putExtra(extraUsername, etUsername.getText().toString());
            chatAct_intent.putExtra(encryptMode, mode.getText().toString());

            //if(etChatRoom.isFocusable() && etUsername.getText().length() > 0){
            //chatAct_intent.putExtra(encryptMode, mode.getText().toString());
            //}
            //else {
            //chatAct_intent.putExtra(encryptMode, "dam");
            //}

            startActivity(chatAct_intent);
        }
        else {
            Toast.makeText(this,"You need to specify a username to access to the chat",
                            Toast.LENGTH_LONG).show();
        }

    }

}
