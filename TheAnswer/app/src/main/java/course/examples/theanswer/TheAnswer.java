package course.examples.theanswer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class TheAnswer extends Activity {

	private static final int[] answers = { 42, -10, 0, 100, 1000 };
	private static final int answer = 42;
	private static String TAG = "TheAnswer";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// Required call through to Activity.onCreate()
		// Restore any saved instance state
		super.onCreate(savedInstanceState);

		// Set up the application's user interface (content view)
		setContentView(R.layout.answer_layout);

		// Android 15+ draws apps edge to edge: keep the content clear of the system bars
		View root = findViewById(R.id.root_layout);
		int pl = root.getPaddingLeft(), pt = root.getPaddingTop(), pr = root.getPaddingRight(), pb = root.getPaddingBottom();
		root.setOnApplyWindowInsetsListener((v, insets) -> {
			v.setPadding(pl + insets.getSystemWindowInsetLeft(), pt + insets.getSystemWindowInsetTop(),
				pr + insets.getSystemWindowInsetRight(), pb + insets.getSystemWindowInsetBottom());
			return insets;
		});

		// Get a reference to a TextView in the content view
		TextView answerView = findViewById(R.id.answer_view);

		int val = findAnswer();
		String output = (val == answer) ? String.valueOf(answer) : getString(R.string.never_know_string);
		
		// Set desired text in answerView TextView
		answerView
				.setText(output);
	}

	private int findAnswer() {
		Log.d(TAG,"Entering findAnswer()");
		for (int val : answers) {
			if (val == answer)
				return val;
		}
		Log.e(TAG, "Unexpected behavior");
		return -1;
	}
}
