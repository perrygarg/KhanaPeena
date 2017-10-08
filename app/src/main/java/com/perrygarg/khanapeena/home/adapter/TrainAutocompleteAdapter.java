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
import com.perrygarg.khanapeena.common.util.DelayAutocompleteTextView;
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
    private List<Train> resultList = new ArrayList();
    private List<Train> filteredList = new ArrayList();
    private Train trainSelected;

    public TrainAutocompleteAdapter(Context context, HomeContract.View view) {
        mContext = context;
        this.view = view;
    }

    public void setTrainsListInstance(List<Train> trains) {
        resultList = trains;
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Train getItem(int i) {
        return filteredList.get(i);
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
        ((TextView) view.findViewById(R.id.text1)).setText(getItem(i).name);
        ((TextView) view.findViewById(R.id.text2)).setText(getItem(i).number);
        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null && !constraint.toString().isEmpty()) {

                    ArrayList<Train> filteredTrains = new ArrayList<>();

                    for (Train train : resultList) {
                        if(String.valueOf(train.number).contains(constraint) || train.name.contains(constraint.toString().toUpperCase())) {
                            filteredTrains.add(train);
                        }
                    }

                    // Assign the data to the FilterResults
                    filterResults.values = filteredTrains;
                    filterResults.count = filteredTrains.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    filteredList = (List<Train>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }

    public void itemSelected(Train b) {
        this.trainSelected = b;
    }
}
