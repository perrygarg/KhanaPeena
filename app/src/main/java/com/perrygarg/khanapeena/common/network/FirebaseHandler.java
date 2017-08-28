package com.perrygarg.khanapeena.common.network;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Perry Garg.
 */

final class FirebaseHandler {

    /**
     * Make following object volatile if this instance has to be used in multithreading.
     */
    private static FirebaseHandler firebaseHandler;

    private final FirebaseAuth firebaseAuth;
    private final DatabaseReference databaseRootReference;
    private final FirebaseDatabase firebaseDatabase;
//    private final FirebaseStorage firebaseStorage;
//    private final StorageReference storageRootRef;

    /**
     * Private constructor to build singleton instance.
     */
    private FirebaseHandler() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
//        firebaseStorage = FirebaseStorage.getInstance();
//        firebaseDatabase.setPersistenceEnabled(true);
        databaseRootReference = firebaseDatabase.getReference();
//        storageRootRef = firebaseStorage.getReference();
    }

    /*
    Thread safe getInstance method to get singelton instance of FirebaseHandler class.
     */
    public static FirebaseHandler getInstance() {
        if (firebaseHandler == null) {
            synchronized (FirebaseHandler.class) {
                if (firebaseHandler == null) {
                    firebaseHandler = new FirebaseHandler();
                }
            }
        }
        return firebaseHandler;
    }

    FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }

//    FirebaseStorage getFirebaseStorage() {
//        return firebaseStorage;
//    }

    DatabaseReference getDatabaseRootReference() {
        return databaseRootReference;
    }

    DatabaseReference getDatabaseChildReference(String child) {
        return databaseRootReference.child(child);
    }

//    StorageReference getStorageChildReference(String child) {
//        return storageRootRef.child(child);
//    }

}
