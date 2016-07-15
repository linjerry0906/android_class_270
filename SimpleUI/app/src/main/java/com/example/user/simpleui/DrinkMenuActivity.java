package com.example.user.simpleui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DrinkMenuActivity extends AppCompatActivity implements DrinkOrderDialog.OnDrinkOrderListener{

    TextView totalTextView;
    ListView drinkMenuListView;

    String[] names = {"冬瓜紅茶", "玫瑰鹽奶蓋紅茶", "珍珠紅茶拿鐵", "紅茶拿鐵"};
    int[] mPrices = {25, 35, 45, 35};
    int[] lPrices = {35, 45, 55, 45};
    int[] imageId = {R.drawable.drink1, R.drawable.drink2, R.drawable.drink3, R.drawable.drink4};

    List<Drink> drinks = new ArrayList<>();
    List<Drink> orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_menu);
        Log.d("Debug", "DrinkMenuActivity OnCreate");
        setData();

        totalTextView = (TextView)findViewById(R.id.totalTextView);
        drinkMenuListView = (ListView)findViewById(R.id.drinkMenuListView);

        setupDrinkMenu();
    }

    private void setupDrinkMenu()
    {
        final DrinkAdapter drinkAdapter = new DrinkAdapter(this, drinks);
        drinkMenuListView.setAdapter(drinkAdapter);

        drinkMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DrinkAdapter drinkAdapter = (DrinkAdapter) parent.getAdapter();
                Drink drink = (Drink) drinkAdapter.getItem(position);
                showDrinkOrderDialog(drink);
            }
        });
    }
    public void showDrinkOrderDialog(Drink drink)
    {
        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction ft = fragmentManager.beginTransaction();

        DrinkOrderDialog dialog = DrinkOrderDialog.newInstance("", "");
        Fragment prev = getFragmentManager().findFragmentByTag("DrinkOrderDialog");
        if(prev != null)
        {
            ft.remove(prev);
        }

        ft.addToBackStack(null);

        dialog.show(ft, "DrinkOrderDialog");

    }
    public void updateTotal()
    {
        int total = 0;
        for(Drink drink: orders)
        {
            total += drink.mPrice;
        }

        totalTextView.setText(String.valueOf(total));
    }

    public void setData()
    {
        for(int i = 0; i < names.length; i++)
        {
            Drink drink = new Drink();
            drink.name = names[i];
            drink.mPrice = mPrices[i];
            drink.lPrice = lPrices[i];
            drink.imageId = imageId[i];
            drinks.add(drink);
        }
    }
    public void done(View view)
    {
        Intent intent = new Intent();

        JSONArray jsonArray = new JSONArray();
        for(Drink drink : orders)
        {
            JSONObject jsonObject = drink.getJsonObject();
            jsonArray.put(jsonObject);
        }
        intent.putExtra("results", jsonArray.toString());

        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancel(View view)
    {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        setResult(RESULT_CANCELED, intent);
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Debug", "DrinkMenuActivity OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Debug", "DrinkMenuActivity OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Debug", "DrinkMenuActivity OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Debug", "DrinkMenuActivity OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Debug", "DrinkMenuActivity OnDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Debug", "DrinkMenuActivity OnRestart");
    }

    @Override
    public void onDrinkOrderFinished() {

    }
}
