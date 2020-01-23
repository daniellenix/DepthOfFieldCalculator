package com.example.cmpt276a2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmpt276a2.model.Lens;
import com.example.cmpt276a2.model.LensManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private LensManager manager = LensManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateLensList();
        populateListView();
//        registerClickCallback();
    }

    private void populateLensList() {
        // Create list of items
        manager.add(new Lens("Canon", 1.8, 50));
        manager.add(new Lens("Tamron", 2.8, 90));
        manager.add(new Lens("Sigma", 2.8, 200));
        manager.add(new Lens("Nikon", 4, 200));
    }

    private void populateListView() {
        // Build adapter
        ArrayAdapter<Lens> adapter = new ArrayAdapter<>(this, R.layout.lens_list, manager.getLenses());

        // Configure the list view
        ListView list = findViewById(R.id.listViewMain);
        list.setAdapter(adapter);
    }


//    private void registerClickCallback() {
//        ListView list = findViewById(R.id.listViewMain);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TextView textView = (TextView) view;
//                String message = "You clicked # " + position +
//                        ", which is string: " +
//                        textView.getText().toString();
//                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
//            }
//        });

}
