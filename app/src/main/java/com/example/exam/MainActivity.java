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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int ACCOUNT_CREATION_ACTIVITY_ID = 10;
    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonForgotPassword;
    Button buttonLogin;
    Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = (EditText) findViewById(R.id.EditTextEmail);
        editTextPassword = (EditText) findViewById(R.id.EditTextPassword);

        buttonForgotPassword = (Button) findViewById(R.id.ButtonForgotPassword);
        buttonLogin = (Button) findViewById(R.id.ButtonLogin);
        buttonRegister = (Button) findViewById(R.id.ButtonRegister);

        ((Button) findViewById(R.id.ButtonLogin)).setOnClickListener(this);
        ((Button) findViewById(R.id.ButtonRegister)).setOnClickListener(this);
        ((Button) findViewById(R.id.ButtonForgotPassword)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ButtonLogin:
                if (editTextEmail.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Email or password is empty");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            (dialog, which) -> dialog.dismiss());
                    alertDialog.show();
                    alertDialog.setCancelable(true);
                } else {
                    onActivityResult(ACCOUNT_CREATION_ACTIVITY_ID, RESULT_OK, new Intent()
                            .putExtra("email", editTextEmail.getText().toString())
                            .putExtra("password", editTextPassword.getText().toString()));
                }
                break;
            case R.id.ButtonRegister:
                intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivityForResult(intent, ACCOUNT_CREATION_ACTIVITY_ID);
                break;
            case R.id.ButtonForgotPassword:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"abbassimelek@gmail.com", "offrepromotion@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Mot de pass oublié");
                intent.putExtra(Intent.EXTRA_TEXT, "Bonjour j'ai oublié mon mot de passe, prière de me le réinitialiser");
                startActivity(Intent.createChooser(intent, "Send Email"));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACCOUNT_CREATION_ACTIVITY_ID) {
            if (resultCode == RESULT_OK) {
                editTextEmail.setText(data.getStringExtra("email"));
                editTextPassword.setText(data.getStringExtra("password"));

                Toast.makeText(this, "Vous avez choisi\n" + data.getStringExtra("gender") + data.getStringExtra("name") + data.getStringExtra("interests"), Toast.LENGTH_LONG).show();
            }
        }
    }
}