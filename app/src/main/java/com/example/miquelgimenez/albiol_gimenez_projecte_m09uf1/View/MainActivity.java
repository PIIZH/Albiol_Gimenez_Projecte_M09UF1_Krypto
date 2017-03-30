package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.etUsername) EditText etUsername;
    @BindView(R.id.etChatRoom) EditText etChatRoo;
    @BindView(R.id.swDefaultChatRoom) Switch swDefaultChatRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        etChatRoo.setFocusable(false);

    }

    @OnFocusChange(R.id.swDefaultChatRoom)
    public void changeEtChatRoom(){
        if(!(swDefaultChatRoom.isChecked())){
            etChatRoo.setFocusable(false);
        }else etChatRoo.setFocusable(true);
    }



    @OnClick(R.id.btnStartChat)
    public void intentChatActivity(View view){
        if(etUsername.getText().toString().trim().length()>0){
            Intent chatAct_intent = new Intent(this, ChatActivity.class);
            startActivity(chatAct_intent);

        }else Toast.makeText(this,"Has d'introduir un nom d'usuari per poder entrar!",Toast.LENGTH_LONG).show();
    }
}
