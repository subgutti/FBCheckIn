package com.desimango.checkin;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

public class NotUsedCheckInPostItem {
	
	private int mAuthorId;
	private int mPageId;
	private long mCheckInId;
	private int mAppId;
	private String mPostId;
	
	private ArrayList<Integer> mTaggedIds = new ArrayList<Integer>();

	private double[] mCoords = new double[2];
	private int mTimestamp;
	private String mMessage;
	
	public NotUsedCheckInPostItem () {
		
	}
	
	public void setAuthorId (int id) {
		mAuthorId = id;
	}
	
	public void setPageId (int id) {
		mPageId = id;
	}
	
	public void setCheckInId (long id) {
		mCheckInId = id;
		
		requestCheckInInfo ();
	}

	public void setAppId (int id) {
		mAppId = id;
	}

	public void setPostId (String id) {
		mPostId = id;
	}
	
	public void setTaggedIds (JSONArray jsonArray) {
		if (jsonArray != null) { 
		   int len = jsonArray.length();
		   for (int i=0;i<len;i++){ 
			   try {
				   mTaggedIds.add(jsonArray.getInt(i));
			   } catch (JSONException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
				   continue;
			   }
		   }
		} 
	}
	
	public void setCoords (double longitude, double latitude) {
		mCoords[0] = longitude;
		mCoords[1] = latitude;
	}
	
	public void setTimeStamp (int timestamp) {
		mTimestamp = timestamp;
	}
	
	public void setMessage (String message) {
		mMessage = message;
	}
	
	private void requestCheckInInfo ()	{
		Bundle params = new Bundle();
        params.putString("fields", "from");
        Utility.mAsyncRunner.request(""+mCheckInId, params, new CheckInRequestListener());
    }
	
	/*
     * Callback for fetching current user's name, picture, uid.
     */
    public class CheckInRequestListener extends BaseRequestListener {

        @Override
        public void onComplete(final String response, final Object state) {
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(response);

                Log.d("##### TEST #####","request listener " + jsonObject.toString());
                //final String picURL = jsonObject.getString("picture");
                //final String name = jsonObject.getString("name");
                //Utility.userUID = jsonObject.getString("id");

                /*mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mText.setText("Welcome " + name + "!");
                        mUserPic.setImageBitmap(Utility.getBitmap(picURL));
                    }
                });*/

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
	
}
