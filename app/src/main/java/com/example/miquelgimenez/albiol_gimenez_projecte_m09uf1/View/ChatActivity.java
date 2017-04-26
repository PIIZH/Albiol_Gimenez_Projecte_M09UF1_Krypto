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
import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller.SymmetricUtil;
import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.R;

import java.net.URISyntaxException;
import java.util.ArrayList;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

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

    public ListChatAdapter chatAdapter;
    public String username;
    public Boolean encryptChat;

    protected ArrayList<String> name = new ArrayList<>();
    protected ArrayList<String> body = new ArrayList<>();

    private static final String IP = "172.20.18.53";
    private static final String PORT = "30002";
    private SymmetricUtil sym;

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
            System.out.println("encryptChat: " + encryptChat);
        }

        sym = new SymmetricUtil();
        sym.makeKey();

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
     *
     * @param   {View}  view
     */
    @OnClick(R.id.btnSend)
    public void sendMessage(View view) {

//        System.out.println("encrypt: " + sym.encrypt(message.getText().toString()));
//        System.out.println("decrypt: " + sym.decrypt(sym.encrypt(message.getText().toString())));
        socket.emit("message", username, sym.encrypt(message.getText().toString()));
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
                        String m = sym.decrypt(data.getString("message"));
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


    //TODO: no borrar demoment
    /*
    public String symmetricEncrypted(String t) {

        try {
            //KeyGenerator keyGen = KeyGenerator.getInstance(KEYMODE);
            //SecretKey secretKey = keyGen.generateKey();

            //Cipher cripto;
            //cripto = Cipher.getInstance("DES/ECB/PKCS5Padding");

            byte[] text = t.getBytes();

            System.out.println("Text [Byte Format]: " + text);
            System.out.println("Text: " + new String(text));

            //cripto.init(Cipher.ENCRYPT_MODE, secretKey);
            //byte[] textEncrypted = cripto.doFinal(text);
            //System.out.println("Text encriptat: " + textEncrypted);

            //cripto.init(Cipher.DECRYPT_MODE, secretKey);
            //byte[] textDecrypted = cripto.doFinal(textEncrypted);
            //System.out.println("Text desencriptat: " + new String(textDecrypted));

            return "";
        }
        catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                BadPaddingException | IllegalBlockSizeException e) {
            System.err.println(e.getMessage());
        }

        return "Something is wrong";

    }
    */

    @Override
    public void onDestroy() {
        super.onDestroy();

        socket.disconnect();
    }
}
