import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.util.Log;

public class CallRecordingService extends Service {
    private MediaRecorder recorder;
    private boolean isRecording = false;
    private static final String TAG = "CallRecordingService";

    @Override
    public IBinder onBind(Intent intent) {
        // This service is not designed with binding in mind
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startRecording(); // Start recording the call
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        stopRecording(); // Stop recording the call
        super.onDestroy();
    }

    private void startRecording() {
        if (isRecording) {
            return; // Already recording
        }

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(getRecordingFilePath()); // Define this method to provide a file path
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            recorder.start();
            isRecording = true;
            Log.d(TAG, "Recording started");
        } catch (Exception e) {
            Log.e(TAG, "Recording failed", e);
        }
    }

    private void stopRecording() {
        if (!isRecording) {
            return; // Not recording
        }

        try {
            recorder.stop();
            recorder.reset();
            recorder.release();
            isRecording = false;
            Log.d(TAG, "Recording stopped");
        } catch (RuntimeException stopException) {
            Log.e(TAG, "Recording stop failed", stopException);
        }
    }

    private String getRecordingFilePath() {
        // Define the path where you want to save the recording file
        // This should be a path in external storage directory and unique for each call
    }
}
