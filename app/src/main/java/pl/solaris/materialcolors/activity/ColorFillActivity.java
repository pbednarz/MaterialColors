package pl.solaris.materialcolors.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.solaris.materialcolors.R;
import pl.solaris.materialcolors.utils.ColorUtils;
import pl.solaris.materialcolors.utils.Utils;
import pl.solaris.materialcolors.widget.RippleViewFrameLayout;

/**
 * Created by solaris on 2015-01-05.
 */
public class ColorFillActivity extends ActionBarActivity implements RippleViewFrameLayout.animationEndListener {
    public static final String ARG_DRAWING_START_LOCATION_X = "arg_drawing_start_location_x";
    public static final String ARG_DRAWING_START_LOCATION_Y = "arg_drawing_start_location_y";
    public static final String COLOR_PRIMARY = "color_primary";
    public static final String COLOR_PRIMARY_DARK = "color_primary_dark";
    public static final String COLOR_NAME = "color_name";
    private static final int ANIM_DURATION_TOOLBAR = 300;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.color_rl)
    RippleViewFrameLayout colorBg;

    private int drawingStartLocationX;
    private int drawingStartLocationY;
    private int colorPrimary;
    private int colorPrimaryDark;
    private String colorName;

    public static void startActivity(Activity activity, View viewFrom, int colorPrimary, int colorPrimaryDark, String name) {
        final Intent intent = new Intent(activity, ColorFillActivity.class);
        if (viewFrom != null) {
            int[] startingLocation = new int[2];
            viewFrom.getLocationOnScreen(startingLocation);
            intent.putExtra(ARG_DRAWING_START_LOCATION_Y, startingLocation[1]);
            intent.putExtra(ARG_DRAWING_START_LOCATION_X, startingLocation[0]);
        }
        intent.putExtra(COLOR_PRIMARY, colorPrimary);
        intent.putExtra(COLOR_PRIMARY_DARK, colorPrimaryDark);
        intent.putExtra(COLOR_NAME, name);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        colorPrimary = getIntent().getIntExtra(COLOR_PRIMARY, getResources().getColor(R.color.primary));
        colorPrimaryDark = getIntent().getIntExtra(COLOR_PRIMARY_DARK, getResources().getColor(R.color.primary_dark));
        if (ColorUtils.isColorWhitish(colorPrimary)) {
            setTheme(R.style.AppTheme_Light_ColorFillActivity);
        }
        setContentView(R.layout.activity_color_fill);
        ButterKnife.inject(this);


        drawingStartLocationX = getIntent().getIntExtra(ARG_DRAWING_START_LOCATION_X, 0);
        drawingStartLocationY = getIntent().getIntExtra(ARG_DRAWING_START_LOCATION_Y, 0);
        if (getIntent().getStringExtra(COLOR_NAME) != null) {
            colorName = getIntent().getStringExtra(COLOR_NAME);
        } else {
            colorName = getString(R.string.indigo);
        }
        initToolbar();

        if (savedInstanceState == null) {
            colorBg.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    colorBg.getViewTreeObserver().removeOnPreDrawListener(this);
                    startIntroAnimation();
                    return true;
                }
            });
        } else {
            colorBg.setBackgroundColor(colorPrimary);
        }
    }

    private void initToolbar() {
        toolbar.setTitle(colorName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //change status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }
    }

    private void startIntroAnimation() {
        int actionbarSize = getResources().getDimensionPixelSize(R.dimen.toolbar_main_height);
        toolbar.setTranslationY(-actionbarSize);
        colorBg.startAnimation(drawingStartLocationX, drawingStartLocationY, colorPrimary, this);
    }

    @Override
    public void onBackPressed() {
        colorBg.animate()
                .translationY(Utils.getScreenHeight(this))
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ColorFillActivity.super.onBackPressed();
                        overridePendingTransition(0, 0);
                    }
                })
                .start();
    }

    @Override
    public void onAnimationEnd() {
        toolbar.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
