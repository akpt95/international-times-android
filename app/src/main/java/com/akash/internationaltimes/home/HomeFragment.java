package com.akash.internationaltimes.home;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akash.internationaltimes.MainActivity;
import com.akash.internationaltimes.R;
import com.akash.internationaltimes.details.NewsDetailsFragment;
import com.akash.internationaltimes.interfaces.FragmentCommunicationInterface;
import com.akash.internationaltimes.interfaces.RecyclerViewItemClickListner;
import com.akash.internationaltimes.network.model.NewsResponse;
import com.akash.internationaltimes.utils.Constants;

import org.jetbrains.annotations.NotNull;


public class HomeFragment extends Fragment implements RecyclerViewItemClickListner {

    private static final String TAG = Fragment.class.getSimpleName();

    private Context context;
    private MainActivity mainActivity;

    private FragmentCommunicationInterface fragmentCommunicationInterface;

    private TextView textLocation;
    private RecyclerView recyclerViewHeadlines;
    private HeadlinesAdapter headlinesAdapter;

    private NewsResponse newsResponse;

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        this.context = context;

        if (context instanceof MainActivity){
            mainActivity = (MainActivity) context;
        }

        if (context instanceof FragmentCommunicationInterface){

            fragmentCommunicationInterface = (FragmentCommunicationInterface) context;
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mainActivity != null){

            if (mainActivity.getSupportActionBar()!=null){

                mainActivity.getSupportActionBar().show();

            }
        }
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textLocation = view.findViewById(R.id.text_select_location);
        recyclerViewHeadlines = view.findViewById(R.id.recycler_view_headlines);

        Bundle args = getArguments();
        if (args != null) {
            newsResponse = args.getParcelable(Constants.NEWS_DATA_KEY);
            if (newsResponse !=null){
                setUpRecyclerView(newsResponse);
            }
        }
    }



    private void setUpRecyclerView(NewsResponse newsResponse) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerViewHeadlines.setLayoutManager(linearLayoutManager);

        headlinesAdapter = new HeadlinesAdapter(newsResponse.getArticles(), this);
        recyclerViewHeadlines.setAdapter(headlinesAdapter);

    }

    @Override
    public void onItemClick(View view, int position) {

        Bundle args = new Bundle();
        args.putParcelable(Constants.NEWS_DETAIL_DATA_KEY, newsResponse.getArticles().get(position));

        fragmentCommunicationInterface.replaceFragment(NewsDetailsFragment.class.getName(), Constants.NEWS_DETAILS_FRAGMENT_KEY, args, true);

    }
}
