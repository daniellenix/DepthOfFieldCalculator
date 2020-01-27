package com.example.cmpt276a2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cmpt276a2.model.Lens;
import com.example.cmpt276a2.model.LensManager;

/**
 * Second activity extracts data and allows for saving lenses and leaving activity.
 */
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

        if(lensIdx != -1) {
            populateEditText();
        }

        setupCancelActivityButton();
        setupSaveActivityButton(lensIdx);
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

//    Adapted from: https://www.geeksforgeeks.org/android-how-to-add-radio-buttons-in-an-android-application/
    private void setupSaveActivityButton(final int lensIdx) {
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb1 = findViewById(R.id.radioButton2);
                RadioButton rb2 = findViewById(R.id.radioButton3);
                RadioButton rb3 = findViewById(R.id.radioButton4);
                RadioButton rb4 = findViewById(R.id.radioButton5);
                RadioButton rb5 = findViewById(R.id.radioButton6);

                if(rb1.isChecked()) {
                    ImageView imageView = findViewById(R.id.imageIcon2);
                    imageView.setImageResource(R.drawable.camera1);
                } else if(rb2.isChecked()) {
                    ImageView imageView = findViewById(R.id.imageIcon2);
                    imageView.setImageResource(R.drawable.camera2);
                }else if(rb3.isChecked()) {
                    ImageView imageView = findViewById(R.id.imageIcon2);
                    imageView.setImageResource(R.drawable.camera3);
                }else if(rb4.isChecked()) {
                    ImageView imageView = findViewById(R.id.imageIcon2);
                    imageView.setImageResource(R.drawable.camera4);
                }else if(rb5.isChecked()) {
                    ImageView imageView = findViewById(R.id.imageIcon2);
                    imageView.setImageResource(R.drawable.camera5);
                }
            }
        });

        Button btn = findViewById(R.id.buttonSave);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int selected = radioGroup.getCheckedRadioButtonId();

                if(lensIdx == -1) {

                    EditText makeText = findViewById(R.id.makeInput);
                    String userMakeInput = makeText.getText().toString().trim();

                    EditText focalLengthText = findViewById(R.id.focalLengthInput);
                    String userFocalLengthInput = focalLengthText.getText().toString().trim();

                    EditText apertureText = findViewById(R.id.apertureInput);
                    String userApertureInput = apertureText.getText().toString().trim();

                    if(userMakeInput.length() == 0) {
                        makeText.setError("Must have input");
                    } else if(userFocalLengthInput.length() == 0) {
                        focalLengthText.setError("Must have input");
                    } else if (userApertureInput.length() == 0) {
                        apertureText.setError("Must have input");
                    } else if(selected == -1){
                        Toast.makeText(SecondActivity.this, "Please enter input", Toast.LENGTH_LONG).show();
                    }else {
                        int userFocalLengthInt = Integer.parseInt(userFocalLengthInput);
                        double userApertureDouble = Double.parseDouble(userApertureInput);

                        if(userFocalLengthInt <= 0) {
                            focalLengthText.setError("Must be larger than 0");
                        } else if (userApertureDouble < 1.4) {
                            apertureText.setError("Must be larger than or equal to 1.4");
                        } else {
                            if(selected == 2131230907) {
                                manager.add(new Lens(userMakeInput, userApertureDouble, userFocalLengthInt, R.drawable.camera1));
                            } else if(selected == 2131230908) {
                                manager.add(new Lens(userMakeInput, userApertureDouble, userFocalLengthInt, R.drawable.camera2));
                            } else if(selected == 2131230909) {
                                manager.add(new Lens(userMakeInput, userApertureDouble, userFocalLengthInt, R.drawable.camera3));
                            } else if(selected == 2131230910) {
                                manager.add(new Lens(userMakeInput, userApertureDouble, userFocalLengthInt, R.drawable.camera4));
                            } else if(selected == 2131230911) {
                                manager.add(new Lens(userMakeInput, userApertureDouble, userFocalLengthInt, R.drawable.camera5));
                            }
                            Intent returnIntent = getIntent();
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        }
                    }
                } else {
                    EditText makeText = findViewById(R.id.makeInput);
                    String userMakeInput = makeText.getText().toString().trim();

                    EditText focalLengthText = findViewById(R.id.focalLengthInput);
                    String userFocalLengthInput = focalLengthText.getText().toString().trim();

                    EditText apertureText = findViewById(R.id.apertureInput);
                    String userApertureInput = apertureText.getText().toString().trim();

                    if(userMakeInput.length() == 0) {
                        makeText.setError("Must have input");
                    } else if(userFocalLengthInput.length() == 0) {
                        focalLengthText.setError("Must have input");
                    } else if (userApertureInput.length() == 0) {
                        apertureText.setError("Must have input");
                    } else if(selected == -1){
                        Toast.makeText(SecondActivity.this, "Please enter input", Toast.LENGTH_LONG).show();
                    }else {
                        int userFocalLengthInt = Integer.parseInt(userFocalLengthInput);
                        double userApertureDouble = Double.parseDouble(userApertureInput);

                        if(userFocalLengthInt <= 0) {
                            focalLengthText.setError("Must be larger than 0");
                        } else if(userApertureDouble < 1.4) {
                            apertureText.setError("Must be larger than or equal to 1.4");
                        } else {
                            if(selected == 2131230907) {
                                manager.getLenses().get(lensIdx).setMake(userMakeInput);
                                manager.getLenses().get(lensIdx).setFocalLength(userFocalLengthInt);
                                manager.getLenses().get(lensIdx).setMaxAperture(userApertureDouble);
                                manager.getLenses().get(lensIdx).setIconID(R.drawable.camera1);
                            } else if(selected == 2131230908) {
                                manager.getLenses().get(lensIdx).setMake(userMakeInput);
                                manager.getLenses().get(lensIdx).setFocalLength(userFocalLengthInt);
                                manager.getLenses().get(lensIdx).setMaxAperture(userApertureDouble);
                                manager.getLenses().get(lensIdx).setIconID(R.drawable.camera2);
                            } else if(selected == 2131230909) {
                                manager.getLenses().get(lensIdx).setMake(userMakeInput);
                                manager.getLenses().get(lensIdx).setFocalLength(userFocalLengthInt);
                                manager.getLenses().get(lensIdx).setMaxAperture(userApertureDouble);
                                manager.getLenses().get(lensIdx).setIconID(R.drawable.camera3);
                            } else if(selected == 2131230910) {
                                manager.getLenses().get(lensIdx).setMake(userMakeInput);
                                manager.getLenses().get(lensIdx).setFocalLength(userFocalLengthInt);
                                manager.getLenses().get(lensIdx).setMaxAperture(userApertureDouble);
                                manager.getLenses().get(lensIdx).setIconID(R.drawable.camera4);
                            } else if(selected == 2131230911) {
                                manager.getLenses().get(lensIdx).setMake(userMakeInput);
                                manager.getLenses().get(lensIdx).setFocalLength(userFocalLengthInt);
                                manager.getLenses().get(lensIdx).setMaxAperture(userApertureDouble);
                                manager.getLenses().get(lensIdx).setIconID(R.drawable.camera5);
                            }

                            Intent returnIntent = getIntent();
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        }
                    }
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void populateEditText() {
        EditText makeText = findViewById(R.id.makeInput);
        makeText.setText("" + manager.getLenses().get(lensIdx).getMake());

        EditText focalLengthText = findViewById(R.id.focalLengthInput);
        focalLengthText.setText("" + manager.getLenses().get(lensIdx).getFocalLength());

        EditText apertureText = findViewById(R.id.apertureInput);
        apertureText.setText("" + manager.getLenses().get(lensIdx).getMaxAperture());
    }
}
