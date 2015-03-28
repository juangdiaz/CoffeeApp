package com.juangdiaz.coffeeapp.adapter;

import android.content.Context;
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

        return row;

    }


    private static final class ResultsViewHolder {

        public TextView coffeeName;
        public TextView coffeeDescription;
        public ImageView coffeeImg;

        public ResultsViewHolder(View v) {

            coffeeName = (TextView) v.findViewById(R.id.coffee_name);
            coffeeDescription = (TextView) v.findViewById(R.id.coffee_description);
            coffeeImg = (ImageView) v.findViewById(R.id.coffee_img);
        }


    }


}

