package com.juangdiaz.coffeeapp.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.juangdiaz.coffeeapp.R;
import com.juangdiaz.coffeeapp.data.ApiClient;
import com.juangdiaz.coffeeapp.model.Coffee;
import com.squareup.picasso.Picasso;


import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class CoffeeDetailFragment extends Fragment {


    public static final String ARG_COFFEE_ID = "selected_coffee";

    Coffee mResultData;

    String mCoffeeid;

    private ProgressDialog loading;


    @InjectView(R.id.coffee_detail_name)
    TextView coffeeDetailName;

    @InjectView(R.id.coffee_detail_description)
    TextView coffeeDetailDescription;

    @InjectView(R.id.coffee_detail_last_updated)
    TextView coffeeDetailLastUpdate;

    @InjectView(R.id.coffee_detail_img)
    ImageView coffeeDetailDetailImage;






    public CoffeeDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments().containsKey(ARG_COFFEE_ID)) {
            String id = getArguments().getString(ARG_COFFEE_ID); // get item from bundle
            mCoffeeid = id;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_coffee_detail, container, false);
        ButterKnife.inject(this, rootView);

        downloadData(mCoffeeid);

        return rootView;
    }


    private void updateView() {

        // Show the content.
        if (mResultData != null) {
            if (!Strings.isNullOrEmpty(mResultData.getName())) {
                ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(Html.fromHtml(mResultData.getName()).toString()); // title in the action bar
                coffeeDetailName.setText(Html.fromHtml(mResultData.getName()).toString());
            }
            if (!Strings.isNullOrEmpty(mResultData.getDesc())) {
                coffeeDetailDescription.setText(Html.fromHtml(mResultData.getDesc()).toString());
            }
            if (!Strings.isNullOrEmpty(mResultData.getLastTimeUpdatedFormattedDate())) {
                coffeeDetailLastUpdate.setText(Html.fromHtml(mResultData.getLastTimeUpdatedFormattedDate()).toString());
            }
            if (!Strings.isNullOrEmpty(mResultData.getImage_url())) {
                Picasso.with(getActivity().getApplicationContext())
                        .load(mResultData.getImage_url())
                        .into(coffeeDetailDetailImage);
            }
            setHasOptionsMenu(true);
            setShareIntent();


        }
    }


    private void downloadData(String id){
        showLoading();
        ApiClient.getCoffeeApiClient().DetailCoffee(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Coffee>() {
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
                    public void onNext(Coffee results) {
                        mResultData = results;
                        updateView();
                    }
                });
    }


    private void showLoading() {
        loading = new ProgressDialog(getActivity());
        loading.setTitle("Loading");
        loading.setMessage("Wait while loading...");
        loading.show();
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuShare = menu.findItem(R.id.menu_item_share);
        ShareActionProvider shareAction = (ShareActionProvider) MenuItemCompat.getActionProvider(menuShare);

        shareAction.setShareIntent(setShareIntent());
    }



    // Call to update the share intent
    private Intent setShareIntent() {

        // create an Intent with the contents of the TextView
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        if(mResultData != null) {
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Checkout this amazing Coffee");
            shareIntent.putExtra(Intent.EXTRA_TEXT, mResultData.getName() + ": " + mResultData.getDesc());
        }
        return shareIntent;


    }
}
