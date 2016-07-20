package com.example.user.simpleui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Intent intent = getIntent();
        String note = intent.getStringExtra("note");
        String menuResults = intent.getStringExtra("menuResults");
        String storeInfo = intent.getStringExtra("storeInfo");

        TextView noteTextView = (TextView)findViewById(R.id.noteTextView);
        TextView menuResultsTextView = (TextView)findViewById(R.id.menuResultsTextView);
        TextView storeTextView = (TextView)findViewById(R.id.storeInfoTextView);

        noteTextView.setText(note);
        storeTextView.setText(storeInfo);

        List<String> menuResultList = Order.getMenuResultList(menuResults);

        String text = "";
        if(menuResultList != null) {
            for (String menuResult : menuResultList) {
                text += menuResult + "\n";
            }
            menuResultsTextView.setText(text);



        }
    }

    public static class GeoCodingTask extends AsyncTask<String, Void, Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... params) {
            String address = params[0];
            double[] latlng = Utils.getLatLngFromGoogleMapAPI(address);
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }
    }

}
