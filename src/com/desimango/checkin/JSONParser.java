package com.desimango.checkin;

import java.security.Timestamp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class JSONParser {

	private static final String LOGTAG = JSONParser.class.getSimpleName();
	
	// FB checkin tablet node names
    private static final String TAG_AUTHOR = "author_uid";
    private static final String TAG_PAGE = "page_id";
    private static final String TAG_CHECKIN = "checkin_id";
    private static final String TAG_APP = "app_id";
    private static final String TAG_POST = "post_id";
    private static final String TAG_COORDS = "coords";
    private static final String TAG_COORDS_LONG = "longitude";
    private static final String TAG_COORDS_LAT = "latitude";
    private static final String TAG_TIMESTAMP = "timestamp";
    private static final String TAG_TAGGED_IDS = "tagged_uids";
    private static final String TAG_MESSAGE = "message";
	
	private Context mContext;
	
	public JSONParser (Context context) {
		mContext = context;
	}
	
	public void parse (String response) {
		/*
         * Output can be a JSONArray or a JSONObject.
         * Try JSONArray and if there's a JSONException, parse to JSONObject
         */
        try {
            JSONArray json = new JSONArray(response);
            parseJSONArray(json);
        } catch (JSONException e) {
            try {
                /*
                 * JSONObject probably indicates there was some error
                 * Display that error, but for end user you should parse the
                 * error and show appropriate message
                 */
                JSONObject json = new JSONObject(response);
                parseJSONObject(json);
            } catch (JSONException e1) {
                Log.d(LOGTAG, "failed request checkins data : ");
            }
        }
	}
	
	private void parseJSONArray (JSONArray checkIns) {
		
		if(checkIns == null)
			return;
		
		try {
			for(int i=0; i < checkIns.length(); i++) {
				JSONObject checkIn = checkIns.getJSONObject(i);
				
				NotUsedCheckInPostItem postItem = new NotUsedCheckInPostItem();
				
				postItem.setCheckInId(checkIn.getLong(TAG_CHECKIN));
				Log.d(LOGTAG,"TEST ::: checkin post item id : " + checkIn.getLong(TAG_CHECKIN));
				postItem.setAuthorId(checkIn.getInt(TAG_AUTHOR));
				postItem.setPageId(checkIn.getInt(TAG_PAGE));
				postItem.setAppId(checkIn.getInt(TAG_APP));
				postItem.setPostId(checkIn.getString(TAG_POST));
				postItem.setTaggedIds(checkIn.getJSONArray(TAG_TAGGED_IDS));
				
				JSONObject coordObject = checkIn.getJSONObject(TAG_COORDS);
				postItem.setCoords(coordObject.getDouble(TAG_COORDS_LONG), coordObject.getDouble(TAG_COORDS_LAT));
				
				postItem.setTimeStamp(checkIn.getInt(TAG_TIMESTAMP));
				postItem.setMessage(checkIn.getString(TAG_MESSAGE));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	private void parseJSONObject (JSONObject json) {
		
	}
}
