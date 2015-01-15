package pl.solaris.materialcolors;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.internal.widget.TintImageView;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.solaris.materialcolors.adapter.ColorListAdapter;
import pl.solaris.materialcolors.adapter.NavigationDrawerAdapter;
import pl.solaris.materialcolors.model.MaterialPallete;
import pl.solaris.materialcolors.utils.Utils;
import pl.solaris.materialcolors.widget.MaterialDialog;

public class MainActivity extends ActionBarActivity {

    private final String CURRENT_POSITION = "position";

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.toolbar_divider)
    View toolbarDivider;

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @InjectView(R.id.menu_drawer)
    ListView mDrawerList;

    @InjectView(R.id.color_list)
    ListView mColorsRecycler;

    private ArrayList<MaterialPallete> navigationItems;
    private ColorListAdapter mColorsAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private int mCurrentSelectedPosition = -1;
    private MaterialPallete selectedPallete;
    private int prevColorPrimary = 0;
    private int prevColorPrimaryDark = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            public void onDrawerClosed(View view) {
                if (selectedPallete != null) {
                    toolbar.setTitle(selectedPallete.getTitle());
                }
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                toolbar.setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        initMenuDrawer();
        initColors();
        if (savedInstanceState == null) {
            selectItem(0);
        } else {
            selectItem(savedInstanceState.getInt(CURRENT_POSITION, 0));
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //fixing toolbar icons after changing theme
        //should be fixed in next support release
        Toolbar t = (Toolbar) findViewById(R.id.toolbar);
        for (int i = 0; i < t.getChildCount(); i++) {
            if (t.getChildAt(i) instanceof ActionMenuView) {
                ActionMenuView v = (ActionMenuView) t.getChildAt(i);
                for (int j = 0; j < v.getChildCount(); j++) {
                    if (v.getChildAt(j) instanceof TintImageView) {
                        TintImageView v1 = (TintImageView) v.getChildAt(j);
                        v1.setImageResource(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha);
                    }
                }
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void initMenuDrawer() {
        navigationItems = Utils.getColorsModels(this);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        mDrawerList.setAdapter(new NavigationDrawerAdapter(this, navigationItems));
        mDrawerList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mDrawerList.setItemChecked(0, true);
    }

    private void initColors() {
        mColorsAdapter = new ColorListAdapter(this, R.layout.list_item_color, navigationItems.get(0).getColors(), navigationItems.get(0).getColorDark());
        mColorsRecycler.setAdapter(mColorsAdapter);
    }

    private void selectItem(int position) {
        if (mCurrentSelectedPosition != position) {
            if (selectedPallete != null) {
                prevColorPrimary = selectedPallete.getColor();
                prevColorPrimaryDark = selectedPallete.getColorDark();
            }

            mCurrentSelectedPosition = position;
            selectedPallete = navigationItems.get(position);
            if (mDrawerList != null && toolbar != null && toolbarDivider != null) {
                mDrawerList.setItemChecked(position, true);
                toolbar.setTitle(selectedPallete.getTitle());
                mColorsAdapter.setColors(selectedPallete.getColors(), selectedPallete.getColorDark());
                mColorsAdapter.resetPosition();
                mColorsAdapter.notifyDataSetChanged();
                if (prevColorPrimary != 0 && prevColorPrimaryDark != 0) {
                    animBackgroundColor(prevColorPrimaryDark, selectedPallete.getColorDark(), mDrawerList);
                    animBackgroundColor(prevColorPrimaryDark, selectedPallete.getColorDark(), toolbarDivider);
                    animBackgroundColor(prevColorPrimary, selectedPallete.getColor(), toolbar);
                } else {
                    mDrawerList.setBackgroundColor(selectedPallete.getColorDark());
                    toolbarDivider.setBackgroundColor(selectedPallete.getColorDark());
                    toolbar.setBackgroundColor(selectedPallete.getColor());
                }
            }

            //change status bar color
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(selectedPallete.getColorDark());
            }
        }
    }

    public void animBackgroundColor(int colorFrom, int colorTo, final View targetView) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                targetView.setBackgroundColor((Integer) animator.getAnimatedValue());
            }

        });
        colorAnimation.setDuration(800);
        colorAnimation.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            final MaterialDialog mMaterialDialog = new MaterialDialog(this);
            mMaterialDialog.setTitle(R.string.app_name);
            mMaterialDialog.setMessage(Html.fromHtml(getString(R.string.app_description)));
            mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialDialog.dismiss();
                }
            });
            mMaterialDialog.show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_POSITION, mCurrentSelectedPosition);
        super.onSaveInstanceState(outState);
    }
}
