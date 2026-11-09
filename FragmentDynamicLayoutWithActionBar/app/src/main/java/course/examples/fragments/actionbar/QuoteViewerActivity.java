package course.examples.fragments.actionbar;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import android.view.View;

public class QuoteViewerActivity extends Activity implements ListSelectionListener {

    @SuppressWarnings("unused")
    private static final String TAG = "QuoteViewerActivity";
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private QuotesFragment mQuoteFragment;
    private TitlesFragment mTitleFragment;
    private FragmentManager mFragmentManager;
    private FrameLayout mTitleFrameLayout, mQuotesFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        // Android 15+ draws apps edge to edge: keep the content clear of the system bars
        View root = findViewById(R.id.main);
        int pl = root.getPaddingLeft(), pt = root.getPaddingTop(), pr = root.getPaddingRight(), pb = root.getPaddingBottom();
        root.setOnApplyWindowInsetsListener((v, insets) -> {
            v.setPadding(pl + insets.getSystemWindowInsetLeft(), pt + insets.getSystemWindowInsetTop(),
                    pr + insets.getSystemWindowInsetRight(), pb + insets.getSystemWindowInsetBottom());
            return insets;
        });

        // Get references to the TitleFragment and to the QuotesFragment containers
        mTitleFrameLayout = findViewById(R.id.title_fragment_container);
        mQuotesFrameLayout = findViewById(R.id.quote_fragment_container);


        // Get a reference to the FragmentManager
        mFragmentManager = getFragmentManager();

        mQuoteFragment = (QuotesFragment) mFragmentManager.findFragmentById(R.id.quote_fragment_container);
        mTitleFragment = (TitlesFragment) mFragmentManager.findFragmentById(R.id.title_fragment_container);


        if (null == mFragmentManager.findFragmentById(R.id.title_fragment_container)) {

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            mTitleFragment = new TitlesFragment();

            // Add the TitleFragment to the layout
            fragmentTransaction.add(R.id.title_fragment_container,
                    mTitleFragment);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();
        }

        // Set layout parameters
        setLayout();


        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        mFragmentManager
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        layoutAfterBackStackedChanged();
                    }
                });

        // Android 16 (predictive back) no longer pops a framework Fragment back stack when the
        // user presses Back, so pop it here whenever the back stack is not empty
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            OnBackInvokedCallback popBackStack = () -> mFragmentManager.popBackStackImmediate();
            FragmentManager.OnBackStackChangedListener updateBackCallback = () -> {
                if (mFragmentManager.getBackStackEntryCount() > 0) {
                    getOnBackInvokedDispatcher().registerOnBackInvokedCallback(
                            OnBackInvokedDispatcher.PRIORITY_DEFAULT, popBackStack);
                } else {
                    getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(popBackStack);
                }
            };
            mFragmentManager.addOnBackStackChangedListener(updateBackCallback);
            updateBackCallback.onBackStackChanged();
        }
    }

    private void layoutAfterBackStackedChanged() {

        // If backed up to single pane display uncheck any checked titles
        if (null == mFragmentManager.findFragmentById(R.id.quote_fragment_container)) {
            mTitleFragment.unCheckSelection();
        }

        // Set layout parameters
        setLayout();
    }

    private void setLayout() {

        // Determine whether the QuoteFragment has been created
        if (null == mFragmentManager.findFragmentById(R.id.quote_fragment_container)) {

            // Make the TitleFragment occupy the entire layout
            mTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));
            mQuotesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));
        } else {

            // Make the TitleLayout take 1/3 of the layout's width
            mTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 1f));

            // Make the QuoteLayout take 2/3's of the layout's width
            mQuotesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 2f));
        }
    }

    // Called when the user selects an item in the TitlesFragment
    @Override
    public void onListSelection(int index) {

        // If the QuoteFragment has not been created, create and add it now
        if (null == mFragmentManager.findFragmentById(R.id.quote_fragment_container)) {

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            mQuoteFragment = new QuotesFragment();

            // Add the QuoteFragment to the layout
            fragmentTransaction.add(R.id.quote_fragment_container,
                    mQuoteFragment);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
        }

        // Tell the QuoteFragment to show the quote string at position index
        mQuoteFragment.showQuoteAtIndex(index);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Get a reference to the MenuInflater
        MenuInflater inflater = getMenuInflater();

        // Inflate the menu using activity_menu.xml
        inflater.inflate(R.menu.activity_menu, menu);

        // Return true to display the menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.activity_menu_item) {

            // Show a Toast Message. Toast Messages are discussed in the lesson on user interface classes
            Toast.makeText(getApplicationContext(),
                    "This action provided by the QuoteViewerActivity", Toast.LENGTH_SHORT)
                    .show();

            // return value true indicates that the menu click has been handled
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}