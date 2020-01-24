package com.example.cmpt276a2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmpt276a2.model.Lens;
import com.example.cmpt276a2.model.LensManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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
        floatingActionButton();
        clickLens();

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

    private void floatingActionButton() {
        FloatingActionButton myFab = findViewById(R.id.floatingActionButtonPlus);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Launch the second activity
                Intent intent = SecondActivity.makeIntent(MainActivity.this);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void clickLens() {

        ListView list = findViewById(R.id.listViewMain);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                String message = "You clicked # " + position +
                        ", which is string: " +
                        textView.getText().toString();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

                // Launch the third activity
                Intent intent = ThirdActivity.makeIntent(MainActivity.this, position);
                startActivity(intent);
            }
        });

    }

    // handles returned data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                populateListView();
            }
        }
    }
}
