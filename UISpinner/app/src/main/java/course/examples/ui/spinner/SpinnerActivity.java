package course.examples.ui.spinner;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SpinnerActivity extends Activity {

    private static boolean wasTouched;

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

        // Indicates whether spinner was touched by user
        wasTouched = false;

        // Get a reference to the Spinner
        Spinner spinner = findViewById(R.id.spinner);

        // Create an Adapter that holds a list of colors
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.colors, R.layout.dropdown_item);

        // Set the Adapter for the spinner
        spinner.setAdapter(adapter);

        // Set an onTouchListener on the spinner because
        // onItemSelected() can be called multiple times by framework
        spinner.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                wasTouched = true;
                v.performClick();
                return false;
            }
        });

        // Set an onItemSelectedListener on the spinner
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                if (wasTouched) {

                    // Display a Toast message indicating the currently selected item
                    Toast.makeText(
                            parent.getContext(),
                            "The color is "
                                    + parent.getItemAtPosition(pos).toString(),
                            Toast.LENGTH_LONG).show();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }
}