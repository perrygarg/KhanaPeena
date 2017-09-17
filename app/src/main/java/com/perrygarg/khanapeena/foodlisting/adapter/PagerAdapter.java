package com.perrygarg.khanapeena.foodlisting.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.google.gson.Gson;
import com.perrygarg.khanapeena.foodlisting.fragment.MyFragment;
import com.perrygarg.khanapeena.foodlisting.model.Categories;

import java.util.ArrayList;

/**
 * Created by Shubham Gupta on 04-10-2015.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    Context context;
   public PagerAdapter(FragmentManager fm, Context context){
      super(fm);
       this.context = context;
   }
    public String titles[];//= {"Burger","Slides","Drinks","Pizzas","Wraps","Happy Meals","Desserts"};
    public ArrayList<Categories> categories;
    final Gson gson = new Gson();
//    public Order order = new Order();

    @Override
    public Fragment getItem(int position) {
        MyFragment myFragment = new MyFragment();
        myFragment.items = categories.get(position).getItems();
//        myFragment.order = order;

        return myFragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
