package com.shonen.ukr.watsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SingUp extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUserName;
    private EditText edtUserEmail;
    private EditText edtUserPassword;

    private Button btnSinUp;
    private Button btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sing_up);
        setTitle("WatsApp Clone");

        edtUserName = findViewById(R.id.singUpUserName);
        edtUserEmail = findViewById(R.id.singUpUserEMail);
        edtUserPassword = findViewById(R.id.singUpUserPassword);

        btnSinUp = findViewById(R.id.btnSingUpSU);
        btnLogIn = findViewById(R.id.btnLogINSU);
        /*
       When user finish enter their password and taped on soft keyboard to enter key, hi start key event to activate signup button
         */
        edtUserPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btnSinUp);
                }
                return false;
            }
        });
        btnSinUp.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            transitionToWatsAppUsers();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSingUpSU:
                if (edtUserEmail.getText().toString().equals("") && edtUserName.getText().toString().equals("") && edtUserPassword.getText().toString().equals("")) {
                    FancyToast.makeText(SingUp.this, "E-mail, Username, Password is required ", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                } else {
                    try {
                        final ParseUser addNewUser = new ParseUser();
                        addNewUser.setUsername(edtUserName.getText().toString());
                        addNewUser.setEmail(edtUserEmail.getText().toString());
                        addNewUser.setPassword(edtUserPassword.getText().toString());
                        addNewUser.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    FancyToast.makeText(SingUp.this, "Done", Toast.LENGTH_SHORT, FancyToast.INFO, false).show();
                                    transitionToWatsAppUsers();
                                } else {
                                    FancyToast.makeText(SingUp.this, e.getMessage(), Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                                }
                            }
                        });
                    } catch (IllegalArgumentException e) {
                        FancyToast.makeText(SingUp.this, e.getMessage(), Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                }
                break;
            case R.id.btnLogINSU:
                Intent intent = new Intent(SingUp.this, LogIn.class);
                startActivity(intent);
                finish();
                break;

        }
    }

    public void rootLayoutTaped(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void transitionToWatsAppUsers() {
        Intent intent = new Intent(SingUp.this, WatsAppCloneUsers.class);
        startActivity(intent);
    }
}

