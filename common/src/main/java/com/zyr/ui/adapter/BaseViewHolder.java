package com.zyr.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by xuekai on 2017/5/5.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    View convertView;
    Context context;

    public BaseViewHolder(View itemView, Context context) {
        super(itemView);
        this.convertView = itemView;
        this.context = context;
    }

    public void setText(int id, String text) {
        TextView tx = (TextView) convertView.findViewById(id);
        tx.setText(text);
    }

    public void setImageResource(int id, int resouceId) {
        ImageView img = (ImageView) convertView.findViewById(id);
        img.setImageResource(resouceId);
    }

    public void setOnClickListener(int id, View.OnClickListener listener) {
        TextView tx = (TextView) convertView.findViewById(id);
        if (listener == null) {
            tx.setClickable(false);
        } else {
            tx.setClickable(true);
            tx.setOnClickListener(listener);
        }
    }

    public void setTextColor(int id, int color) {
        TextView tx = (TextView) convertView.findViewById(id);
        tx.setTextColor(color);
    }

}