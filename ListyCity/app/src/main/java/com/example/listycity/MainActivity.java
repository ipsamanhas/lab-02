package com.example.listycity;


import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;




public class MainActivity extends AppCompatActivity {

    private ListView cityList;
    private ArrayList<String> dataList;
    private ArrayAdapter<String> cityAdapter;
    private EditText input;
    private Button addBtn, deleteBtn, confirmBtn;
    private int selectedIndex = -1;   // which city is selected


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        input      = findViewById(R.id.city_input);
        addBtn     = findViewById(R.id.add_button);
        deleteBtn  = findViewById(R.id.delete_button);
        confirmBtn = findViewById(R.id.confirm_button);


        // starter data
        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney",
                "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        dataList = new ArrayList<>(Arrays.asList(cities));

        // adapter: link data → row layout (content.xml → row_text TextView)
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, R.id.row_text, dataList);


        cityList.setAdapter(cityAdapter);
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;
            cityList.setItemChecked(position, true);
        });

        // adding city
        addBtn.setOnClickListener(v -> input.requestFocus());

        // confirming the city
        confirmBtn.setOnClickListener(v -> {
            String name = input.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Enter a city name", Toast.LENGTH_SHORT).show();
                return;
            }
            // avoiding duplicates
            for (String c : dataList) {
                if (c.equalsIgnoreCase(name)) {
                    Toast.makeText(this, "City already exists", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            dataList.add(name);
            cityAdapter.notifyDataSetChanged();
            input.setText("");

            selectedIndex = dataList.size() - 1;
            cityList.setItemChecked(selectedIndex, true);
        });

       // deleting the city
        deleteBtn.setOnClickListener(v -> {
            if (selectedIndex < 0 || selectedIndex >= dataList.size()) {
                Toast.makeText(this, "Tap a city first to select it", Toast.LENGTH_SHORT).show();
                return;
            }
            dataList.remove(selectedIndex);
            cityAdapter.notifyDataSetChanged();
            cityList.clearChoices();
            selectedIndex = -1;
        });



    }
}
