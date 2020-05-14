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

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LogIn extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUserLogInMail;
    private EditText edtUserLogInPassword;

    private Button btnLogIn;
    private Button btnSingUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setTitle("Log In");

        edtUserLogInMail = findViewById(R.id.logInUserMail);
        edtUserLogInPassword = findViewById(R.id.logInUserPassword);

        btnLogIn = findViewById(R.id.btnLogINLI);
        btnSingUp = findViewById(R.id.btnSingUpLI);
        edtUserLogInPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btnLogIn);
                }
                return false;
            }
        });

        btnLogIn.setOnClickListener(this);
        btnSingUp.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
           // transitionToTwitterUsers();
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogINLI:
                if (edtUserLogInMail.getText().toString().equals("") && edtUserLogInPassword.getText().toString().equals("")) {
                    FancyToast.makeText(LogIn.this, "Please enter the your e-mail and password ", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }else {
                    try {
                        ParseUser.logInInBackground(edtUserLogInMail.getText().toString(), edtUserLogInPassword.getText().toString(), new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (user != null && e == null) {
//                                    transitionToTwitterUsers();
                                } else {
                                    FancyToast.makeText(LogIn.this, e.getMessage(), Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                                }
                            }
                        });
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btnSingUpLI:
                Intent intent= new Intent(LogIn.this,SingUp.class);
                startActivity(intent);
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

//    private void transitionToTwitterUsers() {
//        Intent intent = new Intent(LogIn.this, TwitterUsers.class);
//        startActivity(intent);
//    }
}
