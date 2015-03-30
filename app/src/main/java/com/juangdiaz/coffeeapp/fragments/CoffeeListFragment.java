package com.juangdiaz.coffeeapp.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.juangdiaz.coffeeapp.R;
import com.juangdiaz.coffeeapp.adapter.ListAdapter;
import com.juangdiaz.coffeeapp.data.ApiClient;
import com.juangdiaz.coffeeapp.model.Coffee;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;


public class CoffeeListFragment extends Fragment implements AbsListView.OnItemClickListener{

    @InjectView(R.id.list_view)
    ListView mListView;

    List<Coffee> mResultData;
    ListAdapter mListAdapter;

    private ProgressDialog loading;
    private SwipeRefreshLayout swipeRefreshLayout;

    // The fragment's current callback object, which is notified of list item clicks.
    private Callbacks mCallbacks = sDummyCallbacks;

    public CoffeeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coffee_list, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        downloadData();

        //Implementation of Swipe to Refresh
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //check for new Data
                        downloadData();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }


    private void downloadData(){
        showLoading();
        ApiClient.getCoffeeApiClient().listCoffee()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Coffee>>() {
                    @Override
                    public void onCompleted() {
                        loading.dismiss();

                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), "Failed to retrieve list",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onNext(List<Coffee> results) {
                        mResultData = results;
                        updateDisplay();
                    }
                });
    }

    public void updateDisplay(){
        mListAdapter = new ListAdapter(getActivity(), 0, mResultData);
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(this);
    }

    private void showLoading() {
        loading = new ProgressDialog(getActivity());
        loading.setTitle("Loading");
        loading.setMessage("Wait while loading...");
        loading.show();
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mCallbacks.onItemSelected( mListAdapter.getItem(position).getId());
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

}
