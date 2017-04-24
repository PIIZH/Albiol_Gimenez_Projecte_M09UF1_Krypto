package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller;

/**
 * Created by gerard on 30/03/17.
 */

//TODO: Deprecated
public class User {

    private String username;
    private String roomChat;

    public User(){}

    public User(String username, String roomChat) {
        this.username = username;
        this.roomChat = roomChat;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setRoomChat(String roomChat) {
        this.roomChat = roomChat;
    }

    public String getRoomChat() {
        return this.roomChat;
    }
}
