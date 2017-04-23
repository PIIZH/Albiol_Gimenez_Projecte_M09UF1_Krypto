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

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

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

//    User newUser = new User();
    public ListChatAdapter chatAdapter;
    public String username;
    public String roomChat;

    protected ArrayList<String> name = new ArrayList<>();
    protected ArrayList<String> body = new ArrayList<>();

    private static final String KEYMODE = "DES";

    private static final String IP = "192.168.1.41";
    private static final String PORT = "30002";

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
        socket.on("message", handleIncomingMessages);

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

    }

    /**
     * Update the adapter
     */
    private void updateChat(String u, String m) {

        name.add(u);
        body.add(m);

        System.out.println("updateChat: " + name.toString() + ", " + body.toString());

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

        //symmetricEncrypted(message.getText().toString());

        socket.emit("message", username, message.getText().toString());
        updateChat(username, message.getText().toString());
        //scrollBottom();

        message.setText("");

    }

    /**
     * Scroll to the bottom of the view
     */
    public void scrollBottom() {
        chatList.smoothScrollToPosition(chatAdapter.getCount() - 1);
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
                    String u = "";
                    String m = "";

                    try {
                        u = data.getString("name");
                        m = data.getString("message");
                    }
                    catch(JSONException e) {
                        System.out.println(e.getMessage());
                    }

                    updateChat(u, m);
                }

            });

        }

    };


    /*
    public String symmetricEncrypted(String t) {

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(KEYMODE);
            SecretKey secretKey = keyGen.generateKey();

            Cipher cripto;
            cripto = Cipher.getInstance("DES/ECB/PKCS5Padding");

            byte[] text = t.getBytes();

            System.out.println("Text [Byte Format]: " + text);
            System.out.println("Text: " + new String(text));

            cripto.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] textEncrypted = cripto.doFinal(text);
            System.out.println("Text encriptat: " + textEncrypted);

            //desencripta
            cripto.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] textDecrypted = cripto.doFinal(textEncrypted);
            System.out.println("Text desencriptat: " + new String(textDecrypted));

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
