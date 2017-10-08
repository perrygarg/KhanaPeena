package com.perrygarg.khanapeena.home.contract;

import com.perrygarg.khanapeena.home.model.Train;
import com.perrygarg.khanapeena.home.model.TrainRoute;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by PerryGarg on 15-08-2017.
 */

public interface HomeContract {

    interface View {
        void showProgress(int processCode);
        void hideProgress(int processCode);
        void showError(String errMsg);

        void onAutocompleteTrainSuccess(ArrayList<Train> trainArrayList);

        void fetchAutoCompleteTrainList(String partialTrainInfo);

        void setIntersectionStations(ArrayList<TrainRoute> intersectionStations);

        void disableDates(Calendar[] disabledDates, boolean highlightToday);

        void showToastMessage(int taskCode);

        void onSuccessCheckTrainRunAheadViaLiveAPI(boolean shouldProceed);

        void onSuccessFetchTrainList(ArrayList<Train> trainList);
    }

    interface Presenter {
        void fetchAutoCompleteTrainList(String partialTrainInfo);

        void fetchRouteOfSelectedTrain(Train train);

        void fetchServingStations();

        void checkTrainRunAheadViaLiveAPI(String selectedTrainNumber, String selectedDate, String selectedStationCode);

        void fetchTrainList();
    }

}
