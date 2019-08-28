package org.techtown.remotecontrol.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.techtown.remotecontrol.Myapplication;
import org.techtown.remotecontrol.R;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {



    TextView information;
    TextView title;

    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    int port ;
    String ip = "220.122.13.177";
    Socket sock;

    String msg_1="";//서버에서 받은 리턴 메시지

    View view;

    private String TAG = "MainFragment";

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_main, container, false);


        Button enter_btn = (Button) view.findViewById(R.id.enter_btn);
        enter_btn.setOnClickListener(this);//ENTER버튼 클릭

        Button space_btn = (Button) view.findViewById(R.id.space_btn);
        space_btn.setOnClickListener(this);//SPACE버튼 클릭

        Button alt_tab_btn = (Button) view.findViewById(R.id.alt_tab_btn);
        alt_tab_btn.setOnClickListener(this);//ALT TAB 클릭

        Button up_btn = (Button) view.findViewById(R.id.up_btn);
        up_btn.setOnClickListener(this);

        Button down_btn = (Button) view.findViewById(R.id.down_btn);
        down_btn.setOnClickListener(this);

        Button right_btn = (Button) view.findViewById(R.id.right_btn);
        right_btn.setOnClickListener(this);

        Button left_btn = (Button) view.findViewById(R.id.left_btn);
        left_btn.setOnClickListener(this);
        //확인 버튼
     /*   confirm = (Button) view.findViewById(R.id.connectButton_ipconfig);
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
        });*/

        return view;
    }

    @Override
    public void onClick(final View view) {

        //스레드 내에서 토스트 메시지를 호출
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(getContext(), msg_1, Toast.LENGTH_SHORT).show();
                super.handleMessage(msg);
            }
        };
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {
                    Myapplication myapplication = (Myapplication)getActivity().getApplication();
                    ip = myapplication.getIp();
                    port = myapplication.getPort();

                    sock = new Socket(ip,port);//소켓 염

                    outputStream = new ObjectOutputStream(sock.getOutputStream());
                    outputStream.writeObject( ((Button)view).getText().toString() );//서버로 전송
                    outputStream.flush();

                    inputStream = new ObjectInputStream(sock.getInputStream());//서버에서 return ㅂ다음

                    msg_1 = String.valueOf(inputStream.readObject());//서버에서 돌려받은 메시지

                    handler.sendEmptyMessage(0);//hadler 호출

                    sock.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

        });
        thread.start();
        switch (view.getId())
        {
            case R.id.enter_btn://ENTER



                break;
        }
    }
}
