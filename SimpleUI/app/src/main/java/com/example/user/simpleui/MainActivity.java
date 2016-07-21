package com.example.user.simpleui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_CODE_DRINK_MENU_ACTIVITY = 0;

    TextView textView;
    EditText editText;
    RadioGroup radioGroup;
    ListView listView;
    Spinner spinner;

    String selectedTea = "black tea";

    String menuResults = "";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    List<Order> orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        editText = (EditText)findViewById(R.id.editText);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        listView = (ListView)findViewById(R.id.listView);
        spinner = (Spinner)findViewById(R.id.spinner);

        sharedPreferences = getSharedPreferences("setting", MODE_PRIVATE);
        editor = sharedPreferences.edit();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("spinner", position);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editText.setText(sharedPreferences.getString("editText", ""));
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String text = editText.getText().toString();
                editor.putString("editText", text);
                editor.commit();
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    submit(v);
                    return true;
                }
                return false;
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                selectedTea = radioButton.getText().toString();
            }
        });

//        String history = Utils.readFile(this, "history");
//        String[] datas = history.split("\n");
//        for(String data : datas)
//        {
//            Order order = Order.newInstanceWithData(data);
//            if(order != null)
//                orders.add(order);
//        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order = (Order)parent.getAdapter().getItem(position);
                goToDetail(order);
            }
        });

        setupListView();
        setupSpinner();



        Log.d("Debug", "MainActivity OnCreate");
    }
    public void setupListView()
    {
       ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo networkInfo = cm.getActiveNetworkInfo();

       FindCallback<Order>callback = new FindCallback<Order>() {
           @Override
           public void done(List<Order> objects, ParseException e) {
               orders = objects;
               OrderAdapter adapter = new OrderAdapter(MainActivity.this, orders);
               listView.setAdapter(adapter);
           }
       };

       if(networkInfo == null || !networkInfo.isConnected())
       {
           Order.getQuery().fromLocalDatastore().findInBackground(callback);
       }
       else
       {
           Order.getOrdersFromRemote(callback);
       }

    }
    public void setupSpinner()
    {
        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("StoreInfo");
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                List<String> storeInfos = new ArrayList<String>();
                for(ParseObject object : objects)
                {
                    String storeInfo = object.get("name") + " , " + object.get("address");
                    storeInfos.add(storeInfo);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, storeInfos);
                spinner.setAdapter(adapter);
            }
        });
//        String[] data = getResources().getStringArray(R.array.storeInfos);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, data);
//        spinner.setAdapter(adapter);
//        spinner.setSelection(sharedPreferences.getInt("spinner", 0));
    }
    public void submit(View view)
    {
        String text = editText.getText().toString();

        textView.setText(text);

        Order order = new Order();
        order.setNote(text);
        order.setMenuResults(menuResults);
        order.setStoreInfo((String) spinner.getSelectedItem());

        order.pinInBackground("Order");
        order.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                setupListView();
            }
        });

        Utils.writeFile(this, "history", order.toData() + "\n");

        orders.add(order);

        //setupListView();

        editText.setText("");
        menuResults = "";
    }

    public void goToMenu(View view)
    {
        Intent intent = new Intent();
        intent.setClass(this, DrinkMenuActivity.class);
        startActivityForResult(intent, REQUEST_CODE_DRINK_MENU_ACTIVITY);
    }

    public void goToDetail(Order order)
    {
        Intent intent = new Intent();
        intent.setClass(this, OrderDetailActivity.class);
        intent.putExtra("note", order.getNote());
        intent.putExtra("storeInfo", order.getStoreInfo());
        intent.putExtra("menuResults", order.getMenuResults());
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_DRINK_MENU_ACTIVITY)
        {
            if(resultCode == RESULT_OK)
            {
                Toast.makeText(this, "完成菜單", Toast.LENGTH_SHORT).show();

                menuResults = (data.getStringExtra("results"));
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "取消菜單", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Debug", "MainActivity OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Debug", "MainActivity OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Debug", "MainActivity OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Debug", "MainActivity OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Debug", "MainActivity OnDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Debug", "MainActivity OnRestart");
    }
}
