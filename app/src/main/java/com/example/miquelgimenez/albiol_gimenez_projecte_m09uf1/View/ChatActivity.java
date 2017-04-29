package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.View;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller.ListChatAdapter;
import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller.Symetric.SymmetricUtil;
import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller.Asymetric.AsymetricUtil;
import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.R;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ChatActivity extends AppCompatActivity {
    @BindView(R.id.etMessage) EditText message;
    @BindView(R.id.btnSend) Button send;
    @BindView(R.id.chatList) ListView chatList;

    public ListChatAdapter chatAdapter;
    public String username;
    public Boolean encryptChat;

    protected ArrayList<String> name = new ArrayList<>();
    protected ArrayList<String> body = new ArrayList<>();

    private static final String IP = "192.168.1.38";
    private static final String PORT = "30002";
    private SymmetricUtil sym;
    private AsymetricUtil asym;

    private Socket socket;
    {
        try {
            socket = IO.socket("http://" + IP + ":" + PORT);
        }
        catch(URISyntaxException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        ButterKnife.bind(this);

        socket.connect();
        socket.on("messageServer", handleIncomingMessages);

        Intent obtainIntent = getIntent();

        if(obtainIntent != null) {
            username = obtainIntent.getStringExtra(MainActivity.extraUsername);
            encryptChat = obtainIntent.getStringExtra(MainActivity.encryptMode).equals("Symmetric");
        }

        if(encryptChat) {
            sym = new SymmetricUtil();
            sym.makeKey();
        }
        else {
            asym = new AsymetricUtil();
        }

    }

    /**
     * Update the adapter
     */
    private void updateChat(String u, String m) {

        name.add(u);
        body.add(m);

        chatAdapter = new ListChatAdapter(this, name, body, username);
        chatList.setAdapter(chatAdapter);

    }

    /**
     * Click event to send the message
     *+
     * @param   {View}  view
     */
    @OnClick(R.id.btnSend)
    public void sendMessage(View view){

//        System.out.println("encrypt symetric: " + sym.encrypt(message.getText().toString()));
//        System.out.println("decrypt symetric : " + sym.decrypt(sym.encrypt(message.getText().toString()))+"\n");
//
//        try {
//            System.out.println("encrypt asymetric :"+asym.RSAEncrypt(message.getText().toString()));
//            System.out.println("decrypt asymetric :"+asym.RSADecrypt(asym.RSAEncrypt(message.getText().toString())));
//        } catch (NoSuchAlgorithmException |NoSuchPaddingException |InvalidKeyException | IllegalBlockSizeException| BadPaddingException|UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        if(encryptChat){
            socket.emit("message", username, sym.encrypt(message.getText().toString()), sym.getMyKey());
        }
        else {
            try {
                socket.emit("message", username, asym.RSAEncrypt(message.getText().toString()));
            }
            catch (NoSuchAlgorithmException |NoSuchPaddingException |InvalidKeyException | IllegalBlockSizeException| BadPaddingException|UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        updateChat(username, message.getText().toString());
        message.setText("");

    }


    /**
     * Handler messages
     */
    private Emitter.Listener handleIncomingMessages = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    System.out.println(data);
                    try {
                        String u = data.getString("user");
                        String m = null;
                        try {
                            m = encryptChat ?
                                    sym.decrypt(data.getString("message"), data.getString("key")) :
                                    asym.RSADecrypt("message");
                        } catch (NoSuchAlgorithmException |NoSuchPaddingException |InvalidKeyException |
                                IllegalBlockSizeException| BadPaddingException|UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }


                        System.out.println("soc la m: " + m);
                        updateChat(u, m);
                    }
                    catch(JSONException e) {
                        System.out.println(e.getMessage());
                    }

                }

            });

        }

    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}
