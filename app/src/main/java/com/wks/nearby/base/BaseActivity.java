package com.wks.nearby.base;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by waqqassheikh on 14/10/2017.
 */

public class BaseActivity extends AppCompatActivity {

    protected void setToolbar(Toolbar toolbar) {
        if (toolbar == null) return;
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(70.0f);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}
