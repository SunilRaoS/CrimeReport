package com.happiestminds.civiccop.firebase;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by sakthi on 18-01-2016.
 */
public class LoginHandler implements Firebase.AuthResultHandler {
    @Override
    public void onAuthenticated(AuthData authData) {
        RootRef.authData = authData;
    }

    @Override
    public void onAuthenticationError(FirebaseError firebaseError) {

    }
}
