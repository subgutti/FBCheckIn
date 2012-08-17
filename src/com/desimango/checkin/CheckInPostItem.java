package com.desimango.checkin;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class CheckInPostItem {
	
	private static final String LOG_TAG = CheckInPostItem.class.getSimpleName();
	
	private static final String TAG_CHECKIN_ID = "id";
	private static final String TAG_FROM = "from";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_PLACE = "place";
	private static final String TAG_CREATED_TIME = "created_time";
	
	private long mCheckInId;
	private UserProfile mFromUser;
	private String mMessage;
	private CheckInPlace mCheckInPlace;
	private String mCreatedTime;
	
	public CheckInPostItem (JSONObject jsonObject) {
		try {
			
			mCheckInId = jsonObject.getLong(TAG_CHECKIN_ID);
			mMessage = jsonObject.getString(TAG_MESSAGE);
			Log.d(LOG_TAG, "Message parsed : " + mMessage);
			mCreatedTime = jsonObject.getString(TAG_CREATED_TIME);

			mFromUser = new UserProfile(jsonObject.getJSONObject(TAG_FROM));
			mCheckInPlace = new CheckInPlace(jsonObject.getJSONObject(TAG_PLACE));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		
		if(mCheckInPlace != null) {
			return mMessage + "at" + mCheckInPlace.toString();
		}
		
		return super.toString();
	}
	
	
	
}
