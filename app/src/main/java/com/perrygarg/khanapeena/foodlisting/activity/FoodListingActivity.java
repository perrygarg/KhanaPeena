package com.perrygarg.khanapeena.foodlisting.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.perrygarg.khanapeena.R;
import com.perrygarg.khanapeena.common.activity.BaseActivity;
import com.perrygarg.khanapeena.foodlisting.adapter.PagerAdapter;
import com.perrygarg.khanapeena.foodlisting.model.Categories;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodListingActivity extends BaseActivity {
    PagerAdapter pagerAdapter;
    TabLayout tabLayout;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_listing);

        setupToolbar(getString(R.string.app_name), true);

        init();

        try {
            requestLocal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        pagerAdapter = new PagerAdapter(fragmentManager,this);

        tabLayout = findViewById(R.id.tabLayout);
        pager = findViewById(R.id.pager);
    }


    public void requestLocal() throws Exception {
        String jsonDummy = "{\n" +
                "  \"categories\": [\n" +
                "    {\n" +
                "      \"name\": \"Thalis\",\n" +
                "      \"items\": [\n" +
                "        {\n" +
                "          \"id\": \"1\",\n" +
                "          \"price\": \"100\",\n" +
                "          \"desc\": \"Regular description\",\n" +
                "          \"image\": \"https://img04.olx.in/images_olxin/318390615_1_144x108_free-home-deliverd-lunch-breakfst-dinner-at-daily-delhi.jpg\",\n" +
                "          \"name\": \"Regular Thali\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": \"2\",\n" +
                "          \"price\": \"150\",\n" +
                "          \"desc\": \"Shahi description\",\n" +
                "          \"image\": \"https://img04.olx.in/images_olxin/318390615_1_144x108_free-home-deliverd-lunch-breakfst-dinner-at-daily-delhi.jpg\",\n" +
                "          \"name\": \"Shahi Thali\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Al a carte\",\n" +
                "      \"items\": [\n" +
                "        {\n" +
                "          \"id\": \"1\",\n" +
                "          \"price\": \"200\",\n" +
                "          \"desc\": \"Regular description\",\n" +
                "          \"image\": \"https://img04.olx.in/images_olxin/318390615_1_144x108_free-home-deliverd-lunch-breakfst-dinner-at-daily-delhi.jpg\",\n" +
                "          \"name\": \"Daal Makhni\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": \"2\",\n" +
                "          \"price\": \"35\",\n" +
                "          \"desc\": \"Shahi description\",\n" +
                "          \"image\": \"https://img04.olx.in/images_olxin/318390615_1_144x108_free-home-deliverd-lunch-breakfst-dinner-at-daily-delhi.jpg\",\n" +
                "          \"name\": \"Butter naan\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        JSONObject jsonObject = new JSONObject(jsonDummy);
        JSONArray jsonArray = jsonObject.getJSONArray("categories");
        ArrayList<Categories> categories = new ArrayList();

        for (int i = 0; i < jsonArray.length(); i++) {
            Categories category = new Categories(jsonArray.getJSONObject(i));
            categories.add(category);
        }

        pagerAdapter.titles = new String[categories.size()];

        for (int i = 0; i < categories.size(); i++) {
            System.out.println(categories.get(i).getName());
            pagerAdapter.titles[i] = categories.get(i).getName();
        }

        setupPager(categories);
        // System.out.println(categories.get(0).getItems().get(0).getName());
    }

    void setupPager(ArrayList<Categories> categories) {
        pagerAdapter.categories = categories;
        pager.setAdapter(pagerAdapter);
        tabLayout.setTabsFromPagerAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(pager);
    }

}
