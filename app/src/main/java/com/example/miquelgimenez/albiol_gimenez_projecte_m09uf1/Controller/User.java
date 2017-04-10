package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller;

/**
 * Created by gerard on 30/03/17.
 */

public class User {

    private String username;
    private String roomChat;

    public User(String username, String roomChat) {
        this.username = username;
        this.roomChat = roomChat;
    }

    public String getUsername() {
        return this.username;
    }

    public String getRoomChat() {
        return this.roomChat;
    }
}
