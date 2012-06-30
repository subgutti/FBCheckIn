package com.desimango.checkin;

import org.json.JSONException;
import org.json.JSONObject;

public class UserProfile {
	
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	
	private String mUserName;
	private long mUserId;
	
	public UserProfile (JSONObject jsonObject) {
		try {
			mUserName = jsonObject.getString(TAG_NAME);
			mUserId = jsonObject.getLong(TAG_ID);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getUserName () {
		return mUserName;
	}
	
	public long getUserId () {
		return mUserId;
	}
}
