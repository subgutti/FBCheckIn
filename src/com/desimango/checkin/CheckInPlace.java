package com.desimango.checkin;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckInPlace {

	private static final String TAG_ID="id";
	private static final String TAG_NAME = "name";
	private static final String TAG_LOCATION = "location";
	
	private long placeId;
	private String placeName;
	private Location location;
	
	public CheckInPlace (JSONObject jsonObject) {
		
		try {
			placeId = jsonObject.getLong(TAG_ID);
			placeName = jsonObject.getString(TAG_NAME);
			location = new Location (jsonObject.getJSONObject(TAG_LOCATION));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getPlaceName () {
		return placeName;
	}
	
	public String getLocationString () {
		if(location != null) {
			return location.toString();
		}
		
		return null;
	}
	
	@Override
	public String toString() {
	
		if(placeName != null) {
			return placeName + " in " + location.toString(); 
		}
		
		return super.toString();
	}



	public class Location {
		
		private static final String TAG_CITY="city";
		private static final String TAG_STATE = "state";
		private static final String TAG_COUNTRY = "country";
		private static final String TAG_LATITUDE = "latitude";
		private static final String TAG_LONGITUDE = "longitude";
		
		private String city;
		private String state;
		private String country;
		private float latitude;
		private float longitude;
		
		public Location (JSONObject jsonObject) {
			try {
				city = jsonObject.getString(TAG_CITY);
				state = jsonObject.getString(TAG_STATE);
				country = jsonObject.getString(TAG_COUNTRY);
				latitude = jsonObject.getLong(TAG_LATITUDE);
				longitude = jsonObject.getLong(TAG_LONGITUDE);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public String getCity () {
			return city;
		}
		
		public String getState () {
			return state;
		}

		@Override
		public String toString() {
		
			if(city != null) {
				return getCity() + "," + getState();
			}
				
			return super.toString();
		}
	}
}
