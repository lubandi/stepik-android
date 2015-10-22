package org.stepic.droid.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.squareup.otto.Bus;

import org.stepic.droid.R;
import org.stepic.droid.core.IShell;
import org.stepic.droid.store.operations.DatabaseManager;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class FragmentActivityBase extends AppCompatActivity {


    @Inject
    protected DatabaseManager mDbManager;

    @Inject
    protected IShell mShell;

    @Inject
    protected Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.component(this).inject(this);
    }


    protected void hideSoftKeypad() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    // A method to find height of the status bar
    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void finish() {
        super.finish();
        applyTransitionPrev();
    }


    private void applyTransitionPrev() {
        // apply slide transition animation
        overridePendingTransition(R.anim.slide_in_from_start, R.anim.slide_out_to_end);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
