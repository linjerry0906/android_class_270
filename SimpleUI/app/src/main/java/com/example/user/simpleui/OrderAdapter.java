package com.example.user.simpleui;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 2016/7/13.
 */
public class OrderAdapter extends BaseAdapter {

    List<Order> orders;
    LayoutInflater layoutInflater;

    public OrderAdapter(Context context, List<Order> orders)
    {
        this.orders = orders;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.listview_order_item, null);
        }
        TextView noteTextView = (TextView)convertView.findViewById(R.id.noteTextView);
        TextView storeInfoTextView = (TextView)convertView.findViewById(R.id.storeTextView);
        TextView drinkNameTextView = (TextView)convertView.findViewById(R.id.drinkNameTextView);

        Order order = orders.get(position);
        noteTextView.setText(order.note);
        storeInfoTextView.setText(order.storeInfo);
        drinkNameTextView.setText(order.drinkName);

        return convertView;
    }
}
