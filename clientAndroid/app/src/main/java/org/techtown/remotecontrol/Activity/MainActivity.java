package org.techtown.remotecontrol.Activity;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.techtown.remotecontrol.Fragment.KeyboardFragment;
import org.techtown.remotecontrol.Fragment.MouseFragment;
import org.techtown.remotecontrol.Fragment.SettingFragment;
import org.techtown.remotecontrol.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    KeyboardFragment keyboardFragment;
    SettingFragment settingFragment;
    MouseFragment mouseFragment;

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingFragment = new SettingFragment();
        keyboardFragment = new KeyboardFragment();
        mouseFragment = new MouseFragment();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(Color.BLACK);
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
      //  setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_home, settingFragment).commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.setting_nav) {
            Fragment fragment = settingFragment;
            fragmentManager.beginTransaction().replace(R.id.content_home, fragment).commit();
        }
        else if (id == R.id.keyboard_nav) {
            Fragment fragment = keyboardFragment;
            fragmentManager.beginTransaction().replace(R.id.content_home, fragment).commit();
        }
        else if (id == R.id.mouse_nav) {
            Fragment fragment = mouseFragment;
            fragmentManager.beginTransaction().replace(R.id.content_home, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
