package com.example.cmpt276a2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmpt276a2.model.DepthCalculator;
import com.example.cmpt276a2.model.Lens;
import com.example.cmpt276a2.model.LensManager;

import java.text.DecimalFormat;

/**
 * Third activity extracts data, auto-recalculates, allows deletion and editing of lenses.
 */
public class ThirdActivity extends AppCompatActivity {

    private static final String EXTRA_LENS = "com.example.cmpt276a2.ThirdActivity - lensChosen";
    private LensManager manager = LensManager.getInstance();;
    DepthCalculator depthCalculator = new DepthCalculator();

    private int lensIdx;

    public static Intent makeIntent(Context context, int lensIdx) {
        Intent intent =  new Intent(context, ThirdActivity.class);
        intent.putExtra(EXTRA_LENS, lensIdx);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        extractDataFromIntent();
        setPhotoDetails();
        autoRecalculate();
        deleteLens();
        editLens();
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        lensIdx = intent.getIntExtra(EXTRA_LENS, 0);
    }

    @SuppressLint("SetTextI18n")
    private void setPhotoDetails() {
        TextView textView = findViewById(R.id.textViewInfo);
        textView.setText("Photo Details with " + manager.getLenses().get(lensIdx).toString());
    }

    private void autoRecalculate() {

        final int focalLength = manager.getLenses().get(lensIdx).getFocalLength();
        final double maxAperture = manager.getLenses().get(lensIdx).getMaxAperture();

        final EditText cocText = findViewById(R.id.cocInput);
        final EditText distanceText = findViewById(R.id.distanceInput);
        final EditText apertureText = findViewById(R.id.apertureInput);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {

                String cocInputString = cocText.getText().toString();
                String userDistanceInputString = distanceText.getText().toString();
                String userApertureInputString = apertureText.getText().toString();

                // Adapted from : https://stackoverflow.com/questions/6290531/check-if-edittext-is-empty
                if(TextUtils.isEmpty(cocInputString)) {
                    cocText.setError("Must have input");
                } else if(TextUtils.isEmpty(userDistanceInputString)) {
                    distanceText.setError("Must have input");
                } else if(TextUtils.isEmpty(userApertureInputString)) {
                    apertureText.setError("Must have input");
                } else {

                    double cocInput = Double.parseDouble(cocText.getText().toString());
                    double userDistanceInput = Double.parseDouble(distanceText.getText().toString());
                    double userApertureInput = Double.parseDouble(apertureText.getText().toString());

                    if(cocInput < 0) {
                        cocText.setError("Must be larger than 0");
                    } else if(userDistanceInput < 0) {
                        distanceText.setError("Must be larger than 0");
                    } else if(userApertureInput < maxAperture) {
                        apertureText.setError("Invalid aperture");
                    } else if(userApertureInput < 1.4) {
                        apertureText.setError("Must be larger than or equal to 1.4");
                    } else {
                        double hyperFocalDistance = depthCalculator.hyperFocalDistance(focalLength, userApertureInput, cocInput);
                        double nearFocalPoint = depthCalculator.nearFocalPoint(hyperFocalDistance, userDistanceInput, focalLength);
                        double farFocalPoint = depthCalculator.farFocalPoint(hyperFocalDistance, userDistanceInput, focalLength);
                        double depthOfField = depthCalculator.depthOfField(farFocalPoint, nearFocalPoint);

                        TextView hyperFocalTextView = findViewById(R.id.textViewHyperfocalDistance);
                        hyperFocalTextView.setText("Hyperfocal Distance: " + formatM(hyperFocalDistance) + "m");

                        TextView nearFocalTextView = findViewById(R.id.textViewNearFocalDistance);
                        nearFocalTextView.setText("Near Focal Distance: " + formatM(nearFocalPoint)+ "m");

                        TextView farFocalTextView= findViewById(R.id.textViewFarFocalDistance);
                        farFocalTextView.setText("Far Focal Distance: " + formatM(farFocalPoint)+ "m");

                        TextView depthOfFieldTextView = findViewById(R.id.textViewDepthOfField);
                        depthOfFieldTextView.setText("Depth Of Field: " + formatM(depthOfField)+ "m");

                        Intent returnIntent = getIntent();
                        setResult(Activity.RESULT_OK, returnIntent);
                    }
                }
            }
        };
        cocText.addTextChangedListener(textWatcher);
        distanceText.addTextChangedListener(textWatcher);
        apertureText.addTextChangedListener(textWatcher);
    }

    private String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(distanceInM);
    }

    private void deleteLens() {
        Button btn = findViewById(R.id.buttonDelete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.remove(manager.getLenses().get(lensIdx));

                Intent returnIntent = getIntent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

//    Adapted from : https://developer.android.com/training/contacts-provider/modify-data
    private void editLens() {
        Button btn = findViewById(R.id.buttonEdit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SecondActivity.makeIntent(ThirdActivity.this, lensIdx);
                startActivityForResult(intent, 1);

                Intent returnIntent = getIntent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
