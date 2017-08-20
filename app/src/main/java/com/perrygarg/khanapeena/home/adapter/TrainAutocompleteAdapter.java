package com.perrygarg.khanapeena.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.perrygarg.khanapeena.R;
import com.perrygarg.khanapeena.home.model.Train;
import com.perrygarg.khanapeena.home.contract.HomeContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PerryGarg on 20-08-2017.
 */

public class TrainAutocompleteAdapter extends BaseAdapter implements Filterable {

    private static final int MAX_RESULTS = 6;
    private Context mContext;
    private HomeContract.View view;
    private List<Train> resultList = new ArrayList<Train>();

    public TrainAutocompleteAdapter(Context context, HomeContract.View view) {
        mContext = context;
        this.view = view;
    }

    public void setTrainsListInstance(List<Train> trains) {
        resultList = trains;

    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Train getItem(int i) {
        return resultList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.autocomplete_train_list_row, viewGroup, false);
        }
        ((TextView) view.findViewById(R.id.text1)).setText(getItem(i).trainName);
        ((TextView) view.findViewById(R.id.text2)).setText(getItem(i).trainNumber);
        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    view.fetchAutoCompleteTrainList(constraint.toString());

                    // Assign the data to the FilterResults
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<Train>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }

    private List<Train> findTrains(Context mContext, String s) {
        List<Train> trains = new ArrayList<>();
        Train train = new Train();
        train.trainName = "dummy1";
        train.trainNumber = "12345";
        trains.add(train);
        Train train1 = new Train();
        train1.trainName = "dummy2";
        train1.trainNumber = "23456";
        trains.add(train1);
        return trains;
    }
}
