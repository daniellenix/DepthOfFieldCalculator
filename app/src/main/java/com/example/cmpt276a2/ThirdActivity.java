package com.example.cmpt276a2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

        setPhotoDetails();
        setCalculateActivityButton();
        extractDataFromIntent();
    }

    private void setPhotoDetails() {

        TextView textView = findViewById(R.id.textViewInfo);
        textView.append(manager.getLenses().get(lensIdx).toString());
    }

    private void setCalculateActivityButton() {

        final int focalLength = manager.getLenses().get(lensIdx).getFocalLength();
        final double maxAperture = manager.getLenses().get(lensIdx).getMaxAperture();

        Button btn = findViewById(R.id.buttonCalculate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = findViewById(R.id.distanceInput);
                double userDistanceInput = Double.parseDouble(editText.getText().toString());

                editText = findViewById(R.id.apertureInput);
                double userApertureInput = Double.parseDouble(editText.getText().toString());

                TextView textView;

                // Error handling
                if(userApertureInput < maxAperture) {

                    textView = findViewById(R.id.textViewHyperfocalDistance);
                    textView.append(" " + "Invalid Aperture");

                    textView = findViewById(R.id.textViewNearFocalDistance);
                    textView.append(" " + "Invalid Aperture");

                    textView = findViewById(R.id.textViewFarFocalDistance);
                    textView.append(" " + "Invalid Aperture");

                    textView = findViewById(R.id.textViewDepthOfField);
                    textView.append(" " + "Invalid Aperture");
                }

                // calculations
                double hyperFocalDistance = Double.parseDouble(formatM(depthCalculator.hyperFocalDistance(focalLength, userApertureInput)));
                double nearFocalPoint = Double.parseDouble(formatM(depthCalculator.nearFocalPoint(hyperFocalDistance, userDistanceInput, focalLength)));
                double farFocalPoint = Double.parseDouble(formatM(depthCalculator.farFocalPoint(hyperFocalDistance, userDistanceInput, focalLength)));
                double depthOfField = Double.parseDouble(formatM(depthCalculator.depthOfField(farFocalPoint, nearFocalPoint)));

                textView = findViewById(R.id.textViewHyperfocalDistance);
                textView.append(" : " + hyperFocalDistance);

                textView = findViewById(R.id.textViewNearFocalDistance);
                textView.append(" : " + nearFocalPoint);

                textView = findViewById(R.id.textViewFarFocalDistance);
                textView.append(" : " + farFocalPoint);

                textView = findViewById(R.id.textViewDepthOfField);
                textView.append(" : " + depthOfField);

                Intent returnIntent = getIntent();
                setResult(Activity.RESULT_OK, returnIntent);

            }
        });
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();

        String make = intent.getStringExtra(EXTRA_LENS);
        int focalLength = intent.getIntExtra(EXTRA_LENS, 0);
        double maxAperture = intent.getDoubleExtra(EXTRA_LENS, 0);

    }

    private String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(distanceInM);
    }
}
