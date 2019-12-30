package com.example.abeiiii.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    ListView listView;
    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news2);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        textView=(TextView) findViewById(R.id.tv1);
        listView=(ListView)findViewById((R.id.lv1));
        listView.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        Intent i = getIntent();
        String title = i.getStringExtra("news");
        String newsid = i.getStringExtra("newsid");
        Log.d("tii", "got" + title);
        if (title != null) {
            Log.d("222", "news");
            getSupportActionBar().setTitle(title);
            StringBuilder s1=new StringBuilder("https://newsapi.org/v2/top-headlines?sources=");
            s1.append(newsid);
            s1.append("&apiKey=e9c5ee4f17a84d2dabfbf036cd81d6fa");
            Log.d("ccn","url"+s1);
            new GetnewsAsync().execute(String.valueOf(s1));

        }

    }

    private class GetnewsAsync extends AsyncTask<String, Void, ArrayList<Article>> {
        @Override
        protected ArrayList<Article> doInBackground(String... params) {
            HttpURLConnection connection = null;
            ArrayList<Article> result = new ArrayList<>();
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF8");

                    JSONObject root = new JSONObject(json);
                    JSONArray articles = root.getJSONArray("articles");

                    for (int i = 0; i < articles.length(); i++) {
                        JSONObject articleJson = articles.getJSONObject(i);
                        Article person1 = new Article();
                        person1.url2image = articleJson.getString("urlToImage");
                        person1.title = articleJson.getString("title");
                        person1.author = articleJson.getString("author");
                        person1.date = articleJson.getString("publishedAt");
                        person1.url = articleJson.getString("url");
                        Log.d("abi1", "insideasync");
                        result.add(person1);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(final ArrayList<Article> result) {
            if (result.size() > 0) {
                progressBar.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);
                Log.d("demo", result.toString());
                NewsAdapter adapter = new NewsAdapter(NewsActivity.this, R.layout.activity_news, result);
                for (int i=0; i<1000; i++){
                    for (int j=0; j<1000000; j++);{}
                }
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i=new Intent(NewsActivity.this,WebActivity.class);
                        Article article=result.get(position);
                        String su= article.getUrl();
                        String title=article.getTitle();
                        i.putExtra("url",su);
                        i.putExtra("title",title);
                        startActivity(i);
                    }
                });
            }
        }
    }
}

