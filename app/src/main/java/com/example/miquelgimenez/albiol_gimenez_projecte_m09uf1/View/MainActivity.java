package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btnStartChat) Button btnStartChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toast.makeText(this, "666",Toast.LENGTH_LONG).show();
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btnStartChat)
    public void intentChatActivity(View view){
        Toast.makeText(this, "jkowefnomsolmvñnvinj",Toast.LENGTH_LONG).show();
        Intent chatAct_intent = new Intent(getApplicationContext(), ChatActivity.class);
        startActivity(chatAct_intent);
    }
}
