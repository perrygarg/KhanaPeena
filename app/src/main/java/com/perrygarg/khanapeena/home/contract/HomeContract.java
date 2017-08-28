package com.perrygarg.khanapeena.home.contract;

import com.perrygarg.khanapeena.home.model.Train;

import java.util.ArrayList;

/**
 * Created by PerryGarg on 15-08-2017.
 */

public interface HomeContract {

    interface View {
        void showProgress();
        void hideProgress();
        void showError(String errMsg);

        void onAutocompleteTrainSuccess(ArrayList<Train> trainArrayList);

        void fetchAutoCompleteTrainList(String partialTrainInfo);
    }

    interface Presenter {
        void fetchAutoCompleteTrainList(String partialTrainInfo);

        void fetchServingStations();
    }

}
