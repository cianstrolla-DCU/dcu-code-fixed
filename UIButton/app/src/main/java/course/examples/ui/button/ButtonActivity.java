package course.examples.ui.button;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ButtonActivity extends Activity {
    private static int mCount;
    private Button mButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Android 15+ draws apps edge to edge: keep the content clear of the system bars
        View root = findViewById(R.id.root_layout);
        int pl = root.getPaddingLeft(), pt = root.getPaddingTop(), pr = root.getPaddingRight(), pb = root.getPaddingBottom();
        root.setOnApplyWindowInsetsListener((v, insets) -> {
            v.setPadding(pl + insets.getSystemWindowInsetLeft(), pt + insets.getSystemWindowInsetTop(),
                pr + insets.getSystemWindowInsetRight(), pb + insets.getSystemWindowInsetBottom());
            return insets;
        });

        // Get a reference to the Press Me Button
        mButton = findViewById(R.id.button);

        // Reset Button Text if restarting
        if (null != savedInstanceState) {
            mButton.setText(getString(R.string.got_pressed_string, mCount));
        }

        // Set an OnClickListener on this Button
        // Called each time the user clicks the Button
        mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // Maintain a count of user presses
                // Display count as text on the Button
                mButton.setText(getString(R.string.got_pressed_string, ++mCount));

            }
        });
    }
}