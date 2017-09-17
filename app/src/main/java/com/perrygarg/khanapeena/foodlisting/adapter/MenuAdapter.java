package com.perrygarg.khanapeena.foodlisting.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.perrygarg.khanapeena.R;
import com.perrygarg.khanapeena.common.util.CircleImageView;
import com.perrygarg.khanapeena.foodlisting.model.Items;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder> {
    String rs;
    ArrayList<Items> items;
//    Order order;
    SharedPreferences preferences;
    Context context;
//    UpdateTotalPrice updateTotalPrice;

    public MenuAdapter(ArrayList<Items> items, Context context) {
        this.items = items;
//        this.order = order;
//        this.order.listItems();
//        preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
//        this.context = context;
//        updateTotalPrice = (UpdateTotalPrice) context;
        updatePreferences();
    }

    @Override
    public MenuHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.menu_item, viewGroup, false);
        rs = viewGroup.getContext().getResources().getString(R.string.Rs);
        MenuHolder menuHolder = new MenuHolder(v);
        return menuHolder;
    }

    @Override
    public void onBindViewHolder(final MenuHolder menuHolder, final int i) {

//        menuHolder.price.setText(String.format(rs, items.get(i).getPrice()));
//        menuHolder.name.setText(items.get(i).getName());
//        Log.i("TAG", "items:" + order.items.size());
//        Items item = order.getItem(items.get(i));
//        Log.i("TAG", "items:" + item);
//        if (item != null)
//            menuHolder.qty.setText(String.valueOf(item.getQty()));
//        else
//            menuHolder.qty.setText("0");
//
//        Picasso.with(MyApplication.getContext()).load(items.get(i).getImage())
//                .placeholder(R.drawable.kitchen_small)
//                .into(menuHolder.imageView);
//
//        menuHolder.plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                menuHolder.qty.setText(String.valueOf(Integer.parseInt(menuHolder.qty.getText().toString()) + 1));
//                order.addItem(items.get(i));
//                updatePreferences();
//            }
//        });
//        menuHolder.minus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (Integer.parseInt(menuHolder.qty.getText().toString()) > 0) {
//                    menuHolder.qty.setText(String.valueOf(Integer.parseInt(menuHolder.qty.getText().toString()) - 1));
//                    order.removeItem(items.get(i));
//                    //   menuHolder.qty.setText(""+items.get(i).getQty());
//                    updatePreferences();
//                }
//            }
//        });
    }

    void updatePreferences() {
//        updateTotalPrice.onItemsChangedInOrder(order);
////        SharedPreferences.Editor editor = preferences.edit();
////        Gson gson = new Gson();
////        String json = gson.toJson(order);
////        editor.putString(Config.PREF_ORDER, json);
////        editor.commit();
//        Log.i("Adapter", "size:" + order.items.size());
//        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
//        return 0;
    }

    public class MenuHolder extends RecyclerView.ViewHolder {
        TextView price;
        TextView name;
        ImageButton minus;
        ImageButton plus;
        TextView qty;
        CircleImageView imageView;

        public MenuHolder(View v) {
            super(v);
            price = (TextView) v.findViewById(R.id.product_price);
            name = (TextView) v.findViewById(R.id.product_name);
            minus = (ImageButton) v.findViewById(R.id.minus_sign);
            plus = (ImageButton) v.findViewById(R.id.plus_sign);
            qty = (TextView) v.findViewById(R.id.product_qty);
            imageView = (CircleImageView) v.findViewById(R.id.product_image);

        }


    }
}
