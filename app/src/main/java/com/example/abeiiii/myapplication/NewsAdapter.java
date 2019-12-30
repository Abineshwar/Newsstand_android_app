package com.example.abeiiii.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<Article> {
    public NewsAdapter(Context context, int resource, List<Article> objects) {
        super(context, resource, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Article article = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_news, parent, false);
        ImageView iv=(ImageView)convertView.findViewById(R.id.imageView);
        TextView textViewSubject = (TextView) convertView.findViewById(R.id.textView);
        TextView textViewSummary = (TextView) convertView.findViewById(R.id.textView2);
        TextView textViewEmail = (TextView) convertView.findViewById(R.id.textView3);
        if (!article.getUrl2image().isEmpty()) {
            Picasso.get().load(article.getUrl2image()).into(iv);
        }
        textViewSubject.setText(article.getTitle());
        textViewSummary.setText(article.getAuthor());
        textViewEmail.setText(article.getDate());
        return convertView;
    }
  }

