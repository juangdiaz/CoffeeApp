package com.juangdiaz.coffeeapp.activities;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.juangdiaz.coffeeapp.R;
import com.juangdiaz.coffeeapp.fragments.CoffeeDetailFragment;

public class CoffeeDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show the Up button in the action bar.


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.drip_white);

        if (savedInstanceState == null) {

            String id = getIntent().getStringExtra(CoffeeDetailFragment.ARG_COFFEE_ID);

            // Create the detail fragment and add it to the activity using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(CoffeeDetailFragment.ARG_COFFEE_ID, id); // put selected item
            CoffeeDetailFragment detailFragment = new CoffeeDetailFragment();
            detailFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, detailFragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coffee_detail, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
