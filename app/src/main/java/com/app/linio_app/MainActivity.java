package com.app.linio_app;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.app.linio_app.Fragments.Home;
import com.app.linio_app.Fragments.Login;
import com.app.linio_app.Fragments.Panels;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigation;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer);
        navigation = findViewById(R.id.navigation);
        navigation.setNavigationItemSelectedListener(this);
        initNavView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawer.isDrawerOpen(navigation)) {
                    drawer.closeDrawers();
                } else drawer.openDrawer(navigation);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (menuItem.getItemId()) {
            case R.id.home:
                transaction.replace(R.id.fragmentContainer,new Home());
                setActionBarTitle("HOME");
                drawer.closeDrawers();
                break;
            case R.id.panels:
                transaction.replace(R.id.fragmentContainer,new Panels());
                setActionBarTitle("PANELS");
                drawer.closeDrawers();
                break;
            case R.id.logout:
                transaction.replace(R.id.fragmentContainer,new Login());
                setActionBarTitle("LINIO");
//                auth.signOut();
                hideNonAuthLinks();
                drawer.closeDrawers();
                break;
        }
        transaction.commit();
        return false;
    }

    private void initNavView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,drawer,0,0);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }
    }

    private void hideNonAuthLinks() {
        Menu menu = navigation.getMenu();
        menu.findItem(R.id.panels).setVisible(false);
    }
}
