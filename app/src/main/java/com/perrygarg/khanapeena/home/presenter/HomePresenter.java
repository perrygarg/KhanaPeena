package com.perrygarg.khanapeena.home.presenter;

import android.util.Log;

import com.perrygarg.khanapeena.common.model.MasterResponse;
import com.perrygarg.khanapeena.common.model.ValidationError;
import com.perrygarg.khanapeena.common.network.FirebaseNetworkManager;
import com.perrygarg.khanapeena.common.network.FirebaseValueEventListener;
import com.perrygarg.khanapeena.common.network.WebConstants;
import com.perrygarg.khanapeena.common.network.WebManager;
import com.perrygarg.khanapeena.common.network.WebService;
import com.perrygarg.khanapeena.common.network.WebServiceListener;
import com.perrygarg.khanapeena.common.util.AppUtil;
import com.perrygarg.khanapeena.common.util.UIUtil;
import com.perrygarg.khanapeena.home.contract.HomeContract;
import com.perrygarg.khanapeena.home.listeners.ServingStationsListener;
import com.perrygarg.khanapeena.home.model.AppConfig;
import com.perrygarg.khanapeena.home.model.CurrentStation;
import com.perrygarg.khanapeena.home.model.Day;
import com.perrygarg.khanapeena.home.model.Train;
import com.perrygarg.khanapeena.home.model.TrainAutoCompleteResponse;
import com.perrygarg.khanapeena.home.model.TrainDays;
import com.perrygarg.khanapeena.home.model.TrainLiveStatusResponse;
import com.perrygarg.khanapeena.home.model.TrainRoute;
import com.perrygarg.khanapeena.home.model.TrainRouteResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by PerryGarg on 20-08-2017.
 */

public class HomePresenter implements HomeContract.Presenter, WebServiceListener, ServingStationsListener {
    HomeContract.View view;
    private AppConfig config;
    private ArrayList<TrainRoute> routes;
    private String selectedStationCode;
    private TrainDays trainDays;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void fetchAutoCompleteTrainList(String partialTrainInfo) {
        view.showProgress(WebConstants.WS_CODE_TRAIN_AUTOCOMPLETE);
        WebService service = WebManager.getService(WebConstants.WS_CODE_TRAIN_AUTOCOMPLETE, this);
        service.getData(partialTrainInfo);
    }

    @Override
    public void fetchRouteOfSelectedTrain(Train train) {
        view.showProgress(WebConstants.FETCH_TRAIN_ROUTE_SERVICE);
        WebService service = WebManager.getService(WebConstants.FETCH_TRAIN_ROUTE_SERVICE, this);
        service.getData(train);
    }

    @Override
    public void fetchConfigFromFirebase() {
//        view.showProgress(WebConstants.FETCH_SERVING_STATIONS_SERVICE);
        FirebaseValueEventListener listener = new FirebaseValueEventListener(this, WebConstants.FETCH_CONFIG_SERVICE);
        FirebaseNetworkManager.getInstance().fetchServingStations(listener);
    }

    @Override
    public void checkTrainRunAheadViaLiveAPI(String selectedTrainNumber, String selectedDate, String selectedStationCode) {
        this.selectedStationCode = selectedStationCode;
        view.showProgress(WebConstants.CHECK_TRAIN_LIVE_API_SERVICE);
        WebService service = WebManager.getService(WebConstants.CHECK_TRAIN_LIVE_API_SERVICE, this);
        service.getData(selectedTrainNumber, selectedDate);
    }

    @Override
    public void fetchTrainList() {
        view.showProgress(WebConstants.FETCH_TRAIN_LIST_SERVICE);
        FirebaseValueEventListener listener = new FirebaseValueEventListener(this, WebConstants.FETCH_TRAIN_LIST_SERVICE);
        FirebaseNetworkManager.getInstance().fetchTrainList(listener);
    }

    @Override
    public boolean isScheduledTimeValidated(String selectedStationCode, String selectedDate, boolean isSelectedStationSource) {
        return scheduledServingTimeValidated(selectedStationCode) && scheduledMarginalTimeValidated(selectedStationCode, selectedDate, isSelectedStationSource);
    }

    private boolean scheduledMarginalTimeValidated(String selectedStationCode, String selectedDate, boolean isSelectedStationSource) {
        if (!isSelectedDateTodaysDate(selectedDate)) {
            return true;
        } else {
            String schDepartureTime = null;
            if (isSelectedStationSource) {
                schDepartureTime = routes.get(0).schDeparture;
            } else {
                for (int i = 0; i < routes.size(); i++) {
                    TrainRoute route = routes.get(i);
                    if (route.routeStation.stationCode.equalsIgnoreCase(selectedStationCode)) {
                        if (i == routes.size() - 1) {
                            schDepartureTime = route.schArrival;
                        } else {
                            schDepartureTime = route.schDeparture;
                        }
                        break;
                    }
                }
            }

            if (schDepartureTime != null) {
                String[] schDepTime = schDepartureTime.split(":");
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int date = calendar.get(Calendar.DATE);
                calendar.set(year, month, date, Integer.parseInt(schDepTime[0]), Integer.parseInt(schDepTime[1]));
                long depTimeMillis = calendar.getTime().getTime();
                Calendar calendar1 = Calendar.getInstance();
                long currTimeMillis = calendar1.getTime().getTime();
                long difference = depTimeMillis - currTimeMillis;
                if (Math.signum(difference) >= 1.0f) {
                    long differenceMin = difference / 1000 / 60;
                    if (differenceMin >= config.time_prior_to_order) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean isSelectedDateTodaysDate(String selectedDate) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        Date today = c.getTime();

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        try {
            c.setTime(sdf.parse(selectedDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date dateSpecified = c.getTime();

        if (dateSpecified.after(today)) {
            return false;
        } else {
            return true;
        }
    }


//        if(currentStation != null) {
//            Calendar calendar = Calendar.getInstance();
//            Date formattedArrDate = null;
//
//            String arrivalDate = currentStation.actualArrivalDate;
//            arrivalDate = arrivalDate.replaceAll(" ", "-");
//            String arrivalTime = currentStation.actualArrivalTime;
//            String[] arrivalDateSplit = arrivalDate.split("-");
//            String month = arrivalDateSplit[1];
//            String date = arrivalDateSplit[0];
//            String year = arrivalDateSplit[2];
//            String[] arrTimeSplit = arrivalTime.split(":");
//            String hour = arrTimeSplit[0];
//            String min = arrTimeSplit[1];
//            DateFormat format = new SimpleDateFormat("MMM dd HH:mm:ss yyyy", Locale.US);
//            try {
//                //perry
//                formattedArrDate = format.parse(month + " " + date + " " + hour + ":" + min + ":00" + " " + year);
//                Log.d("", "");
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            Date formattedCurrDate = calendar.getTime();
//
//            if(formattedCurrDate.before(formattedArrDate)) {
//                return true;
//            } else {
//                long difference = formattedArrDate.getTime() - formattedCurrDate.getTime();
//                if(Math.signum(difference) >= 1.0f) {
//                    long differenceMin = difference/1000/60;
//                    if(difference >= 60) {
//                        return true;
//                    } else {
//                        return false;
//                    }
//                } else {
//                    return false;
//                }
//            }
//
//        }
//        return false;
//    }

    private boolean scheduledServingTimeValidated(String selectedStationCode) {
        String schDepartureTime = null;
        for (int i = 0; i < routes.size(); i++) {
            TrainRoute route = routes.get(i);
            if (route.routeStation.stationCode.equalsIgnoreCase(selectedStationCode)) {
                if (i == routes.size() - 1) {
                    schDepartureTime = route.schArrival;
                } else {
                    schDepartureTime = route.schDeparture;
                }
                break;
            }
        }

        if (schDepartureTime != null) {
            String[] schDepTime = schDepartureTime.split(":");
            int hour = Integer.parseInt(schDepTime[0]);
            if (hour >= config.serving_start_timing && hour < config.serving_end_timing) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean selectedStationIsSourceStation(String selectedStationCode) {
        return routes.get(0).routeStation.stationCode.equalsIgnoreCase(selectedStationCode);
    }

    @Override
    public void onServiceBegin(int taskCode) {

    }

    @Override
    public void onServiceSuccess(MasterResponse masterResponse, int taskCode) {
        switch (taskCode) {
            case WebConstants.WS_CODE_TRAIN_AUTOCOMPLETE:
                Train[] trains = ((TrainAutoCompleteResponse) masterResponse).train;
                int totalTrains = ((TrainAutoCompleteResponse) masterResponse).total;
                if (totalTrains > 5) {
//            totalTrains = 5;
                }
                ArrayList<Train> trainList = new ArrayList<>();
                for (int i = 0; i < totalTrains; i++) {
                    trainList.add(trains[i]);
                }
                view.hideProgress(WebConstants.WS_CODE_TRAIN_AUTOCOMPLETE);
                view.onAutocompleteTrainSuccess(trainList);
                break;

            case WebConstants.FETCH_TRAIN_ROUTE_SERVICE:
                TrainRoute[] route = ((TrainRouteResponse) masterResponse).trainRoute;
                trainDays = ((TrainRouteResponse) masterResponse).trainDays;
                routes = new ArrayList<>(Arrays.asList(route));
//                manipulateDatesInDatePicker(trainDays, routes);
                calculateIntersectionStations(config.stations_served, routes);
                break;

            case WebConstants.CHECK_TRAIN_LIVE_API_SERVICE:
                CurrentStation actStation = null;
                CurrentStation currentStation[] = ((TrainLiveStatusResponse) masterResponse).liveRoute;
                for (CurrentStation cStation : currentStation) {
                    if (cStation.station.stationCode.equalsIgnoreCase(this.selectedStationCode)) {
                        actStation = cStation;
                        break;
                    }
                }
                if (isTimeValidatedViaLiveApi(actStation)) {
                    view.hideProgress(taskCode);
                    view.onSuccessCheckTrainRunAheadViaLiveAPI(true);
                } else {
                    view.hideProgress(taskCode);
                    view.onSuccessCheckTrainRunAheadViaLiveAPI(false);
                }
                break;
        }

    }

    private boolean isTimeValidatedViaLiveApi(CurrentStation currentStation) {
        if (currentStation != null) {
            Calendar calendar = Calendar.getInstance();
            Date formattedArrDate = null;

            String arrivalDate = currentStation.actualArrivalDate;
            arrivalDate = arrivalDate.replaceAll(" ", "-");
            String arrivalTime = currentStation.actualArrivalTime;
            String[] arrivalDateSplit = arrivalDate.split("-");
            String month = arrivalDateSplit[1];
            String date = arrivalDateSplit[0];
            String year = arrivalDateSplit[2];
            String[] arrTimeSplit = arrivalTime.split(":");
            String hour = arrTimeSplit[0];
            String min = arrTimeSplit[1];
            DateFormat format = new SimpleDateFormat("MMM dd HH:mm:ss yyyy", Locale.US);
            try {
                //perry
                formattedArrDate = format.parse(month + " " + date + " " + hour + ":" + min + ":00" + " " + year);
                Log.d("", "");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date formattedCurrDate = calendar.getTime();

            if (formattedCurrDate.before(formattedArrDate)) {
                return liveServingTimeValidated(hour);
            } else {
                long difference = formattedArrDate.getTime() - formattedCurrDate.getTime();
                if (Math.signum(difference) >= 1.0f) {
                    long differenceMin = difference / 1000 / 60;
                    if (differenceMin >= config.time_prior_to_order) {
                        return liveServingTimeValidated(hour);
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }

        } else {
            return false;
        }
    }

    private boolean liveServingTimeValidated(String hours) {
        if (hours != null) {
            int hour = Integer.parseInt(hours);
            if (hour >= config.serving_start_timing && hour < config.serving_end_timing) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void manipulateDatesInDatePicker(String selectedStationCode) {
        Day[] days = trainDays.day;

        int dayOfReachingStation = -1;

        for (TrainRoute route : routes) {
            if (route.routeStation.stationCode.equalsIgnoreCase(selectedStationCode.trim())) {
                dayOfReachingStation = route.day;
            }
        }

        days[4].runs = "N";
        days[6].runs = "N";

        boolean weekDaysToDisable[] = new boolean[8]; //taking size 8 as java days works from 1-7 (SUN-SAT)
        for (Day day : days) {
            if (day.runs.equals("N")) {
                int d = AppUtil.getWeekDayOfWeek(day.dayCode);
                int finalDay = AppUtil.shiftDesiredDays(d, dayOfReachingStation - 1);
                weekDaysToDisable[finalDay] = true; //1 for SUN - 7 for SAT
            }
        }

        boolean highlightToday = false;

        Calendar today = Calendar.getInstance();
        if(weekDaysToDisable[today.get(Calendar.DAY_OF_WEEK)]) {
            highlightToday = true;
        }

        Calendar[] disabledDates = getCalendarInstancesToDisable(today, 30, weekDaysToDisable);

        view.disableDates(disabledDates, highlightToday);
    }

    private static Calendar[] getCalendarInstancesToDisable(Calendar cal, int totalDays, boolean[] days)
    {
        ArrayList<Calendar> cals = new ArrayList<>();


        Calendar c = Calendar.getInstance();
        c.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        Calendar n;
        for(int i = 0; i <= totalDays; i++)
        {
            if(days[c.get(Calendar.DAY_OF_WEEK)]) {
                if(i != 0) {//If day is not today
                    n = Calendar.getInstance();
                    n.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
                    cals.add(n);
                }
            }
            c.add(Calendar.DATE, 1);
        }

        return cals.toArray(new Calendar[cals.size()]);
    }

    private void calculateIntersectionStations(ArrayList<String> servingStationCodes, ArrayList<TrainRoute> routes) {
        ArrayList<TrainRoute> intersectionStations = new ArrayList<>();
        if (servingStationCodes != null && routes != null) {
            for (TrainRoute route : routes) {
                if (servingStationCodes.contains(route.routeStation.stationCode)) {
                    intersectionStations.add(route);
                }
            }
            view.setIntersectionStations(intersectionStations);
            view.hideProgress(WebConstants.FETCH_TRAIN_ROUTE_SERVICE);
        }
    }

    @Override
    public void onServiceError(String message, int taskCode, int errorType) {
        switch (taskCode) {
            case WebConstants.CHECK_TRAIN_LIVE_API_SERVICE:
                //live API didn't work
                view.hideProgress(taskCode);
                view.showToastMessage("Live API didn't work");
                view.onFailureCheckTrainRunAheadViaLiveAPI(errorType);
                break;
            default:
                view.hideProgress(taskCode);
                view.showToastMessage(message);
                view.resetView();
                break;
        }
    }

    @Override
    public void onValidationError(ValidationError[] validationError, int taskCode) {

    }

    @Override
    public void onSuccessFetchConfig(AppConfig config, int serviceCode) {
//        view.hideProgress(serviceCode);
        this.config = config;
    }

    @Override
    public void onFailureFetchServingStations(int errCode, int serviceCode, String errMsg) {

    }

    @Override
    public void onSuccessFetchTrainList(ArrayList<Train> trainList, int serviceCode) {
        view.hideProgress(serviceCode);
        view.onSuccessFetchTrainList(trainList);
    }

    @Override
    public void onFailureFetchTrainList(int code, int serviceCode, String message) {

    }

}
