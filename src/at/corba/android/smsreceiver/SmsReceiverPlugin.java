package at.corba.android.smsreceiver;

import android.telephony.SmsMessage;
import android.util.Log;

import org.apache.cordova.api.PluginResult;
import org.apache.cordova.api.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.phonegap.api.Plugin;

/**
 * Apache Cordova Plugin for forwarding SMS to JS-code.
 * @author ChrLipp
 */
public class SmsReceiverPlugin extends Plugin
{
	/** Logger-Tag. */
    public static final String TAG = "SmsReceiverPlugin";

    /** Constant for action REGISTER */
    public static final String REGISTER="register";

    /** Constant for action UNREGISTER */
	public static final String UNREGISTER="unregister";

	/** Currently active plugin instance */
	public static Plugin currentPluginInstance;

	/** save Callbackfunction-Name for later use */
	private static String callbackFunction;

	@Override
    public PluginResult execute(final String action, final JSONArray data, final String callbackId)
    {
		Log.v(TAG + ":execute", "action=" + action);

		PluginResult result = null;

		if (REGISTER.equals(action))
		{
			Log.v(TAG + ":execute", "data=" + data.toString());

			try
			{
				JSONObject json = data.getJSONObject(0);
				callbackFunction = (String) json.get("callback");
				currentPluginInstance = this;
				result = new PluginResult(Status.OK);
			}
			catch (JSONException e)
			{
				Log.e(TAG, "Got JSON Exception " + e.getMessage());
				result = new PluginResult(Status.JSON_EXCEPTION);
			}
		}
		else if (UNREGISTER.equals(action))
		{
			currentPluginInstance = null;
			result = new PluginResult(Status.OK);
		}
		else
		{
			Log.e(TAG, "Invalid action : " + action);
			result = new PluginResult(Status.INVALID_ACTION);
		}

		return result;
	}

	/**
	 * Static function to send a SMS to JS.
	 * @param json
	 */
	public static void sendMessage(final SmsMessage msg)
	{
		// When the Activity is not loaded, the currentPluginInstance is null
		if (currentPluginInstance != null)
		{
			// build JSON message
			JSONObject json = new JSONObject();
			try
			{
				json.put("origin", msg.getOriginatingAddress());
				json.put("body", msg.getMessageBody());
			}
			catch (JSONException e)
			{
		 	   	Log.e(TAG + ":sendMessage", "JSON exception");
			}

			// build code to call function
			String code =  "javascript:" + callbackFunction + "(" + json.toString() + ");";
	 	   	Log.v(TAG + ":sendJavascript", code);

	 	   	// execute code
	 	   	currentPluginInstance.sendJavascript(code);
		}
	}
}