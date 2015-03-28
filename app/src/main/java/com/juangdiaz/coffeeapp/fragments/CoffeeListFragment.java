package com.juangdiaz.coffeeapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class CoffeeListFragment extends Fragment {

    @InjectView(R.id.list_view)
    ListView mListView;


    ListAdapter mListAdapter;

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
    }





    private void downloadData(){

        ApiClient.getCoffeeApiClient().listCoffee()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Coffee>>() {
                    @Override
                    public void onCompleted() {

                    }


                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), "Failed to retrieve list",
                                Toast.LENGTH_LONG).show();
                    }


                    @Override
                    public void onNext(List<Coffee> results) {
                        List<Coffee> resultData = results;

                        mListAdapter = new ListAdapter(getActivity(), 0, results);
                        mListView.setAdapter(mListAdapter);

                    }
                });
    }

}
