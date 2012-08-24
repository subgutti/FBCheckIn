package com.desimango.checkin;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.desimango.checkin.SessionEvents.AuthListener;
import com.desimango.checkin.SessionEvents.LogoutListener;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.R;

public class CheckinsActivity extends Activity {
	
	private static final String APP_ID = "294389337322518";
    public static final String LOGTAG = CheckinsActivity.class.getSimpleName();
	
    Facebook facebook = new Facebook(APP_ID);
    private SharedPreferences mPrefs;
    
    final static int AUTHORIZE_ACTIVITY_RESULT_CODE = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Instantiate the asynrunner object for asynchronous api calls.
        Utility.mAsyncRunner = new AsyncFacebookRunner(facebook);
        
        /*
         * Get existing access_token if any
         */
        mPrefs = getPreferences(MODE_PRIVATE);
        String access_token = mPrefs.getString("access_token", null);
        long expires = mPrefs.getLong("access_expires", 0);
        if(access_token != null) {
            facebook.setAccessToken(access_token);
        }
        if(expires != 0) {
            facebook.setAccessExpires(expires);
        }
        
        /*
         * Only call authorize if the access_token has expired.
         */
        if(!facebook.isSessionValid()) {

            facebook.authorize(this, new String[] {"read_stream", "user_status", "friends_status","user_checkins", "friends_checkins"}, new DialogListener() {
                @Override
                public void onComplete(Bundle values) {
                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putString("access_token", facebook.getAccessToken());
                    editor.putLong("access_expires", facebook.getAccessExpires());
                    editor.commit();
                    
                    requestCheckInData();
                }
    
                @Override
                public void onFacebookError(FacebookError error) {}
    
                @Override
                public void onError(DialogError e) {}
    
                @Override
                public void onCancel() {}
            });
        }
    }
    
    @Override
	protected void onResume() {
		super.onResume();
	
		requestCheckInData();
		
    }



	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);

    	facebook.authorizeCallback(requestCode, resultCode, data);
    }
    
    /*
     * Request user name, and picture to show on the main screen.
     */
    private void requestCheckInData () {
    	// Construct multiquery that returns checkin, user and place data.
        JSONObject jsonFQL = new JSONObject();
        try {
        	jsonFQL.put("query1", "SELECT checkin_id, author_uid, page_id, coords, timestamp FROM checkin WHERE (author_uid IN (SELECT uid2 FROM friend WHERE uid1 = me()) OR author_uid=me()) ORDER BY timestamp DESC LIMIT 50");
            jsonFQL.put("query2", "SELECT uid, username, first_name, middle_name, last_name, name, pic, sex, about_me FROM user WHERE uid in (SELECT author_uid FROM #query1)");
            jsonFQL.put("query3", "SELECT page_id, name, description, categories, pic, fan_count, website, checkins, location FROM page WHERE page_id IN (SELECT page_id FROM #query1)");	        	
        }
        catch(JSONException ex) {
        	return;
        }

        Bundle params = new Bundle();
        params.putString("method", "fql.multiquery");
        params.putString("queries", jsonFQL.toString());
        
    	/* //Multi-query
    	String query1 = 
    	
    	*/
    	
    	//batched request
//    	String batch = "[ {\"method\":\"GET\",\"relative_url\":\"search?type=checkin\"} ]";
//    	String batch = "[ {\"method\":\"GET\",\"relative_url\":\"me/checkins\"} ]";
//    	Bundle params = new Bundle ();
//    	params.putString("batch", batch);
    	
    	Utility.mAsyncRunner.request(params, new CheckInsRequestListener());
    }
    
    public class FQLRequestListener extends BaseRequestListener {

        @Override
        public void onComplete(final String response, final Object state) {
            JSONParser parser = new JSONParser(CheckinsActivity.this);
            
            parser.parse(response);
        }

        public void onFacebookError(FacebookError error) {
            Log.d(LOGTAG, "failed request checkins data : error");
        }
    }
    
    public class CheckInsRequestListener extends BaseRequestListener {

        @Override
        public void onComplete(final String response, final Object state) {
            //JSONObject jsonObject;
            ////try {
            	
            	CheckinsJsonParser parser = new CheckinsJsonParser(CheckinsActivity.this);
            	parser.parse(response);
                //jsonObject = new JSONObject(response);

                //Log.d(LOGTAG, "checkins data : " + jsonObject.toString());

            //} catch (JSONException e) {
                // TODO Auto-generated catch block
             //   e.printStackTrace();
            //}
        }

    }
    
    /*
     * The Callback for notifying the application when authorization succeeds or
     * fails.
     */

    public class FbAPIsAuthListener implements AuthListener {

        @Override
        public void onAuthSucceed() {
        	requestCheckInData();
        }

        @Override
        public void onAuthFail(String error) {
            Log.d(LOGTAG, "authorization failed ");
        }
    }
    
    /*
     * The Callback for notifying the application when log out starts and
     * finishes.
     */
    public class FbAPIsLogoutListener implements LogoutListener {
        @Override
        public void onLogoutBegin() {
        	Log.d(LOGTAG,"logging out");
        }

        @Override
        public void onLogoutFinish() {
        	Log.d(LOGTAG,"logged out");
        }
    }
    
}