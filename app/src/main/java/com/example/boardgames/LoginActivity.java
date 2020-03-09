package com.example.boardgames;

import androidx.appcompat.app.AppCompatActivity;

import com.example.boardgames.Classes.ConstantsHelper;
import com.example.boardgames.Classes.IntentService;
import com.example.boardgames.Classes.SharedPrefService;
import com.example.boardgames.Classes.ToastService;
import com.example.boardgames.Classes.HttpService;
import com.example.boardgames.Classes.ValidatorHelper;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.os.AsyncTask;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {

    EditText usernameInput, passwordInput, usernameError, passwordError;
    ValidatorHelper validator;
    Button loginButton, registerButton;
    SharedPrefService sharedPrefService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPrefService = new SharedPrefService(getBaseContext());

        // Remove title bar at the top of the activity
        this.hideTitleBar();

        //Was the user logged previously?
        this.isTheUserLogged();

        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.username);
        usernameError = findViewById(R.id.usernameerror);
        passwordError = findViewById(R.id.passworderror);
        passwordInput = findViewById(R.id.password);
        loginButton = findViewById(R.id.loggin);
        registerButton = findViewById(R.id.register);

        validator = new ValidatorHelper();

        usernameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                usernameError.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                usernameError.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (validator.isNullOrEmpty(s.toString())) {
                    validator.showError(usernameError,"string", "username", null);
                }

                if(validator.isRangeInvalid(s.toString(), 4, 20)) {
                    HashMap<String,String> params = new HashMap<String,String>();
                    params.put("max", "4");
                    params.put("min", "20");
                    validator.showError(usernameError,"range", "username", params);
                }
            }
        });

        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                passwordError.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordError.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (validator.isNullOrEmpty(s.toString())) {
                    validator.showError(passwordError,"string", "password", null);
                }

                if(validator.isRangeInvalid(s.toString(), 4, 20)) {
                    HashMap<String,String> params = new HashMap<String,String>();
                    params.put("max", "4");
                    params.put("min", "20");
                    validator.showError(passwordError,"range", "password", params);
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (!validator.isFormValid(username, password)) {
                    new LoginOnBoardGameGeek().execute(username, password);
                } else {
                    ToastService toast = new ToastService("There are some fields to be validated", LoginActivity.this);
                    toast.runToast();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LoginActivity.this.goToRegister();
            }
        });

    }

    public class LoginOnBoardGameGeek extends AsyncTask<String, Void, String>{

        protected void onPreExecute() {
            loginButton.setEnabled(false);
        }

        protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            String payload = "{\"credentials\": {\"username\": \"" +username+"\", \"password\": \""+password+"\"}}";


            HttpService httpService = new HttpService();

            if (httpService.postMethod(ConstantsHelper.REQUEST_URL_LOGIN, payload)) {
                LoginActivity.this.goToDashboard(username);

                sharedPrefService.setSharedPrefBoolean(ConstantsHelper.LOGGED, true);
                sharedPrefService.setSharedPrefString(ConstantsHelper.USERNAME, username);

            } else {
                ToastService toast = new ToastService("\"Username or password mismatched\"", LoginActivity.this);
                toast.runToast();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            loginButton.setEnabled(true);
        }
    }

    // Go to the dashboard activity passing parameter
    private void goToDashboard(String username) {

        HashMap<String, String> map = new HashMap<>();
        map.put("username", username);

        IntentService intent = new IntentService(LoginActivity.this,
                TopGamesActivity.class);
        intent.putExtra(map);
        intent.startActivity();
        intent.finishActivity();
    }

    // Go to the register activity passing parameter
    private void goToRegister() {
        IntentService intent = new IntentService(LoginActivity.this,
                RegisterActivity.class);

        intent.startActivity();
    }
    // Remove title bar at the top of the activity
    private void hideTitleBar() {
        getSupportActionBar().hide();
    }

    private void isTheUserLogged() {
        String username = sharedPrefService.getSharedPrefString(ConstantsHelper.USERNAME);
        if (username.length() > 0) {
            this.goToDashboard(username);
        }
    }
}
