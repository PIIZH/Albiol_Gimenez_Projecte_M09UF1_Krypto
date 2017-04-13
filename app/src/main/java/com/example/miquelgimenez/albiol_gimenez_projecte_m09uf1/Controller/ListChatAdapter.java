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

/**
 * Created by gerard on 11/04/17.
 */

public class ListChatAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<String> user;
    private ArrayList<String> message;


    public ListChatAdapter(Activity context, ArrayList<String> user, ArrayList<String> message) {
        super();
        this.context = context;
        this.user = user;
        this.message = message;
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

//        TODO: no funciona el canvi d'alineacio, fer un altre xml amb el text a l'altra banda?
//        if(!user.get(position).equals("gerard")) {
//            holder.txtViewUser.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
//            holder.txtViewBody.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
//        }

        return convertView;
    }

}
