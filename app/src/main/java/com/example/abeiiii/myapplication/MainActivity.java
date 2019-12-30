

package com.example.abeiiii.myapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.DataInput;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ProgressBar progressBar;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main Activity");
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        textView=(TextView) findViewById(R.id.tv);
        listView=(ListView)findViewById((R.id.lv));
        if (isConnected()) {
            listView.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Internet Enabled", Toast.LENGTH_SHORT).show();
            new GetDataAsync().execute("https://newsapi.org/v2/sources?apiKey=e9c5ee4f17a84d2dabfbf036cd81d6fa");
        } else
            Toast.makeText(this, "Internet notEnabled", Toast.LENGTH_SHORT).show();


    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    private class GetDataAsync extends AsyncTask<String, Void, ArrayList<Source>> {

         @Override
        protected ArrayList<Source> doInBackground(String... params) {
            HttpURLConnection connection = null;
           ArrayList<Source> result = new ArrayList<>();
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF8");

                    JSONObject root = new JSONObject(json);
                    JSONArray articles = root.getJSONArray("sources");

                    for (int i = 0; i < articles.length(); i++) {
                        JSONObject articleJson = articles.getJSONObject(i);
                        Source person= new Source();
                        person.id = articleJson.getString("id");
                        person.name = articleJson.getString("name");
                        result.add(person);

                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }

        protected void onPostExecute(final ArrayList<Source> result) {

            if (result.size() > 0) {
                progressBar.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);
                ArrayAdapter<Source> adapter=new ArrayAdapter<Source>(MainActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,result);
                for (int i=0; i<1000; i++){
                    for (int j=0; j<1000000; j++);{}
                }
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Source person= result.get(position);
                        String name=person.getName();
                        String nid=person.getId();
                        Intent in = new Intent(MainActivity.this, NewsActivity.class);
                        in.putExtra("news",name);
                        in.putExtra("newsid",nid);
                        startActivity(in);
                    }
                });

            }
        }
    }
}
