package com.juangdiaz.coffeeapp.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.juangdiaz.coffeeapp.R;
import com.juangdiaz.coffeeapp.fragments.CoffeeDetailFragment;
import com.juangdiaz.coffeeapp.fragments.CoffeeListFragment;

public class CoffeeMainActivity extends ActionBarActivity  implements CoffeeListFragment.Callbacks{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.drip_white);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coffee_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Callback method from {@link com.juangdiaz.coffeeapp.fragments.CoffeeListFragment.Callbacks
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {

        Intent detailIntent = new Intent(getApplicationContext(), CoffeeDetailActivity.class);
        detailIntent.putExtra(CoffeeDetailFragment.ARG_COFFEE_ID, id);
        startActivity(detailIntent);
    }
}
