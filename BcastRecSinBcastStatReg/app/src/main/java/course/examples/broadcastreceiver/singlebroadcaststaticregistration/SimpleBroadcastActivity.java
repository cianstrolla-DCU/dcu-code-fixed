package course.examples.broadcastreceiver.singlebroadcaststaticregistration;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SimpleBroadcastActivity extends Activity {

    private static final String CUSTOM_INTENT = "course.examples.broadcastreceiver.singlebroadcaststaticregistration.SHOW_TOAST";
    private static final String TAG = "SimpleBroadcastActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Android 15+ draws apps edge to edge: keep the content clear of the system bars
        View root = findViewById(R.id.main_layout);
        int pl = root.getPaddingLeft(), pt = root.getPaddingTop(), pr = root.getPaddingRight(), pb = root.getPaddingBottom();
        root.setOnApplyWindowInsetsListener((v, insets) -> {
            v.setPadding(pl + insets.getSystemWindowInsetLeft(), pt + insets.getSystemWindowInsetTop(),
                pr + insets.getSystemWindowInsetRight(), pb + insets.getSystemWindowInsetBottom());
            return insets;
        });
    }

    public void onClick(@SuppressWarnings("unused") View v) {
        Log.i(TAG, "Broadcast sent");

        Intent intent = new Intent(CUSTOM_INTENT);
        intent.setPackage("course.examples.broadcastreceiver.singlebroadcaststaticregistration");
        sendBroadcast(intent, Manifest.permission.VIBRATE);
    }
}