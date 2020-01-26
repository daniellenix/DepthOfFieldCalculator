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

    private static final String EXTRA_LENS = "com.example.cmpt276a2.SecondActivity - lensChosen";
    private LensManager manager = LensManager.getInstance();

    private int lensIdx;

    public static Intent makeIntent(Context context, int lensIdx) {
        Intent intent =  new Intent(context, SecondActivity.class);
        intent.putExtra(EXTRA_LENS, lensIdx);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        extractDataFromIntent();
        populateEditText();
        setupCancelActivityButton();
        setupSaveActivityButton();
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        lensIdx = intent.getIntExtra(EXTRA_LENS, 0);
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

                    manager.getLenses().get(lensIdx).setMake(userMakeInput);
                    manager.getLenses().get(lensIdx).setFocalLength(userFocalLengthInput);
                    manager.getLenses().get(lensIdx).setMaxAperture(userApertureInput);

                    manager.remove(manager.getLenses().get(lensIdx));

                    Intent returnIntent = getIntent();
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });
    }


    private void populateEditText() {
        EditText makeText = findViewById(R.id.makeInput);
        makeText.setText("" + manager.getLenses().get(lensIdx).getMake());

        EditText focalLengthText = findViewById(R.id.focalLengthInput);
        focalLengthText.setText("" + manager.getLenses().get(lensIdx).getFocalLength());

        EditText apertureText = findViewById(R.id.apertureInput);
        apertureText.setText("" + manager.getLenses().get(lensIdx).getMaxAperture());
    }

}
