package com.juangdiaz.coffeeapp.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.juangdiaz.coffeeapp.R;
import com.juangdiaz.coffeeapp.model.Coffee;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by juangdiaz on 3/28/15.
 */
public class ListAdapter extends ArrayAdapter<Coffee> {

    private final LayoutInflater mLayoutInflater;

    public ListAdapter(Context context, int resource, List<Coffee> objects) {
        super(context, resource, objects);


        mLayoutInflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ResultsViewHolder holder;

        if (row == null) {
            row = mLayoutInflater.inflate(R.layout.coffee_list, parent, false);
            holder = new ResultsViewHolder(row);
            row.setTag(holder);
        }
        else {
            holder = (ResultsViewHolder) row.getTag();
        }

        Coffee results = getItem(position);

        holder.coffeeName.setText(results.getName());
        holder.coffeeDescription.setText(results.getDesc());

        // Load the screen  image on a background thread
        if(!results.getImage_url().isEmpty()) {
            Picasso.with(getContext())
                    .load(results.getImage_url())
                    .into(holder.coffeeImg);
        }
        else {

           holder.coffeeImg.setImageDrawable(null);
        }

        return row;

    }

     static final class ResultsViewHolder {

        @InjectView(R.id.coffee_name) TextView coffeeName;
        @InjectView(R.id.coffee_description) TextView coffeeDescription;
        @InjectView(R.id.coffee_img) ImageView coffeeImg;

        public ResultsViewHolder(View v) {
            ButterKnife.inject(this, v);
        }


    }


}

