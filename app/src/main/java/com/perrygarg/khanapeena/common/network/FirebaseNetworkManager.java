package com.perrygarg.khanapeena.common.network;

import com.google.firebase.database.DatabaseReference;

import static android.R.attr.id;

/**
 * Created by PerryGarg on 27-08-2017.
 */

public class FirebaseNetworkManager {

    /**
     * Make following object volatile if this instance has to be used in multithreading.
     */
    private static FirebaseNetworkManager networkManager;

    private final FirebaseHandler firebaseHandler;

    private FirebaseNetworkManager() {
        firebaseHandler = FirebaseHandler.getInstance();
    }

    /**
     * Thread safe getInstance method to get singelton instance of NetworkManager class.
     */
    public static FirebaseNetworkManager getInstance() {
        if (networkManager == null) {
            synchronized (FirebaseHandler.class) {
                if (networkManager == null) {
                    networkManager = new FirebaseNetworkManager();
                }
            }
        }
        return networkManager;
    }

    public boolean isUserLoggedIn() {
        return firebaseHandler.getFirebaseAuth().getCurrentUser() == null ? false : true;
    }

    public void fetchServingStations(FirebaseValueEventListener listener) {
        DatabaseReference reference = firebaseHandler.getDatabaseChildReference(WebConstants.SERVING_STATIONS_SCHEMA);
        reference.orderByPriority().addListenerForSingleValueEvent(listener);
    }

    public void fetchTrainList(FirebaseValueEventListener listener) {
        DatabaseReference reference = firebaseHandler.getDatabaseChildReference(WebConstants.TRAIN_LIST_SCHEMA);
        reference.orderByKey().addListenerForSingleValueEvent(listener);
    }
}
