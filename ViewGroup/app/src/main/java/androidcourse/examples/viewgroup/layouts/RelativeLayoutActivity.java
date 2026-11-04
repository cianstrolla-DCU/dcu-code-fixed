package androidcourse.examples.viewgroup.layouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidcourse.example.viewgroup.layouts.R;

public class RelativeLayoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.relative_layout);

        // Android 15+ draws apps edge to edge: keep the content clear of the system bars
        View root = findViewById(R.id.relativeLayoutRoot);
        int pl = root.getPaddingLeft(), pt = root.getPaddingTop(), pr = root.getPaddingRight(), pb = root.getPaddingBottom();
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(pl + bars.left, pt + bars.top, pr + bars.right, pb + bars.bottom);
            return insets;
        });
    }

    public void onGoBackClicked(View v) {

        Intent intent = new Intent(RelativeLayoutActivity.this,
                MainLayoutActivity.class);
        startActivity(intent);
    }

    public void onClickDcu(View v) {

        Toast.makeText(this, getString(R.string.ui_dcu_message),
                Toast.LENGTH_LONG).show();

        }

}
