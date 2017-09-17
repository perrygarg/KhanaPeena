package com.perrygarg.khanapeena.foodlisting.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.perrygarg.khanapeena.R;
import com.perrygarg.khanapeena.common.util.DividerItemDecoration;
import com.perrygarg.khanapeena.foodlisting.adapter.MenuAdapter;
import com.perrygarg.khanapeena.foodlisting.model.Items;

import java.util.ArrayList;

/**
 * Created by Shubham Gupta on 04-10-2015.
 */
public class MyFragment extends android.support.v4.app.Fragment {
    public ArrayList<Items> items;
//    public Order order;
    Context context;
   public MyFragment(){

   }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment, container,false);
        MenuAdapter adapter  = new MenuAdapter(items,getActivity());
//        MenuAdapter adapter  = new MenuAdapter();//perry remove line
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), getActivity().getResources().getDrawable(R.drawable.abc_list_divider_mtrl_alpha),
                        true, true, R.color.dividerColor));
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        return view ;
    }
}
