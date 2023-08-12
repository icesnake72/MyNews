package com.example.mynews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

public class NewsAdaptor extends RecyclerView.Adapter<NewsAdaptor.ViewHolder> {

    private Articles[] articles;

    public NewsAdaptor(Articles[] articles) {
        this.articles = articles;
//        Fresco.initialize(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String uri = articles[position].getUrlToImage();
        holder.draweeView.setImageURI(uri);
        holder.textNewsTitle.setText(articles[position].getTitle());
        holder.textNewsData.setText(articles[position].getDescription());
        holder.news_url = articles[position].getUrl();
    }

    @Override
    public int getItemCount() {
        if (articles==null)
            return 0;

        return articles.length;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        private String news_url;
        private SimpleDraweeView draweeView;
        private TextView textNewsTitle;
        private TextView textNewsData;

        public ViewHolder(@NonNull View view) {
            super(view);

            draweeView = view.findViewById(R.id.news_image_view);
            textNewsTitle = view.findViewById(R.id.news_title);
            textNewsData = view.findViewById(R.id.news_data);

            draweeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(view.getContext(), WebViewActivity.class);
                    intent.putExtra("NEWS_URL", news_url);
                    view.getContext().startActivity(intent);

//                    Toast toast = Toast.makeText(view.getContext(), news_url, Toast.LENGTH_SHORT);
//                    toast.show();
                }
            });
        }

        public SimpleDraweeView getDraweeView() {
            return draweeView;
        }

        public TextView getTextNewsTitle() {
            return textNewsTitle;
        }

        public TextView getTextNewsData() {
            return textNewsData;
        }
    }
}
