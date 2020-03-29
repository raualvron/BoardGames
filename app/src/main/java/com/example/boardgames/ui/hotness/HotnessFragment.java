package com.example.boardgames.ui.hotness;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.boardgames.Classes.ConstantsHelper;
import com.example.boardgames.Classes.ImageTask;
import com.example.boardgames.Classes.IntentService;
import com.example.boardgames.GameActivity;
import com.example.boardgames.Model.Hotness;
import com.example.boardgames.Parsers.BoardGamesGeekParser;
import com.example.boardgames.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HotnessFragment extends Fragment {
    List<Hotness> gamesHotness;
    View settingView;

    LinearLayout linearLayoutParent;
    ListView listView;
    SimpleAdapter listAdapter;
    ArrayList<HashMap<String,String>> gameList;

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
            gamesHotness = parser.parseHotnessGames();
            gameList = new ArrayList<>();

            for (int i = 0; i < gamesHotness.size(); i++) {
                addingGameFieldsToList(i);
            }
            return true;
        }

        protected void onPostExecute(Boolean result) {
            linearLayoutParent = settingView.findViewById(R.id.hotness_list);
            listView = getActivity().findViewById(R.id.hotness_parent);


            String[] arrayFields = {"id", "rank", "name", "year", "image"};
            int[] arrayViewIds ={R.id.game_id, R.id.rank, R.id.name, R.id.year, R.id.image_game};

            listAdapter = new SimpleAdapter(getActivity(),  gameList, R.layout.hotness_list, arrayFields , arrayViewIds);
            listAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,String textRepresentation)
                {
                    if(view instanceof ImageView)
                    {
                        ImageView iv = (ImageView) view;
                        ImageTask imageLoaderTask = new ImageTask(data.toString(), iv);
                        imageLoaderTask.execute();
                        return true;
                    }
                    return false;
                }
            });
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView arg0, View arg1,
                                        int position, long arg3) {
                    TextView gameId = arg1.findViewById(R.id.game_id);
                    HotnessFragment.this.goToGameActivity(gameId.getText().toString());
                }
            });

        }
    }

    private void goToGameActivity(String id) {

        HashMap<String, String> map = new HashMap<>();
        map.put("gameID", id);

        IntentService intent = new IntentService(getActivity(),
                GameActivity.class);
        intent.putExtra(map);
        intent.startActivity();
    }

    private void addingGameFieldsToList(int pos) {
        HashMap<String, String> gamesRow = new HashMap<>();
        gamesRow.put("rank", gamesHotness.get(pos).getRank());
        gamesRow.put("name", gamesHotness.get(pos).getNameGame());
        gamesRow.put("year", gamesHotness.get(pos).getYear());
        gamesRow.put("image", gamesHotness.get(pos).getImage());
        gamesRow.put("id", gamesHotness.get(pos).getId());
        gameList.add(gamesRow);
    }
}
