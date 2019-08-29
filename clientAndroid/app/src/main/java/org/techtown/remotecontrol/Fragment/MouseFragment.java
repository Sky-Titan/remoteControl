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
import android.widget.LinearLayout;
import android.widget.Toast;

import org.techtown.remotecontrol.Myapplication;
import org.techtown.remotecontrol.R;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * A simple {@link Fragment} subclass.
 */
public class MouseFragment extends Fragment {

    View view;

    private String TAG = "MouseFragment";

    LinearLayout touchlayout;

    int first_X,first_Y,current_X,current_Y,value_X=0,value_Y=0;

    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    int port ;
    String ip = "220.122.13.177";
    Socket sock=null;

    String msg_1="";//서버에서 받은 리턴 메시지

    boolean isFinished = true ; //스레드 여러 개 생성 못하게 하기 위한 장치

    Button left,wheel,right;

    public MouseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mouse, container, false);

        left = (Button) view.findViewById(R.id.mouse_left_btn);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEvent("MOUSE LEFT");//마우스 왼쪽버튼
            }
        });

        wheel = (Button) view.findViewById(R.id.mouse_wheel_btn);
        wheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEvent("MOUSE WHEEL BTN");
            }
        });

        right = (Button) view.findViewById(R.id.mouse_right_btn);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEvent("MOUSE RIGHT");//마우스 오른쪽버튼
            }
        });

        touchlayout = (LinearLayout) view.findViewById(R.id.mouseTouch_layout);
        touchlayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int eventaction = motionEvent.getAction();

                //DOWN -> MOVE -> UP
                switch (eventaction)
                {
                    case MotionEvent.ACTION_UP://터치 뗄 때
                        break;
                    case MotionEvent.ACTION_MOVE://터치 드래그할때
                        current_X = (int)motionEvent.getX();
                        current_Y = (int)motionEvent.getY();
                        Log.d(TAG,"current_x : "+ current_X+" "+"current_y : "+current_Y);

                        value_X = current_X - first_X;
                        value_Y = current_Y - first_Y;

                        first_X = current_X;
                        first_Y = current_Y;

                        if(isFinished)
                        {
                            sendEvent("MOUSE DRAG");//drag 신호 보냄
                            isFinished=false;
                        }
                        break;
                    case MotionEvent.ACTION_DOWN://터치 했을 때 좌표
                        first_X = (int) motionEvent.getX();//첫 터치한 X 좌표
                        first_Y = (int) motionEvent.getY();//첫 터치한 Y 좌표
                        Log.d(TAG,"first_x : "+ first_X+" "+"first_y : "+first_Y);

                        break;
                }


                return true;//무조건 true!!
            }
        });

        return view;
    }

    public void sendEvent(final String motion)
    {
        //스레드 내에서 토스트 메시지를 호출
     /*   final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(getContext(), msg_1, Toast.LENGTH_SHORT).show();
                super.handleMessage(msg);
            }
        };*/
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

                    if(motion.equals("MOUSE DRAG"))
                        outputStream.writeObject("MOUSE DRAG" + "&" + code + "&" + value_X + "|" + value_Y );//서버로 전송
                    else if(motion.equals("MOUSE LEFT"))
                        outputStream.writeObject("MOUSE LEFT" + "&" + code);//서버로 전송
                    else if(motion.equals("MOUSE WHEEL BTN"))
                        outputStream.writeObject("MOUSE WHEEL BTN" + "&" + code);//서버로 전송
                    else if(motion.equals("MOUSE RIGHT"))
                        outputStream.writeObject("MOUSE RIGHT" + "&" + code);//서버로 전송


                    Log.d(TAG,"event 보냄");
                    inputStream = new ObjectInputStream(sock.getInputStream());//서버에서 return 받음

                    msg_1 = String.valueOf(inputStream.readObject());//서버에서 돌려받은 메시지
                    outputStream.flush();
                 //   handler.sendEmptyMessage(0);//hadler 호출

                    if(sock!=null&&!sock.isClosed())
                        sock.close();
                    sock=null;
                    isFinished=true;
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    if(sock!=null&&!sock.isClosed())
                    {
                        try
                        {
                            sock.close();
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                            isFinished=true;
                        }
                    }
                    isFinished=true;
                    return ;

                }

            }

        });
        thread.start();
    }
}