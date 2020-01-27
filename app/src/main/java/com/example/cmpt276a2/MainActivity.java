package com.example.cmpt276a2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Main activity adds lenses to screen, launches second activity to add lens and launches third activity to calculate.
 */
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
        manager.add(new Lens("Canon", 1.8, 50, R.drawable.camera1));
        manager.add(new Lens("Tamron", 2.8, 90, R.drawable.camera2));
        manager.add(new Lens("Sigma", 2.8, 200, R.drawable.camera3));
        manager.add(new Lens("Nikon", 4, 200, R.drawable.camera4));
    }

    private void populateListView() {
        ArrayAdapter<Lens> adapter = new MyListAdapter();
        ListView list = findViewById(R.id.listViewMain);
        list.setAdapter(adapter);

//        Adapted from: https://stackoverflow.com/questions/10017088/android-displaying-text-when-listview-is-empty
        TextView textView = findViewById(R.id.textViewNoLensMessage);
        list.setEmptyView(textView);
    }

    private class MyListAdapter extends ArrayAdapter<Lens> {

        public MyListAdapter() {
            super(MainActivity.this, R.layout.item_view, manager.getLenses());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            // make sure we have a view to work with
            View itemView = convertView;

            if(itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            // find the lens to work with
            Lens currentLens = manager.getLenses().get(position);

            // fill the view
            ImageView imageView = itemView.findViewById(R.id.itemIcon);
            imageView.setImageResource(currentLens.getIconID());

            TextView textView = itemView.findViewById(R.id.item_text);
            textView.setText(currentLens.toString());

            return itemView;
        }
    }

    private void floatingActionButton() {
        FloatingActionButton myFab = findViewById(R.id.floatingActionButtonPlus);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Launch the second activity
                Intent intent = SecondActivity.makeIntent(MainActivity.this, -1);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void clickLens() {
        ListView list = findViewById(R.id.listViewMain);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lens clickedLens = manager.getLenses().get(position); ;
                String message = "You clicked # " + position + ", which is string: " + clickedLens.toString();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

                // Launch the third activity
                Intent intent = ThirdActivity.makeIntent(MainActivity.this, position);
                startActivityForResult(intent, 1);
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
