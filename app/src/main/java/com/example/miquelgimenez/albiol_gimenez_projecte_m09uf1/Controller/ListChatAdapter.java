package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.R;

import java.util.ArrayList;


public class ListChatAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<String> user;
    private ArrayList<String> message;
    private String username;


    public ListChatAdapter(Activity context, ArrayList<String> user, ArrayList<String> message, String username) {
        super();
        this.context = context;
        this.user = user;
        this.message = message;
        this.username = username;
    }

    @Override
    public int getCount() {
        return user.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView txtViewUser;
        TextView txtViewBody;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(!user.get(position).equals(this.username)) {
            return other(position, convertView);
        }

        ViewHolder holder;

        LayoutInflater inflater = context.getLayoutInflater();

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.message_item_chat, null);
            holder = new ViewHolder();
            holder.txtViewUser = (TextView) convertView.findViewById(R.id.txtUser);
            holder.txtViewBody = (TextView) convertView.findViewById(R.id.txtBody);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtViewUser.setText(user.get(position));
        holder.txtViewBody.setText(message.get(position));

        return convertView;
    }

    /**
     * Render messages from other people
     *
     * @param   {Number}    position
     * @param   {View}  convertView
     * @return  View
     */
    public View other(int position, View convertView) {
        ViewHolder holder;

        LayoutInflater inflater = context.getLayoutInflater();

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.message_item_chat_other, null);
            holder = new ViewHolder();
            holder.txtViewUser = (TextView) convertView.findViewById(R.id.txtUserOther);
            holder.txtViewBody = (TextView) convertView.findViewById(R.id.txtBodyOther);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtViewUser.setText(user.get(position));
        holder.txtViewBody.setText(message.get(position));

        return convertView;
    }

}
