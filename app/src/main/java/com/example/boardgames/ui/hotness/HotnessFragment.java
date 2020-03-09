package com.example.boardgames.ui.hotness;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.boardgames.Classes.ConstantsHelper;
import com.example.boardgames.Classes.ImageTask;
import com.example.boardgames.Model.Hotness;
import com.example.boardgames.Parsers.BoardGamesGeekParser;
import com.example.boardgames.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HotnessFragment extends Fragment {
    List<Hotness> games;
    View settingView;
    Integer fields = 4;

    LinearLayout linearLayoutParent;
    ArrayList<HashMap<String, String>> gamesList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        settingView = inflater.inflate(R.layout.fragment_hotness, container, false);

        LoadXmlTask task = new LoadXmlTask();
        task.execute();

        return settingView;
    }

    private class LoadXmlTask extends AsyncTask<String,Integer,Boolean> {

        protected Boolean doInBackground(String... params) {
            BoardGamesGeekParser parser = new BoardGamesGeekParser(ConstantsHelper.HOTNESS_XML_URL, null);
            games = parser.parseHotnessGames();
            return true;
        }

        protected void onPostExecute(Boolean result) {
            linearLayoutParent = settingView.findViewById(R.id.hotness_layout);

            for(int i = 0; i < games.size(); i++ ) {

                String rank = games.get(i).getRank();
                String name = games.get(i).getNameGame();
                String year = games.get(i).getYear();
                String image = games.get(i).getImage();

                HotnessFragment.this.createLinearLayout(linearLayoutParent, rank, name, year, image);
            }
        }
    }

    private void createLinearLayout(LinearLayout linearLayoutParent, String... params) {
        for(int i = 0; i < fields; i++ ) {
            if (i == 3) {
                ImageView imageView = new ImageView(getActivity());
                new ImageTask(params[i], imageView).execute();
                linearLayoutParent.addView(imageView);
            } else {
                TextView textView = new TextView(getActivity());
                textView.setText(params[i]);
                linearLayoutParent.addView(textView);
            }
        }
    }
}
