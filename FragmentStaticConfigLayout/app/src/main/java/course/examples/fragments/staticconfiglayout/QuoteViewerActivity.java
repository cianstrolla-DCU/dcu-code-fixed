package course.examples.fragments.staticconfiglayout;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

//Several Activity lifecycle methods are instrumented to emit LogCat output
//so you can follow this class' lifecycle
public class QuoteViewerActivity extends Activity implements
        ListSelectionListener {

    private static final String TAG = "QuoteViewerActivity";
    public static String[] mTitleArray, mQuoteArray;
    public static String mNoQuoteSelectedString;
    private QuotesFragment mQuoteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the string arrays with the titles and quotes
        mTitleArray = getResources().getStringArray(R.array.Titles);
        mQuoteArray = getResources().getStringArray(R.array.Quotes);
        mNoQuoteSelectedString = getResources().getString(R.string.noQuoteSelected);

        setContentView(R.layout.main);

        // Android 15+ draws apps edge to edge: keep the content clear of the system bars
        View root = findViewById(R.id.main);
        int pl = root.getPaddingLeft(), pt = root.getPaddingTop(), pr = root.getPaddingRight(), pb = root.getPaddingBottom();
        root.setOnApplyWindowInsetsListener((v, insets) -> {
            v.setPadding(pl + insets.getSystemWindowInsetLeft(), pt + insets.getSystemWindowInsetTop(),
                    pr + insets.getSystemWindowInsetRight(), pb + insets.getSystemWindowInsetBottom());
            return insets;
        });

        // Get a reference to the QuotesFragment
        mQuoteFragment = (QuotesFragment) getFragmentManager()
                .findFragmentById(R.id.details);
    }

    // Called when the user selects an item in the TitlesFragment
    @Override
    public void onListSelection(int index) {

        // Tell the QuoteFragment to show the quote string at position index
        mQuoteFragment.showQuoteAtIndex(index);

    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onRestart()");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
        super.onStop();
    }

}