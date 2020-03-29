package com.example.boardgames;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.boardgames.Classes.ConstantsHelper;
import com.example.boardgames.Classes.ImageTask;
import com.example.boardgames.Classes.IntentService;
import com.example.boardgames.Model.Game;
import com.example.boardgames.Parsers.BoardGamesGeekParser;

public class GameActivity extends AppCompatActivity {

    String gameId;
    ImageView imageView;
    TextView nameView, descriptionView, yearPublishedView, playersView, categoryView,
        maxPlayTime, minPlayTime, playingTime, minAge;

    Game currentGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.hideTitleBar();

        imageView = findViewById(R.id.image_game);
        nameView = findViewById(R.id.game_name);

        descriptionView = findViewById(R.id.game_description);
        yearPublishedView = findViewById(R.id.game_year_registered);
        playersView = findViewById(R.id.game_players);
        categoryView = findViewById(R.id.game_category);
        // Scrollable
        descriptionView.setMovementMethod(new ScrollingMovementMethod());

        maxPlayTime = findViewById(R.id.playing_max);
        minPlayTime =  findViewById(R.id.playing_min);
        playingTime =  findViewById(R.id.playing_time);
        minAge = findViewById(R.id.game_age);

        IntentService intentService = new IntentService(GameActivity.this, null);
        gameId = intentService.getExtraByKey("gameID");

        GameActivity.LoadXmlTask task = new GameActivity.LoadXmlTask();
        task.execute(gameId);

    }

    private class LoadXmlTask extends AsyncTask<String,Integer,Boolean> {

        protected Boolean doInBackground(String... params) {
            BoardGamesGeekParser parser = new BoardGamesGeekParser(ConstantsHelper.HOTNESS_GAME_URL, params[0]);
            currentGame = parser.parseGame();
            return true;
        }

        protected void onPostExecute(Boolean result) {

            nameView.setText(currentGame.getName());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                descriptionView.setText(Html.fromHtml(currentGame.getDescription(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                descriptionView.setText(Html.fromHtml(currentGame.getDescription()));
            }

            yearPublishedView.setText("Year Published: " + currentGame.getYearPublished());
            playersView.setText(currentGame.getMinPlayer() + "-" + currentGame.getMaxPlayer() + " Players");
            categoryView.setText(currentGame.getCategory());

            playingTime.setText("Playing Time: " + currentGame.getPlayingTime() + " mins");
            minPlayTime.setText("Min Playing Time: " + currentGame.getMinPlayTime() + " mins");
            maxPlayTime.setText("Max Playing Time: " + currentGame.getMaxPlayTime() + " mins");
            minAge.setText("Min Age: " + currentGame.getMinAge() + " years old");

            ImageTask imageLoaderTask = new ImageTask(currentGame.getImage().toString(), imageView);
            imageLoaderTask.execute();
        }
    }

    // Remove title bar at the top of the activity
    private void hideTitleBar() {
        getSupportActionBar().hide();
    }
}
