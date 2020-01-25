package com.example.cmpt276a2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cmpt276a2.model.Lens;
import com.example.cmpt276a2.model.LensManager;

public class SecondActivity extends AppCompatActivity {

    private LensManager manager = LensManager.getInstance();

    public static Intent makeIntent(Context context) {
        Intent intent =  new Intent(context, SecondActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setupCancelActivityButton();
        setupSaveActivityButton();
    }

    private void setupCancelActivityButton() {
        Button btn = findViewById(R.id.buttonCancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }

    private void setupSaveActivityButton() {
        Button btn = findViewById(R.id.buttonSave);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText makeText = findViewById(R.id.makeInput);
                String userMakeInput = makeText.getText().toString();

                EditText focalLengthText = findViewById(R.id.focalLengthInput);
                int userFocalLengthInput = Integer.parseInt(focalLengthText.getText().toString());

                EditText apertureText = findViewById(R.id.apertureInput);
                double userApertureInput = Double.parseDouble(apertureText.getText().toString());

                if(userMakeInput.length() == 0) {
                    makeText.setError("Must be longer than 0");
                } else if(userFocalLengthInput < 0) {
                    focalLengthText.setError("Must be larger than 0");
                } else if(userApertureInput < 1.4) {
                    apertureText.setError("Must be larger than or equal to 1.4");
                } else {
                    manager.add(new Lens(userMakeInput, userApertureInput, userFocalLengthInput));

                    Intent returnIntent = getIntent();
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });
    }





}
