package com.perrygarg.khanapeena.home.contract;

/**
 * Created by PerryGarg on 15-08-2017.
 */

public interface HomeContract {

    interface HomeView {
        void showProgress();
        void hideProgress();
        void showError(String errMsg);

        void onAutocompleteTrainSuccess();
    }

    interface HomePresenter {
        void autocompleteTrain(String partialTrainInfo);
    }

}
