package com.happiestminds.civiccop.firebase;

import android.content.Context;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.happiestminds.civiccop.model.data.User;

/**
 * Created by Sakthi.Surendran on 14-01-2016.
 */
public class RootRef {

    private static Firebase baseRef;
    private static String fireBaseUrl;
    public static AuthData authData;
    public static User user;

    private RootRef() {

    }

    public static void init(Context context, String fireBaseUrl) {
        RootRef.fireBaseUrl = fireBaseUrl;
        Firebase.setAndroidContext(context);
        RootRef.baseRef = new Firebase(RootRef.fireBaseUrl);
    }

    public static Firebase getInstance() {
        return RootRef.baseRef;
    }

    public static Firebase getComplaintsRoot() {
        return RootRef.baseRef.child("complaints");
    }
    public static Firebase getUsersRoot() {
        return RootRef.baseRef.child("users");
    }
}
