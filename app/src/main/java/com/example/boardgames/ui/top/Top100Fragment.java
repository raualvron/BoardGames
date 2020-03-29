package com.example.boardgames.ui.top;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.boardgames.Classes.ConstantsHelper;
import com.example.boardgames.Classes.DateFormatHelper;
import com.example.boardgames.Model.Top100;
import com.example.boardgames.Parsers.BoardGamesGeekParser;
import com.example.boardgames.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Top100Fragment extends Fragment {
    List<Top100> threads;
    View settingView;

    String subject, id, url;

    LinearLayout linearLayoutParent;
    ListView listView;
    SimpleAdapter listAdapter;
    ArrayList<HashMap<String,String>> threadList;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.toggleMenu(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_close:
                WebView web = settingView.findViewById(R.id.web_frame);
                web.setVisibility(View.GONE);
                this.toggleMenu(false);
                break;
            case R.id.action_share:
                this.createShareIntent(this.getThreadUrl(), this.getThreadSubject());
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        settingView = inflater.inflate(R.layout.fragment_top100, container, false);

        LoadXmlTask task = new LoadXmlTask();
        task.execute();

        return settingView;
    }

    private class LoadXmlTask extends AsyncTask<String,Integer,Boolean> {

        protected Boolean doInBackground(String... params) {
            BoardGamesGeekParser parser = new BoardGamesGeekParser(ConstantsHelper.TOP100_XML_URL, null);
            threads = parser.parseTop100();
            threadList = new ArrayList<>();

            for (int i = 0; i < threads.size(); i++) {
                addingGameFieldsToList(i);
            }
            return true;
        }

        protected void onPostExecute(Boolean result) {
            linearLayoutParent = settingView.findViewById(R.id.top100_list);
            listView = getActivity().findViewById(R.id.top100_parent);


            String[] arrayFields = {"id", "subject", "postdate", "author"};
            int[] arrayViewIds ={R.id.thread_id, R.id.subject, R.id.postdate, R.id.author};

            listAdapter = new SimpleAdapter(getActivity(), threadList, R.layout.top100_list, arrayFields , arrayViewIds);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {
                    TextView idView =  arg1.findViewById(R.id.thread_id);
                    TextView subjectView =  arg1.findViewById(R.id.subject);
                    String url = "https://boardgamegeek.com/thread/" + idView.getText().toString();

                    Top100Fragment.this.setThreadIdClicked(idView.getText().toString());
                    Top100Fragment.this.setThreadSubjectClicked(subjectView.getText().toString());
                    Top100Fragment.this.setThreadUrlClicked(url);

                    WebView web = settingView.findViewById(R.id.web_frame);

                    web.setVisibility(arg1.VISIBLE);
                    Top100Fragment.this.toggleMenu(true);

                    web.getSettings().setJavaScriptEnabled(true);
                    web.getSettings().setLoadWithOverviewMode(true);
                    web.getSettings().setUseWideViewPort(true);
                    web.getSettings().setBuiltInZoomControls(true);
                    web.setWebViewClient(new WebViewClient());

                    web.loadUrl("https://boardgamegeek.com/thread/" + Top100Fragment.this.getThreadId());

                }
            });

        }
    }

    private void addingGameFieldsToList(int pos) {
        String dateParsed = this.parseDate(threads.get(pos).getPostdate());

        HashMap<String, String> threadsRow = new HashMap<>();
        threadsRow.put("subject", threads.get(pos).getSubject());
        threadsRow.put("id", threads.get(pos).getId());
        threadsRow.put("postdate",  dateParsed);
        threadsRow.put("author", threads.get(pos).getAuthor());
        threadList.add(threadsRow);
    }

    private String parseDate(String date) {
        DateFormatHelper dateFormatHelper = new DateFormatHelper(date, ConstantsHelper.DATE_FORMAT);
        return dateFormatHelper.getDateFormatted();
    }

    private void createShareIntent(String body, String subject) {
        Intent intentShared = new Intent(Intent.ACTION_SEND);
        intentShared.setType("text/plain");
        intentShared.putExtra(Intent.EXTRA_TEXT, "Hey!, have a look at the following thread on BoardGamesGeek: " + body);
        intentShared.putExtra(Intent.EXTRA_SUBJECT, subject);
        startActivity(Intent.createChooser(intentShared, "Share using"));
    }

    private void setThreadIdClicked(String id) {
        this.id = id;
    }

    private void setThreadSubjectClicked(String subject) {
        this.subject = subject;
    }

    private void setThreadUrlClicked(String url) {
        this.url = url;
    }


    private String getThreadId() {
        return this.id;
    }

    private String getThreadUrl() {
        return this.url;
    }

    private String getThreadSubject() {
        return this.subject;
    }

    private void toggleMenu(Boolean action) {
        if (action) {
            setHasOptionsMenu(true);
        } else {
            setHasOptionsMenu(false);
        }
    }
}

