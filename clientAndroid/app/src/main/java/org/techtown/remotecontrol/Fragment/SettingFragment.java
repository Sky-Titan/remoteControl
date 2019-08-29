package org.techtown.remotecontrol.Fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.techtown.remotecontrol.Myapplication;
import org.techtown.remotecontrol.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    View view;

    private String TAG = "SettingFragment";

    EditText ip_address;
    EditText port_number;
    EditText certify_number;

    SharedPreferences sf;

    Myapplication myapplication;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);

        myapplication = (Myapplication)getActivity().getApplication();

        ip_address = (EditText) view.findViewById(R.id.IPaddress_edittext);
        port_number = (EditText) view.findViewById(R.id.portnumber_edittext);
        certify_number = (EditText) view.findViewById(R.id.certifynumber_edittext);

        sf = getActivity().getSharedPreferences("sFile",getActivity().MODE_PRIVATE);

        ip_address.setText(sf.getString("ip",""));
        port_number.setText(sf.getString("port","5001"));
        certify_number.setText(sf.getString("certify",""));

        myapplication.setIp(ip_address.getText().toString());
        myapplication.setPort(Integer.parseInt(port_number.getText().toString()));
        myapplication.setCertifyNumber(certify_number.getText().toString());

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
        return view;
    }

}
