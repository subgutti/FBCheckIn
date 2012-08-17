package com.desimango.checkin;

import java.util.ArrayList;
import java.util.Hashtable;

import android.app.Application;
import android.net.http.AndroidHttpClient;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;

public class Utility extends Application {

	public static Facebook mFacebook;
    public static AsyncFacebookRunner mAsyncRunner;
    
    public static String userUID = null;
    public static String objectID = null;
    public static AndroidHttpClient httpclient = null;
    public static Hashtable<String, String> currentPermissions = new Hashtable<String, String>();
    
    public static ArrayList<CheckInPostItem> mCheckInsArrayList = new ArrayList();

}
