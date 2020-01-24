package com.example.cmpt276a2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cmpt276a2.model.DepthCalculator;
import com.example.cmpt276a2.model.Lens;
import com.example.cmpt276a2.model.LensManager;

public class ThirdActivity extends AppCompatActivity {

    private LensManager manager = LensManager.getInstance();
    DepthCalculator depthCalculator = new DepthCalculator();

    private int lens = 0;

    public static Intent makeIntent(Context context, int lens) {
        Intent intent =  new Intent(context, ThirdActivity.class);
        this.lens = lens;
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        setCalculateActivityButton();
        extractDataFromIntent();
    }

    private void setCalculateActivityButton() {

        Lens lens = manager.getLenses().get(lens);

        Button btn = findViewById(R.id.buttonCalculate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = findViewById(R.id.distanceInput);
                double userMakeInput = Double.parseDouble(editText.getText().toString());

                editText = findViewById(R.id.apertureInput);
                double userApertureInput = Double.parseDouble(editText.getText().toString());

                depthCalculator.hyperFocalDistance();

                Intent returnIntent = getIntent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            }
        });
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        int focalLength = intent.getIntExtra();

    }
}
