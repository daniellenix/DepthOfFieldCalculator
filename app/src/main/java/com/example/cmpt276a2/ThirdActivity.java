package com.example.cmpt276a2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cmpt276a2.model.DepthCalculator;
import com.example.cmpt276a2.model.Lens;
import com.example.cmpt276a2.model.LensManager;

import java.text.DecimalFormat;

public class ThirdActivity extends AppCompatActivity {

    private static final String EXTRA_LENS = "com.example.cmpt276a2.ThirdActivity - lensChosen";
    private LensManager manager = LensManager.getInstance();
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
        setCalculateActivityButton();
    }

    private void setPhotoDetails() {
        TextView textView = findViewById(R.id.textViewInfo);
        textView.append(manager.getLenses().get(lensIdx).toString());
    }

    private void setCalculateActivityButton() {

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

            @Override
            public void afterTextChanged(Editable s) {

                String cocInputString = cocText.getText().toString();
                String userDistanceInputString = distanceText.getText().toString();
                String userApertureInputString = apertureText.getText().toString();

                // Adapted from : https://stackoverflow.com/questions/6290531/check-if-edittext-is-empty
                if(TextUtils.isEmpty(cocInputString) || TextUtils.isEmpty(userDistanceInputString) || TextUtils.isEmpty(userApertureInputString)) {
                    return;
                } else {
                    double cocInput = Double.parseDouble(cocText.getText().toString());
                    double userDistanceInput = Double.parseDouble(distanceText.getText().toString());
                    double userApertureInput = Double.parseDouble(apertureText.getText().toString());

                    if(cocInput < 0) {
                        cocText.setError("Must be larger than 0");
                    } else if(userDistanceInput < 0) {
                        distanceText.setError("Must be larger than 0");
                    } else if(userApertureInput < 1.4) {
                        apertureText.setError("Must be larger than or equal to 1.4");
                    } else if(userApertureInput < maxAperture) {
                        apertureText.setError("Invalid aperture");
                    } else {

                        double hyperFocalDistance = Double.parseDouble(formatM(depthCalculator.hyperFocalDistance(focalLength, userApertureInput, cocInput)));
                        double nearFocalPoint = Double.parseDouble(formatM(depthCalculator.nearFocalPoint(hyperFocalDistance, userDistanceInput, focalLength)));
                        double farFocalPoint = Double.parseDouble(formatM(depthCalculator.farFocalPoint(hyperFocalDistance, userDistanceInput, focalLength)));
                        double depthOfField = Double.parseDouble(formatM(depthCalculator.depthOfField(farFocalPoint, nearFocalPoint)));

                        TextView hyperFocalTextView = findViewById(R.id.textViewHyperfocalDistance);
                        hyperFocalTextView.append(" : " + hyperFocalDistance);

                        TextView nearFocalTextView = findViewById(R.id.textViewNearFocalDistance);
                        nearFocalTextView.append(" : " + nearFocalPoint);

                        TextView farFocalTextView= findViewById(R.id.textViewFarFocalDistance);
                        farFocalTextView.append(" : " + farFocalPoint);

                        TextView depthOfFieldTextView = findViewById(R.id.textViewDepthOfField);
                        depthOfFieldTextView.append(" : " + depthOfField);

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

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        lensIdx = intent.getIntExtra(EXTRA_LENS, 0);
    }

    private String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(distanceInM);
    }
}
