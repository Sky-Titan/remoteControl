package org.techtown.remotecontrol.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class KeyboardFragment extends Fragment implements View.OnTouchListener {



    TextView information;
    TextView title;

    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    int port ;
    String ip = "220.122.13.177";
    Socket sock;

    String msg_1="";//서버에서 받은 리턴 메시지

    View view;

    private String TAG = "KeyboardFragment";

    public KeyboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_keyboard, container, false);


        Button enter_btn = (Button) view.findViewById(R.id.enter_btn);
        enter_btn.setOnTouchListener(this);//ENTER버튼 클릭

        Button space_btn = (Button) view.findViewById(R.id.space_btn);
        space_btn.setOnTouchListener(this);//SPACE버튼 클릭

        Button alt_tab_btn = (Button) view.findViewById(R.id.alt_tab_btn);
        alt_tab_btn.setOnTouchListener(this);//ALT TAB 클릭

        Button up_btn = (Button) view.findViewById(R.id.up_btn);
        up_btn.setOnTouchListener(this);

        Button down_btn = (Button) view.findViewById(R.id.down_btn);
        down_btn.setOnTouchListener(this);

        Button right_btn = (Button) view.findViewById(R.id.right_btn);
        right_btn.setOnTouchListener(this);

        Button left_btn = (Button) view.findViewById(R.id.left_btn);
        left_btn.setOnTouchListener(this);

        return view;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int eventaction = motionEvent.getAction();

        //DOWN -> MOVE -> UP
        switch (eventaction)
        {
            case MotionEvent.ACTION_UP://터치 뗄 때
                sendEvent( ((Button)view).getText().toString() + " " + "RELEASE");
                break;
            case MotionEvent.ACTION_MOVE://터치 드래그할때
                break;
            case MotionEvent.ACTION_DOWN://터치 했을 때 좌표
                sendEvent( ((Button)view).getText().toString() + " " + "PRESS");
                break;
        }


        return true;//무조건 true!!
    }

    public void sendEvent(final String motion)
    {
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
                    String code = myapplication.getCertifyNumber();
                    sock = new Socket(ip,port);//소켓 염

                    outputStream = new ObjectOutputStream(sock.getOutputStream());
                    outputStream.writeObject( motion+"&"+code );//서버로 전송
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
    }

}
