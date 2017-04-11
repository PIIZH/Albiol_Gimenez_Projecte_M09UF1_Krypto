package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.R;

/**
 * Created by gerard on 11/04/17.
 */

public class ListChatAdapter extends BaseAdapter {

    Activity context;
    String[] user;
    String[] message;

    /**
     * Constructor Custom ListView
     *
     * @param   {Activity}  context
     * @param   {String[]}  user
     * @param   {String[]}  message
     */
    public ListChatAdapter(Activity context, String[] user, String[] message) {
        super();
        this.context = context;
        this.user = user;
        this.message = message;
    }

    @Override
    public int getCount() {
        return user.length;
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

        holder.txtViewUser.setText(user[position]);
        holder.txtViewBody.setText(message[position]);

        if(!user[position].equals("gerard")) {
            holder.txtViewUser.setGravity(Gravity.LEFT);
            holder.txtViewBody.setGravity(Gravity.LEFT);
        }

        return convertView;
    }

}
