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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.techtown.SocketLibrary;
import org.techtown.remotecontrol.Fragment.KeyboardFragment;
import org.techtown.remotecontrol.Fragment.MouseFragment;
import org.techtown.remotecontrol.Fragment.SettingFragment;
import org.techtown.remotecontrol.KeyboardItem;
import org.techtown.remotecontrol.Myapplication;
import org.techtown.remotecontrol.R;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    KeyboardFragment keyboardFragment;
    SettingFragment settingFragment;
    MouseFragment mouseFragment;

    private static final String TAG = "MainActivity";

    ArrayList<KeyboardItem> keyboardItemList = new ArrayList<>();
    int currentMenuId=R.id.setting_nav;//처음 선택되어있는 메뉴 아이디는 setting메뉴
    DrawerLayout drawer;

    SocketLibrary socketLibrary;

    Myapplication myapplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myapplication = (Myapplication) getApplication();
        BasicKeyListSetting();//키리스트 세팅

        socketLibrary = new SocketLibrary(myapplication);

        settingFragment = new SettingFragment();
        keyboardFragment = new KeyboardFragment();
        mouseFragment = new MouseFragment();

        //전역 변수로 설정
        myapplication.setSettingFragment(settingFragment);
        myapplication.setKeyboardFragment(keyboardFragment);
        myapplication.setMouseFragment(mouseFragment);

        ImageButton autonew_btn = (ImageButton) findViewById(R.id.autonew_btn);//새로고침 버튼
        autonew_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                socketLibrary.reconnect(getApplicationContext(),MainActivity.this);
            }
        });


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
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
        socketLibrary.disconnect(getApplicationContext(),this);
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
        currentMenuId = id;
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


    public void BasicKeyListSetting()//기본 키리스트 세팅
    {
        ArrayList<Integer> keycodes = new ArrayList<>();
        ArrayList<String> keyNames = new ArrayList<>();
        keycodes.add(27);//esc
        keyNames.add("Esc");
        keycodes.add(9);//tab
        keyNames.add("Tab");
        keycodes.add(20);//caps lock
        keyNames.add("Caps Lock");
        keycodes.add(16);//shift
        keyNames.add("Shift");
        keycodes.add(17);//cltr
        keyNames.add("Control");
        keycodes.add(524);//window
        keyNames.add("Window");
        keycodes.add(18);//alt
        keyNames.add("Alt");
        keycodes.add(32);//space
        keyNames.add("Space");
        keycodes.add(155);//insert
        keyNames.add("Insert");
        keycodes.add(36);//home
        keyNames.add("Home");
        keycodes.add(33);//page up
        keyNames.add("Page Up");
        keycodes.add(34);//page down
        keyNames.add("Page Down");
        keycodes.add(154);//print screen
        keyNames.add("Print Screen");
        keycodes.add(145);//scroll lock
        keyNames.add("Scroll Lock");
        keycodes.add(19);//pause
        keyNames.add("Pause");
        keycodes.add(10);//enter
        keyNames.add("Enter");
        keycodes.add(144);//num lock
        keyNames.add("Num Lock");
        keycodes.add(44);// comma ','
        keyNames.add(",");
        keycodes.add(46);// period '.'
        keyNames.add(".");
        keycodes.add(47);//slash '/'
        keyNames.add("/");
        keycodes.add(61);// equals '='
        keyNames.add("=");
        keycodes.add(45);//minus '-'
        keyNames.add("-");
        keycodes.add(521);//plus '+'
        keyNames.add("+");
        keycodes.add(92);//backslash '\'
        keyNames.add("Back Slash");
        keycodes.add(59);// semi colon ';'
        keyNames.add(";");
        keycodes.add(91);//open bricket '['
        keyNames.add("[");
        keycodes.add(93);//close bricket ']'
        keyNames.add("]");
        keycodes.add(8);//back space
        keyNames.add("Back Space");

        for(int i=0;i<keycodes.size();i++)//특수키
        {
            int keycode = keycodes.get(i);//alt
            KeyboardItem keyboardItem = new KeyboardItem(String.format("%s",keycode));
            ArrayList<String> btn_list = new ArrayList<>();
            btn_list.add(keyNames.get(i));
            ArrayList<Integer> keycode_list = new ArrayList<>();
            keycode_list.add(keycode);
            keyboardItem.setButton_list(btn_list);
            keyboardItem.setKeycode_list(keycode_list);
            keyboardItem.setTAG("Special Key");//특수키
            keyboardItemList.add(keyboardItem);
        }

        for(int i=37;i<=40;i++)//키보드 : 방향키
        {
            KeyboardItem keyboardItem = new KeyboardItem(String.format("%s",i));
            ArrayList<String> btn_list = new ArrayList<>();
            if(i==37)//left
                btn_list.add("←");
            else if(i==38)//up
                btn_list.add("↑");
            else if(i==39)//right
                btn_list.add("→");
            else if(i==40)//down
                btn_list.add("↓");
            ArrayList<Integer> keycode_list = new ArrayList<>();
            keycode_list.add(i);
            keyboardItem.setButton_list(btn_list);
            keyboardItem.setKeycode_list(keycode_list);
            keyboardItem.setTAG("Direction key");//방향키
            keyboardItemList.add(keyboardItem);
        }
        for(int i=48;i<=57;i++)//키보드 : 0~9
        {
            KeyboardItem keyboardItem = new KeyboardItem(String.format("%s",i));
            ArrayList<String> btn_list = new ArrayList<>();
            btn_list.add(String.valueOf(i-48));
            ArrayList<Integer> keycode_list = new ArrayList<>();
            keycode_list.add(i);
            keyboardItem.setButton_list(btn_list);
            keyboardItem.setKeycode_list(keycode_list);
            keyboardItem.setTAG("Number");//숫자
            keyboardItemList.add(keyboardItem);
        }
        for(int i=96;i<=105;i++)//키보드 : 숫자패드0~9
        {
            KeyboardItem keyboardItem = new KeyboardItem(String.format("%s",i));
            ArrayList<String> btn_list = new ArrayList<>();
            btn_list.add("숫자패드"+String.valueOf(i-96));
            ArrayList<Integer> keycode_list = new ArrayList<>();
            keycode_list.add(i);
            keyboardItem.setButton_list(btn_list);
            keyboardItem.setKeycode_list(keycode_list);
            keyboardItem.setTAG("Number Pad");//숫자패드
            keyboardItemList.add(keyboardItem);
        }
        for(int i=65;i<=90;i++)//키보드 A~Z
        {
            KeyboardItem keyboardItem = new KeyboardItem(String.format("%s",i));
            ArrayList<String> btn_list = new ArrayList<>();
            btn_list.add(String.format("%s",i));
            ArrayList<Integer> keycode_list = new ArrayList<>();
            keycode_list.add(i);
            keyboardItem.setButton_list(btn_list);
            keyboardItem.setKeycode_list(keycode_list);
            keyboardItem.setTAG("Alphabet");
            keyboardItemList.add(keyboardItem);
        }
        for(int i=112;i<=123;i++)//키보드 F1~F12
        {
            KeyboardItem keyboardItem = new KeyboardItem(String.format("%s",i));
            ArrayList<String> btn_list = new ArrayList<>();
            btn_list.add("F"+String.valueOf(i-111));
            ArrayList<Integer> keycode_list = new ArrayList<>();
            keycode_list.add(i);
            keyboardItem.setButton_list(btn_list);
            keyboardItem.setKeycode_list(keycode_list);
            keyboardItem.setTAG("Function Key");
            keyboardItemList.add(keyboardItem);
        }
        myapplication.setKeyboardItemlist(keyboardItemList);//전역변수로 설정정
    }
}
