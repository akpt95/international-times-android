package com.akash.internationaltimes.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.akash.internationaltimes.R;
import com.akash.internationaltimes.interfaces.RecyclerViewItemClickListner;
import com.akash.internationaltimes.network.model.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HeadlinesAdapter extends RecyclerView.Adapter<HeadlinesAdapter.ViewHolder> {

    private static final String TAG = HeadlinesAdapter.class.getSimpleName();

    private RecyclerViewItemClickListner recyclerViewItemClickListner;

    private List<Article> articles;

    HeadlinesAdapter(List<Article> articles, RecyclerViewItemClickListner recyclerViewItemClickListner) {
        this.articles = articles;
        this.recyclerViewItemClickListner = recyclerViewItemClickListner;
    }

    @NonNull
    @Override
    public HeadlinesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_headlines, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadlinesAdapter.ViewHolder holder, int position) {

        Picasso.get().load(articles.get(position).getUrlToImage()).into(holder.thumbnail);
        Picasso.get().load(articles.get(position).getUrlToImage()).fit().into(holder.expandedThubnail);
        holder.headline.setText(articles.get(position).getTitle());
        holder.expandedTitle.setText(articles.get(position).getTitle());
        holder.expandedDescription.setText(articles.get(position).getDescription());

    }

    @Override
    public int getItemCount() {

        if (articles == null)
            return 0;
        else
            return articles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView cardCollapsed;
        private CardView cardExpanded;
        private ImageView thumbnail;
        private ImageView expandedThubnail;
        private TextView headline;
        private TextView expandedTitle;
        private TextView expandedDescription;
        private TextView textReadMore;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardCollapsed = itemView.findViewById(R.id.card_headline_collapsed);
            cardExpanded = itemView.findViewById(R.id.card_headline_expanded);
            thumbnail = itemView.findViewById(R.id.image_headline_thumbnail);
            expandedThubnail = itemView.findViewById(R.id.image_headline_expanded_thumbnail);
            headline = itemView.findViewById(R.id.text_headline);
            expandedTitle = itemView.findViewById(R.id.text_news_details_title);
            expandedDescription = itemView.findViewById(R.id.text_news_details_description);
            textReadMore = itemView.findViewById(R.id.text_news_details_read_more);

//            textReadMore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    recyclerViewItemClickListner.onItemClick(v, getAdapterPosition());
//                }
//            });

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (cardExpanded.getVisibility() == View.GONE){
                cardCollapsed.setVisibility(View.GONE);
                cardExpanded.setVisibility(View.VISIBLE);
            } else {
                recyclerViewItemClickListner.onItemClick(v, getAdapterPosition());
            }

        }
    }
}
