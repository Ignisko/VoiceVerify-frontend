import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallReceiver extends BroadcastReceiver {

    private static final String TAG = "CallReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (state != null) {
                switch (state) {
                    case TelephonyManager.EXTRA_STATE_RINGING:
                        String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                        Log.d(TAG, "Incoming call from: " + incomingNumber);
                        // Perform actions for incoming call (e.g., record call, analyze for spam)
                        break;
                    case TelephonyManager.EXTRA_STATE_OFFHOOK:
                        Log.d(TAG, "Call answered");
                        // Perform actions when call is answered (e.g., start recording)
                        break;
                    case TelephonyManager.EXTRA_STATE_IDLE:
                        Log.d(TAG, "Call ended");
                        // Perform actions when call ends (e.g., stop recording, analyze call data)
                        break;
                }
            }
        }
    }
}
