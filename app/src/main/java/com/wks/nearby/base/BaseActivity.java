package com.wks.nearby.base;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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
            getSupportActionBar().setDisplayHomeAsUpEnabled(getDisplayHomeAsUpEnabled());
            if (getHomeAsUpIndicator() != null) {
                getSupportActionBar().setHomeAsUpIndicator(getHomeAsUpIndicator());
            }
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    protected boolean getDisplayHomeAsUpEnabled() {
        return true;
    }

    @Nullable
    protected Drawable getHomeAsUpIndicator() {
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onHomeOptionItemClicked();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onHomeOptionItemClicked() {
        finish();
    }
}
