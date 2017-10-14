package com.perrygarg.khanapeena.home.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by PerryGarg on 14-10-2017.
 */

@IgnoreExtraProperties
public class AppConfig {

    public int distance_prior_to_order;
    public int time_prior_to_order;
    public int serving_end_timing;
    public int serving_start_timing;
    public ArrayList<String> stations_served;

    public AppConfig() {

    }

    public AppConfig(int distance_prior_to_order, int time_prior_to_order, int serving_start_timing, int serving_end_timing) {
        this.distance_prior_to_order = distance_prior_to_order;
        this.time_prior_to_order = time_prior_to_order;
        this.serving_start_timing = serving_start_timing;
        this.serving_end_timing = serving_end_timing;
    }

}
