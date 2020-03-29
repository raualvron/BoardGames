package com.example.boardgames.ui.settings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.boardgames.Classes.ConstantsHelper;
import com.example.boardgames.Classes.IntentService;
import com.example.boardgames.Classes.LocationService;
import com.example.boardgames.Classes.SharedPrefService;
import com.example.boardgames.LoginActivity;
import com.example.boardgames.Model.User;
import com.example.boardgames.Parsers.BoardGamesGeekParser;
import com.example.boardgames.R;

import java.util.List;

public class SettingsFragment extends Fragment {

    List<User> users;
    View settingView;
    TextView firstNameText, lastNameText, countryText, stateText, yearText, lastLoginText, locationText;
    Button logoutButton;
    SharedPrefService sharedPrefService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        settingView = inflater.inflate(R.layout.fragment_settings, container, false);

        firstNameText = settingView.findViewById(R.id.firstname_user);
        lastNameText = settingView.findViewById(R.id.lastname_user);
        countryText = settingView.findViewById(R.id.country_user);
        stateText = settingView.findViewById(R.id.state_user);
        yearText = settingView.findViewById(R.id.year_user);
        lastLoginText = settingView.findViewById(R.id.lastlogin_user);
        locationText = settingView.findViewById(R.id.location_user);

        logoutButton = settingView.findViewById(R.id.logout);

        sharedPrefService = new SharedPrefService(getContext());
        String username = this.getUsernameSharedPrefs();

        LoadXmlTask task = new LoadXmlTask();
        task.execute(username);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SettingsFragment.this.clearSharedPrefs();
                SettingsFragment.this.doLogout();
            }
        });

        this.setLocationOnView();

        return settingView;
    }

    private class LoadXmlTask extends AsyncTask<String,Integer,Boolean> {

        protected Boolean doInBackground(String... params) {
            BoardGamesGeekParser parser = new BoardGamesGeekParser(ConstantsHelper.USER_XML_URL, params[0]);
            users = parser.parseUsers();
            return true;
        }

        protected void onPostExecute(Boolean result) {
            User user = users.get(0);

            firstNameText.setText(user.getFirstname());
            lastNameText.setText(user.getLastname());
            countryText.setText(user.getCountry());
            stateText.setText(user.getState());
            lastLoginText.setText(user.getLastLogin());
            yearText.setText(user.getYearRegistered());

        }
    }

    private void doLogout() {
        IntentService intent = new IntentService(getActivity(),
                LoginActivity.class);

        intent.startActivity();
        intent.finishActivity();
    }

    private String getUsernameSharedPrefs() {
        return sharedPrefService.getSharedPrefString("username");
    }

    private void setLocationOnView() {
        LocationService locationService = new LocationService(getActivity());
        double latitude = locationService.getLatitudeLocation();
        double longitude = locationService.getLongitudeLocation();

        locationText.setText(Double.toString(latitude) + ' ' + Double.toString(longitude));
    }

    private void clearSharedPrefs () {
        sharedPrefService.clearAllSharedPref();
    }
}
