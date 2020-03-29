package com.example.boardgames;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.boardgames.Classes.ConstantsHelper;
import com.example.boardgames.Classes.HttpService;
import com.example.boardgames.Classes.IntentService;
import com.example.boardgames.Classes.ToastService;
import com.example.boardgames.Classes.ValidatorHelper;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameInput, passwordInput, emailInput,
            emailInputError, usernameInputError, passwordInputError;
    Button registerButton;
    ValidatorHelper validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove title bar at the top of the activity
        this.hideTitleBar();

        setContentView(R.layout.activity_register);

        usernameInput = findViewById(R.id.username);
        usernameInputError = findViewById(R.id.username_error);

        emailInput = findViewById(R.id.email);
        emailInputError = findViewById(R.id.email_error);

        passwordInput = findViewById(R.id.password);
        passwordInputError = findViewById(R.id.password_error);

        registerButton = findViewById(R.id.register);
        validator = new ValidatorHelper();

        usernameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                usernameInputError.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                usernameInputError.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (validator.isNullOrEmpty(s.toString())) {
                    validator.showError(usernameInputError,"string", "username", null);
                }

                if(validator.isRangeInvalid(s.toString(), 4, 20)) {
                    HashMap<String,String> params = new HashMap<String,String>();
                    params.put("max", "4");
                    params.put("min", "20");
                    validator.showError(usernameInputError,"range", "username", params);
                }
            }
        });

        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                passwordInputError.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordInputError.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (validator.isNullOrEmpty(s.toString())) {
                    validator.showError(passwordInputError,"string", "password", null);
                }

                if(validator.isRangeInvalid(s.toString(), 4, 20)) {
                    HashMap<String,String> params = new HashMap<String,String>();
                    params.put("max", "4");
                    params.put("min", "20");
                    validator.showError(passwordInputError,"range", "password", params);
                }
            }
        });

        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                emailInputError.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailInputError.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (validator.isNullOrEmpty(s.toString())) {
                    validator.showError(emailInputError,"string", "password", null);
                }

                if (validator.isEmailInvalid(s.toString())) {
                    validator.showError(emailInputError,"string", "email", null);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = usernameInput.getText().toString();
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (!validator.isFormValid(username, email, password)) {
                    new RegisterOnBoardGameGeek().execute(username, email, password);
                } else {
                    ToastService toast = new ToastService("There are some fields to be validated", RegisterActivity.this);
                    toast.runToast();
                }
            }
        });
    }


    public class RegisterOnBoardGameGeek extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            registerButton.setEnabled(false);
        }

        protected String doInBackground(String... params) {
            String username = params[0];
            String email = params[1];
            String password = params[2];
            String payload = "{\"username\": \"" +username+"\", \"email\": \"" +email+"\" ,\"password\": \""+password+"\"}";

            HttpService httpService = new HttpService();

            if (httpService.postMethod(ConstantsHelper.REQUEST_URL_REGISTER, payload)) {
                RegisterActivity.this.goToLogin();
                ToastService toast = new ToastService("Account created successfully", RegisterActivity.this);
                toast.runToast();
            } else {
                ToastService toast = new ToastService("Ups, something happened, try again", RegisterActivity.this);
                toast.runToast();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            registerButton.setEnabled(true);
        }
    }


    private void goToLogin() {
        IntentService intent = new IntentService(RegisterActivity.this,
                LoginActivity.class);

        intent.startActivity();
        intent.finishActivity();
    }

    // Remove title bar at the top of the activity
    public void hideTitleBar() {
        getSupportActionBar().hide();
    }
}

