package com.akash.internationaltimes.details;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.akash.internationaltimes.MainActivity;
import com.akash.internationaltimes.R;
import com.akash.internationaltimes.network.model.Article;
import com.akash.internationaltimes.utils.Constants;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsDetailsFragment extends Fragment {

    private static final String TAG = NewsDetailsFragment.class.getSimpleName();

    private Context context;
    private MainActivity mainActivity;
    private Article article;
    
    private WebView webViewNews;
    

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

        if (context instanceof MainActivity){
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mainActivity != null){

            if (mainActivity.getSupportActionBar()!=null){

                mainActivity.getSupportActionBar().hide();

            }
        }

        return inflater.inflate(R.layout.fragment_news_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        webViewNews = view.findViewById(R.id.web_view_news_content);

        Bundle args = getArguments();
        if (args!=null){
            article = args.getParcelable(Constants.NEWS_DETAIL_DATA_KEY);
            if (article != null) {
                setupWebView();
            }
        }
    }

    private void setupWebView() {
        webViewNews.clearCache(false);
        webViewNews.clearHistory();
        webViewNews.bringToFront();
        webViewNews.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        webViewNews.loadUrl(article.getUrl());
    }
}
