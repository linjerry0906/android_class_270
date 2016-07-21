package com.example.user.simpleui;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity implements  GeoCodingTask.GeoCodingResponse{

    GoogleMap googleMap;

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
        ImageView staticMapImageView = (ImageView)findViewById(R.id.googleMapImageView);


        noteTextView.setText(note);
        storeTextView.setText(storeInfo);

        List<String> menuResultList = Order.getMenuResultList(menuResults);

        String text = "";
        if(menuResultList != null) {
            for (String menuResult : menuResultList) {
                text += menuResult + "\n";
            }
            menuResultsTextView.setText(text);

            MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.mapFragment);

            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    googleMap = map;
                    (new GeoCodingTask(OrderDetailActivity.this)).execute("台北市大安區羅斯福路四段一號");
                }
            });

        }
    }

    @Override
    public void responseWithGeoCodingResults(LatLng latLng) {
        if(googleMap != null)
        {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            googleMap.animateCamera(cameraUpdate);
            //googleMap.moveCamera(cameraUpdate);
        }

    }

//    public static class GeoCodingTask extends AsyncTask<String, Void, Bitmap>
//    {
//
//        WeakReference<ImageView> imageViewWeakReference;
//
//        @Override
//        protected Bitmap doInBackground(String... params) {
//            String address = params[0];
//            double[] latlng = Utils.getLatLngFromGoogleMapAPI(address);
//            return Utils.getStaticMap(latlng);
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap)
//        {
//            super.onPostExecute(bitmap);
//            if(imageViewWeakReference.get() != null && bitmap != null)
//            {
//                ImageView imageView = imageViewWeakReference.get();
//                imageView.setImageBitmap(bitmap);
//            }
//        }
//
//        public GeoCodingTask(ImageView imageView)
//        {
//            this.imageViewWeakReference = new WeakReference<ImageView>(imageView);
//        }
//    }

}
