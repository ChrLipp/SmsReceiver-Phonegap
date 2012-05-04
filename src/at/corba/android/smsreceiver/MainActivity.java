package at.corba.android.smsreceiver;

import android.os.Bundle;

import org.apache.cordova.DroidGap;

public class MainActivity extends DroidGap
{
	/** Logger-Tag. */
    @SuppressWarnings("hiding")
    public static final String TAG = "MainActivity";

	/**
	 *  Called when the activity is first created.
	 */
	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

    	super.loadUrl("file:///android_asset/www/index.html", 1);
	}
}
