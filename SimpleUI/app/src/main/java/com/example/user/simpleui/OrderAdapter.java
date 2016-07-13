package com.example.user.simpleui;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        Holder holder;
        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.listview_order_item, null);
            TextView noteTextView = (TextView)convertView.findViewById(R.id.noteTextView);
            TextView storeInfoTextView = (TextView)convertView.findViewById(R.id.storeTextView);
            TextView drinkNameTextView = (TextView)convertView.findViewById(R.id.drinkNameTextView);
            holder = new Holder();
            holder.noteTextview = noteTextView;
            holder.storeInfoTextView = storeInfoTextView;
            holder.drinkNameTextView = drinkNameTextView;

            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder)convertView.getTag();
        }
        Order order = orders.get(position);
        holder.noteTextview.setText(order.note);
        holder.storeInfoTextView.setText(order.storeInfo);
        holder.drinkNameTextView.setText(order.drinkName);

        return convertView;
    }

    class Holder
    {
        TextView drinkNameTextView;
        TextView storeInfoTextView;
        TextView noteTextview;
    }
}
