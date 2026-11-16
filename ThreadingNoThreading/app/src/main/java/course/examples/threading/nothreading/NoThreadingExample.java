package course.examples.threading.nothreading;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class NoThreadingExample extends Activity {
    private ImageView mIView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        mIView = findViewById(R.id.imageView);
    }

    public void onClickOtherButton(@SuppressWarnings("unused") View v) {
        Toast.makeText(NoThreadingExample.this, "I'm Working",
                Toast.LENGTH_SHORT).show();
    }


    public void onClickLoadButton(@SuppressWarnings("unused") View view) {
        try {
            // Accentuates slow operation
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mIView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.painter));
    }
}