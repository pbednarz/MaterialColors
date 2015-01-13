package pl.solaris.materialcolors;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import pl.solaris.materialcolors.model.MaterialPallete;
import pl.solaris.materialcolors.recycler.NavigationDrawerRowAdapter;
import pl.solaris.materialcolors.recycler.RecycleAdapter;
import pl.solaris.materialcolors.utils.Utils;

public class MainActivity extends ActionBarActivity {

    private ArrayList<MaterialPallete> navigationItems;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private RecyclerView mColorsRecycler;
    private RecycleAdapter mColorsAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private int mCurrentSelectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                toolbar.setTitle(R.string.app_name);
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
            mCurrentSelectedPosition = 0;
            selectItem(0);
        }
    }

    private void initMenuDrawer() {
        navigationItems = Utils.getColorsModels(this);
        mDrawerList = (ListView) findViewById(R.id.menu_drawer);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        mDrawerList.setAdapter(new NavigationDrawerRowAdapter(this, navigationItems));
        mDrawerList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mDrawerList.setItemChecked(mCurrentSelectedPosition, true);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        MaterialPallete selectedPallete = navigationItems.get(position);
        if (mDrawerList != null) {
            mDrawerList.setItemChecked(position, true);
//            mDrawerList.setBackgroundColor(selectedPallete.getColorDark());
            if (mDrawerLayout != null) {
                mDrawerLayout.closeDrawer(mDrawerList);
            }
            mColorsAdapter.setColors(selectedPallete.getColors());
            mColorsAdapter.notifyDataSetChanged();
        }
    }

    private void initColors() {
        mColorsRecycler = (RecyclerView) findViewById(R.id.color_list);
        mColorsRecycler.setHasFixedSize(true);
        mColorsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mColorsRecycler.setItemAnimator(new DefaultItemAnimator());
        mColorsAdapter = new RecycleAdapter(navigationItems.get(0).getColors());
        mColorsRecycler.setAdapter(mColorsAdapter);
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
            return true;
        }
        return super.onOptionsItemSelected(item);
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
}
