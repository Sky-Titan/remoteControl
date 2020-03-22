package org.techtown.remotecontrol.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.Toast;

import org.techtown.SocketLibrary;
import org.techtown.remotecontrol.Myapplication;
import org.techtown.remotecontrol.R;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    View view;

    private String TAG = "SettingFragment";
    Myapplication myapplication;
    NumberPicker mouseSensitivity, mouseWheelSensitivity;

    LinearLayout socket_layout, bluetooth_layout;

    EditText ip_address;
    EditText port_number;
    EditText certify_number;

    SharedPreferences sf;

    Socket sock;

    SocketLibrary socketLibrary;


    Context context;

    Button socket_connect_btn;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("resume");

        if(myapplication.getSocket()==null)
        {
            socket_connect_btn.setText("연결 하기");
        }
        else
        {
            socket_connect_btn.setText("연결 끊기");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);

        context = container.getContext();
        myapplication = (Myapplication)getActivity().getApplication();

        socketLibrary = new SocketLibrary(myapplication);

        mouseSensitivity = (NumberPicker) view.findViewById(R.id.mouse_sensitivity_picker);
        mouseSensitivity.setMinValue(1);
        mouseSensitivity.setMaxValue(5);
        mouseSensitivity.setValue(1);
        mouseSensitivity.setWrapSelectorWheel(false);

        mouseWheelSensitivity = (NumberPicker) view.findViewById(R.id.wheel_sensitivity_picker);
        mouseWheelSensitivity.setMinValue(1);
        mouseWheelSensitivity.setMaxValue(5);
        mouseWheelSensitivity.setValue(1);
        mouseWheelSensitivity.setWrapSelectorWheel(false);

        ip_address = (EditText) view.findViewById(R.id.IPaddress_edittext);
        port_number = (EditText) view.findViewById(R.id.portnumber_edittext);
        certify_number = (EditText) view.findViewById(R.id.certifynumber_edittext);

        sf = getActivity().getSharedPreferences("sFile",getActivity().MODE_PRIVATE);

        ip_address.setText(sf.getString("ip",""));
        port_number.setText(sf.getString("port","5001"));
        certify_number.setText(sf.getString("certify",""));
        mouseSensitivity.setValue(sf.getInt("mouseSensitivity",1));
        mouseWheelSensitivity.setValue(sf.getInt("mouseWheelSensitivity",1));

        myapplication.setIp(ip_address.getText().toString());
        myapplication.setPort(Integer.parseInt(port_number.getText().toString()));
        myapplication.setCertifyNumber(certify_number.getText().toString());
        myapplication.setMouseSensitivity(mouseSensitivity.getValue());
        myapplication.setMouseWheelSensitivity(mouseWheelSensitivity.getValue());

        //마우스감도
        mouseSensitivity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                SharedPreferences.Editor editor = sf.edit();
                editor.putInt("mouseSensitivity",i1);
                editor.commit();

                myapplication.setMouseSensitivity(i1);
            }
        });

        //휠감도
        mouseWheelSensitivity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                SharedPreferences.Editor editor = sf.edit();
                editor.putInt("mouseWheelSensitivity",i1);
                editor.commit();

                myapplication.setMouseWheelSensitivity(i1);
            }
        });
        //ip 주소 텍스트 변화 있을때마다 전역변수로 입력
        ip_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
                SharedPreferences.Editor editor = sf.edit();
                editor.putString("ip", ip_address.getText().toString()); // key, value를 이용하여 저장하는 형태
                editor.commit();

                myapplication.setIp(ip_address.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //포트번호 텍스트 변화 있을 때마다 전역변수로 입력
        port_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
                SharedPreferences.Editor editor = sf.edit();
                editor.putString("port", port_number.getText().toString()); // key, value를 이용하여 저장하는 형태
                editor.commit();
                myapplication.setPort(Integer.parseInt(port_number.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //인증번호 텍스트 변화 있을 때마다 전역변수로 입력
        certify_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
                SharedPreferences.Editor editor = sf.edit();
                editor.putString("certify", certify_number.getText().toString()); // key, value를 이용하여 저장하는 형태
                editor.commit();
                myapplication.setCertifyNumber(certify_number.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        socket_layout = (LinearLayout)view.findViewById(R.id.socket_connect_layout);
        bluetooth_layout = (LinearLayout)view.findViewById(R.id.bluetooth_connect_layout);

        //나타나는 애니메이션
        final Animation animation = new AlphaAnimation(0, 1);
        animation.setDuration(300);
        //사라지는 애니메이션
        final Animation animation2 = new AlphaAnimation(1, 0);
        animation2.setDuration(300);

        Switch bluetooth_switch = (Switch)view.findViewById(R.id.bluetooth_switch);
        bluetooth_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)//체크면 블루투스 연결
                {
                    socket_layout.startAnimation(animation2);
                    bluetooth_layout.startAnimation(animation);
                    bluetooth_layout.setVisibility(View.VISIBLE);
                    socket_layout.setVisibility(View.GONE);

                }
                else {//체크안되있으면 소켓 연결
                    socket_layout.startAnimation(animation);
                    bluetooth_layout.startAnimation(animation2);
                    bluetooth_layout.setVisibility(View.GONE);
                    socket_layout.setVisibility(View.VISIBLE);

                }
            }
        });

        socket_connect_btn = (Button)view.findViewById(R.id.socket_connect_button);
        socket_connect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sock = myapplication.getSocket();
                if(sock==null) {//연결
                    //connect();
                    socketLibrary.connect(context,getActivity());

                }
                else {
                    //disconnect();
                    socketLibrary.disconnect(context,getActivity());
                }

            }
        });

        return view;
    }

    public void changeConnectBtnText(final String changeText)//connect 버튼 텍스트 변경
    {
        System.out.println("바뀜");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                socket_connect_btn.setText(changeText);
            }
        });

    }


}
