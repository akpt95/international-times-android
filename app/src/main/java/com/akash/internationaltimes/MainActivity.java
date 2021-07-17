package com.akash.internationaltimes;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.FragmentManager;

import com.akash.internationaltimes.category.CategoryFragment;
import com.akash.internationaltimes.home.HomeFragment;
import com.akash.internationaltimes.interfaces.FragmentCommunicationInterface;
import com.akash.internationaltimes.network.RetrofitController;
import com.akash.internationaltimes.network.model.NewsResponse;
import com.akash.internationaltimes.utils.Constants;
import com.akash.internationaltimes.utils.SharedPreferenceUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FragmentCommunicationInterface {

    private static final String TAG= MainActivity.class.getSimpleName();

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);

        SharedPreferenceUtils.getInstance().setLocation("in");
        getNewsData(null, 1);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> replaceFragment(CategoryFragment.class.getName(), Constants.CATEGORY_FRAGMENT_TAG, null,false));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getNewsData(String searchString, int page) {

        progressDialog.setMessage(getResources().getString(R.string.loading_news));
        progressDialog.show();

        String location = SharedPreferenceUtils.getInstance().getLocation();
        String category = SharedPreferenceUtils.getInstance().getCategory();

        final Call<NewsResponse> newsResponseCall = RetrofitController.getInstance().getNewsApi().getTopHeadlines(location,category,searchString,page);

        newsResponseCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NotNull Call<NewsResponse> call, @NotNull Response<NewsResponse> response) {

                if (response.isSuccessful()){
                    NewsResponse newsResponse = response.body();
                    if (newsResponse != null) {

                        Bundle args = new Bundle();
                        args.putParcelable(Constants.NEWS_DATA_KEY, newsResponse);
                        replaceFragment(HomeFragment.class.getName(), Constants.HOME_FRAGMENT_TAG, args, false);
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NotNull Call<NewsResponse> call, @NotNull Throwable t) {
                Log.e(TAG, "getNewsData() failed with error: "+t.getMessage());
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void replaceFragment(String fragmentName, String tag, Bundle args, boolean requiresBackStackAddition){

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if(fragment == null){
            fragment = new FragmentFactory().instantiate(this.getClassLoader(), fragmentName);;
        }

        if (args!=null)
            fragment.setArguments(args);

        if (requiresBackStackAddition){
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, tag).commit();
        }


    }

    @Override
    public void setUpHomeWithNewsData() {

    }
}
