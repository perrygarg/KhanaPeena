package com.perrygarg.khanapeena.home.contract;

/**
 * Created by PerryGarg on 15-08-2017.
 */

public interface HomeContract {

    interface View {
        void showProgress();
        void hideProgress();
        void showError(String errMsg);

        void onAutocompleteTrainSuccess();

        void fetchAutoCompleteTrainList(String partialTrainInfo);
    }

    interface Presenter {
        void fetchAutoCompleteTrainList(String partialTrainInfo);
    }

}
