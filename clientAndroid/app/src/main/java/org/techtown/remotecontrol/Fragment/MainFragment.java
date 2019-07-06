package org.techtown.remotecontrol.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.techtown.remotecontrol.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    Button confirm;
    TextView information;
    TextView title;

    EditText ip_address;
    EditText port_number;
    View view;

    public void autoConnect()//자동연결
    {

    }
    public void socketConnect(String ip,String port)
    {
    }
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_main, container, false);

        autoConnect();//자동연결


        ip_address = (EditText) view.findViewById(R.id.IPaddress_edittext);
        port_number = (EditText) view.findViewById(R.id.portnumber_edittext);

        //확인 버튼
        confirm = (Button) view.findViewById(R.id.connectButton_ipconfig);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ip_address.getText().toString().equals("") || port_number.getText().toString().equals(""))//주소비었는지확인
                {
                    Toast.makeText(getContext(),"정보를 입력해주십시오",Toast.LENGTH_SHORT).show();
                }
                else//입력이 있다면
                {

                }
            }
        });

        return view;
    }

}
