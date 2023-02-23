package com.example.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextName, editTextEmail, editTextPassword;
    RadioGroup radioGroupGender;
    CheckBox checkBoxFormeEtSante, checkBoxLoisirs, checkBoxShopping;
    Button buttonValider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = (EditText) findViewById(R.id.EditTextName);
        editTextEmail = (EditText) findViewById(R.id.EditTextEmail);
        editTextPassword = (EditText) findViewById(R.id.EditTextPassword);

        radioGroupGender = (RadioGroup) findViewById(R.id.RadioGroupGender);

        checkBoxFormeEtSante = (CheckBox) findViewById(R.id.CheckBoxFormetSante);
        checkBoxLoisirs = (CheckBox) findViewById(R.id.CheckBoxLoisirs);
        checkBoxShopping = (CheckBox) findViewById(R.id.CheckBoxShopping);

        buttonValider = (Button) findViewById(R.id.ButtonValider);

        buttonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextName = (EditText) findViewById(R.id.EditTextName);
                editTextPassword = (EditText) findViewById(R.id.EditTextPassword);

                int count = 0;
                StringBuilder result = new StringBuilder();
                if (checkBoxFormeEtSante.isChecked()) {
                    result.append("\nForme et sante");
                    count += 1;
                }
                if (checkBoxLoisirs.isChecked()) {
                    result.append("\nLoisirs");
                    count += 1;
                }
                if (checkBoxShopping.isChecked()) {
                    result.append("\nShopping");
                    count += 1;
                }

                if ((!editTextEmail.getText().toString().isEmpty() && !editTextPassword.getText().toString().isEmpty() && !editTextName.getText().toString().isEmpty()) && (radioGroupGender.getCheckedRadioButtonId() != -1) && (count >= 2)) {
                    Intent intent = new Intent();
                    intent.putExtra("email", editTextEmail.getText().toString());
                    intent.putExtra("name", editTextName.getText().toString());
                    intent.putExtra("password", editTextPassword.getText().toString());
                    intent.putExtra("interests", result.toString());

                    if (radioGroupGender.getCheckedRadioButtonId() == R.id.RadioButtonMale) {
                        intent.putExtra("gender", "Mr.");
                    } else {
                        intent.putExtra("gender", "Mme.");
                    }

                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    if (editTextEmail.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty() || editTextName.getText().toString().isEmpty()) {
                        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                        alertDialog.setTitle("Error");
                        alertDialog.setMessage("Please fill all the fields");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                (dialog, which) -> dialog.dismiss());
                        alertDialog.show();
                        alertDialog.setCancelable(true);
                    } else if (radioGroupGender.getCheckedRadioButtonId() == -1) {
                        AlertDialog alertDialogGender = new AlertDialog.Builder(RegisterActivity.this).create();
                        alertDialogGender.setTitle("Choix de Genre Manquant");
                        alertDialogGender.setMessage("Vous devez choisir un genre");
                        alertDialogGender.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                (dialog, which) -> dialog.dismiss());
                        alertDialogGender.show();
                        alertDialogGender.setCancelable(true);
                    } else if (count < 2) {
                        Toast.makeText(RegisterActivity.this, "Please select at least 2 interests", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}