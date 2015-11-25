package uk.mondosports.plugins.iadbox;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.util.Log;

import com.peanutlabs.plsdk.*;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class iAdBoxPlugin extends CordovaPlugin {

    private static final String LOGTAG = "iAdBoxPlugin";
    private static final String DEFAULT_AFFILIATE_ID = 'futmondo';

    private static final String ACTION_CREATE_USER = "createUser";
    private static final String ACTION_CREATE_SESSION = "createSession";
    private static final String ACTION_SET_SECTIONS = "setSections";
    private static final String ACTION_OPEN_INBOX = "openInbox:";
    private static final String ACTION_OPEN_DEALS = "openDeals";
    private static final String ACTION_OPEN_PROFILE = "openProfile";
    private static final String ACTION_OPEN_NOTIFICATIONS_SETTINGS = "openNotificationsSettings";
    private static final String ACTION_OPEN_MY_PREFERENCES = "openMyPreferences";
    private static final String ACTION_OPEN_ACTION = "openAction";
    private static final String ACTION_GET_SUMMARY_MESSAGES = "getSummaryOfMessages";
    private static final String ACTION_GET_PRODUCTS = "getProducts";
    private static final String ACTION_GET_CATEGORIES = "getCategories";
    private static final String ACTION_SET_CUSTOM_TOAST_COLORS = "setCustomToastColors";
    private static final String ACTION_GET_MESSAGES_COUNT = "getMessagesCount";
    private static final String ACTION_GET_DEALS_COUNT = "getDealsCount";
    private static final String ACTION_SET_PUSH_MESSAGE = "setPushMessage";
    private static final String ACTION_SET_BORDER_COLOR = "setBorderColor";
    private static final String ACTION_SET_TITLE = "setTitle";
    private static final String ACTION_SET_THEME = "setTheme";
    private static final String ACTION_SET_MAX_SHOWS_DAY = "setMaxShowsPerDay";
    private static final String ACTION_SET_APP_OPENING_SHOW = "setAppOpeningsPerShow";
    private static final String ACTION_REPLACE_BUTTON = "replaceButton";
    private static final String ACTION_SET_OPEN_SHARE_ACTION_LISTENER = "setOpenShareActionListener";
    private static final String ACTION_REPLACE_CUSTOM_NETWORK_ERROR_STRING = "replaceCustomNetworkErrorStringResourceId";
               
    private static final String OPT_EXTERNAL_ID = "externalId";
    private static final String OPT_AFFILIATE_ID = "affiliateId";
    private static final String OPT_PUSH_DEVICE_REGISTRADTION_ID = "pushDeviceRegistrationId";

    private String affiliateId = DEFAULT_AFFILIATE_ID;
    private String externalId = "";
    private String pushDeviceRegistrationId = "";

    private PeanutLabsManager plManager;

    protected OnResponseListener onResponseListener = new OnResponseListener() {
        public void onSuccess() {
            Log.w(LOGTAG, 'onResponseListener onSuccess');
        }
        
        public void onFailure(String message) {
            Log.w(LOGTAG, 'onResponseListener onFailure ' + message);
        }
    };

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        PluginResult result = null;
        
        if (ACTION_CREATE_USER.equals(action)) {
            JSONObject options = args.optJSONObject(0);
            result = executeCreateUser(options, callbackContext);
        } else if (ACTION_CREATE_SESSION.equals(action)) {
            JSONObject options = args.optJSONObject(0);
            result = executeCreateSession(options, callbackContext);
        }  else if (ACTION_SET_SECTIONS.equals(action)) {
            JSONObject options = args.optJSONObject(0);
            result = executeSetSections(options, callbackContext);
        }

        if (result != null) callbackContext.sendPluginResult( result );

        return true;
    }

    private PluginResult executeCreateUser(JSONObject options, CallbackContext callbackContext) {
        Log.w(LOGTAG, "executeCreateUser");
        
        this.createUser(options);
        
        callbackContext.success();

        return null;
    }

    private void createUser( JSONObject options ) {
        if (options.has(OPT_AFFILIATE_ID)) {
            this.affiliateId = options.optInt(OPT_AFFILIATE_ID);
        }
        if (options.has(OPT_EXTERNAL_ID)) {
            this.externalId = options.optString(OPT_EXTERNAL_ID);
        }
        if (options.has(OPT_PUSH_DEVICE_REGISTRADTION_ID)) {
            this.pushDeviceRegistrationId = options.optString(OPT_PUSH_DEVICE_REGISTRADTION_ID);
        }
        
        try {
            Context context = cordova.getActivity().getApplicationContext();
            Qustodian.getInstance(context)
                .createUser(context, 
                    this.externalId, 
                    this.affiliateId, 
                    this.pushDeviceRegistrationId, 
                    onResponseListener);
        } catch (RuntimeException e){
            Log.d(LOGTAG, e.getLocalizedMessage());
        }
    }

    private PluginResult executeCreateSession(JSONObject options, CallbackContext callbackContext) {
        Log.w(LOGTAG, "executeCreateSession");
        
        this.createSession(options);
        
        callbackContext.success();

        return null;
    }

    private void createSession( JSONObject options ) {
        if (options.has(OPT_AFFILIATE_ID)) {
            this.affiliateId = options.optInt(OPT_AFFILIATE_ID);
        }
        if (options.has(OPT_EXTERNAL_ID)) {
            this.externalId = options.optString(OPT_EXTERNAL_ID);
        }
        if (options.has(OPT_PUSH_DEVICE_REGISTRADTION_ID)) {
            this.pushDeviceRegistrationId = options.optString(OPT_PUSH_DEVICE_REGISTRADTION_ID);
        }
        
        try {
            Context context = cordova.getActivity().getApplicationContext();
            Qustodian.getInstance(context)
                .createSession(context, 
                    this.externalId, 
                    this.affiliateId, 
                    this.pushDeviceRegistrationId, 
                    onResponseListener);
        } catch (RuntimeException e){
            Log.d(LOGTAG, e.getLocalizedMessage());
        }
    }

    private PluginResult executeSetSections(JSONObject options, CallbackContext callbackContext) {
        Log.w(LOGTAG, "executeCreateSession");
        
        this.setSections(options);
        
        callbackContext.success();

        return null;
    }

    private void setSections( JSONObject options ) {
        boolean showInbox = true;
        boolean showDeals = true;
        boolean showInvite = true;
        boolean showProfie = true;
         
        if (options.has(OPT_AFFILIATE_ID)) {
            this.affiliateId = options.optInt(OPT_AFFILIATE_ID);
        }
        if (options.has(OPT_EXTERNAL_ID)) {
            this.externalId = options.optString(OPT_EXTERNAL_ID);
        }
        if (options.has(OPT_PUSH_DEVICE_REGISTRADTION_ID)) {
            this.pushDeviceRegistrationId = options.optString(OPT_PUSH_DEVICE_REGISTRADTION_ID);
        }
        
        try {
            Context context = cordova.getActivity().getApplicationContext();
            Qustodian.getInstance(context)
                .createSession(context, 
                    this.externalId, 
                    this.affiliateId, 
                    this.pushDeviceRegistrationId, 
                    onResponseListener);
        } catch (RuntimeException e){
            Log.d(LOGTAG, e.getLocalizedMessage());
        }
    }
}