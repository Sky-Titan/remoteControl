package org.techtown.remotecontrol.Fragment;


import android.graphics.drawable.BitmapDrawable;
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

    LinearLayout touchlayout;//마우스 터치 레이아웃

    int first_X,first_Y,current_X,current_Y,value_X=0,value_Y=0;//마우스 커서용
    int first_wheel_Y,current_wheel_Y,wheel_value_Y = 0;//휠 용

    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    int port ;
    String ip = "";
    Socket sock=null;

    boolean isConnect = false;

    String msg_1="";//서버에서 받은 리턴 메시지


    Button left,wheel,right;
    Button wheel_up,wheel_down,wheel_bar;

    Myapplication myapplication;

    public MouseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mouse, container, false);

        Log.d(TAG,"onCreateView");

        myapplication = (Myapplication)getActivity().getApplication();

        sock = myapplication.getSocket();

        wheel_up = (Button) view.findViewById(R.id.mouse_wheel_up);//마우스 휠 업
        wheel_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEvent("MOUSE WHEEL UP");
            }
        });

        wheel_down = (Button) view.findViewById(R.id.mouse_wheel_down);//마우스 휠 다운
        wheel_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEvent("MOUSE WHEEL DOWN");
            }
        });

        wheel_bar = (Button) view.findViewById(R.id.mouse_wheel_bar);//마우스 휠 드래그 바
        wheel_bar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int eventaction = motionEvent.getAction();

                //DOWN -> MOVE -> UP
                switch (eventaction)
                {
                    case MotionEvent.ACTION_UP://터치 뗄 때
                        break;
                    case MotionEvent.ACTION_MOVE://터치 드래그할때

                        current_wheel_Y = (int)motionEvent.getY();
                        Log.d(TAG,"current_wheel_y : "+current_wheel_Y);

                        wheel_value_Y = (current_wheel_Y - first_wheel_Y);

                        first_wheel_Y = current_wheel_Y;

                        sendEvent("MOUSE WHEEL DRAG");//drag 신호 보냄

                        break;
                    case MotionEvent.ACTION_DOWN://터치 했을 때 좌표
                        first_wheel_Y = (int) motionEvent.getY();//첫 터치한 Y 좌표
                        Log.d(TAG,"first_wheel_y : "+first_wheel_Y);

                        break;
                }

                return true;
            }
        });
        left = (Button) view.findViewById(R.id.mouse_left_btn);
        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int eventaction = motionEvent.getAction();

                //DOWN -> MOVE -> UP
                switch (eventaction)
                {
                    case MotionEvent.ACTION_UP://터치 뗄 때
                        sendEvent("MOUSE LEFT RELEASE");
                        break;
                    case MotionEvent.ACTION_MOVE://터치 드래그할때
                        break;
                    case MotionEvent.ACTION_DOWN://터치 했을 때 좌표
                        sendEvent("MOUSE LEFT PRESS");
                        break;
                }

                return true;
            }
        });

        wheel = (Button) view.findViewById(R.id.mouse_wheel_btn);
        wheel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int eventaction = motionEvent.getAction();

                //DOWN -> MOVE -> UP
                switch (eventaction)
                {
                    case MotionEvent.ACTION_UP://터치 뗄 때
                        sendEvent("MOUSE WHEEL RELEASE");
                        break;
                    case MotionEvent.ACTION_MOVE://터치 드래그할때
                        break;
                    case MotionEvent.ACTION_DOWN://터치 했을 때 좌표
                        sendEvent("MOUSE WHEEL PRESS");
                        break;
                }

                return true;
            }
        });

        right = (Button) view.findViewById(R.id.mouse_right_btn);
        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int eventaction = motionEvent.getAction();

                //DOWN -> MOVE -> UP
                switch (eventaction)
                {
                    case MotionEvent.ACTION_UP://터치 뗄 때
                        sendEvent("MOUSE RIGHT RELEASE");
                        break;
                    case MotionEvent.ACTION_MOVE://터치 드래그할때
                        break;
                    case MotionEvent.ACTION_DOWN://터치 했을 때 좌표
                        sendEvent("MOUSE RIGHT PRESS");
                        break;
                }

                return true;
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

                        sendEvent("MOUSE DRAG");//drag 신호 보냄

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

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

               try
                {

                    int sensitivity = myapplication.getMouseSensitivity();//마우스감도
                    int wheel_sensitivity = myapplication.getMouseWheelSensitivity();//휠감도

                    String code = myapplication.getCertifyNumber();

                    sock = myapplication.getSocket();


                    outputStream = new ObjectOutputStream(sock.getOutputStream());

                    if(motion.equals("MOUSE DRAG"))
                        outputStream.writeObject("MOUSE DRAG" + "&" + code + "&" + value_X * sensitivity+ "|" + value_Y * sensitivity );//서버로 전송
                    else if(motion.equals("MOUSE LEFT PRESS"))//마우스 왼쪽 버튼 누름
                        outputStream.writeObject("MOUSE LEFT PRESS" + "&" + code);//서버로 전송
                    else if(motion.equals("MOUSE LEFT RELEASE"))//마우스 왼쪽 버튼 떼기
                        outputStream.writeObject("MOUSE LEFT RELEASE" + "&" + code);//서버로 전송
                    else if(motion.equals("MOUSE WHEEL PRESS"))//마우스 휠 버튼 누름
                        outputStream.writeObject("MOUSE WHEEL PRESS" + "&" + code);//서버로 전송
                    else if(motion.equals("MOUSE WHEEL RELEASE"))//마우스 휠 버튼 떼기
                        outputStream.writeObject("MOUSE WHEEL RELEASE" + "&" + code);//서버로 전송
                    else if(motion.equals("MOUSE RIGHT PRESS"))//마우스 오른쪽 버튼 누름
                        outputStream.writeObject("MOUSE RIGHT PRESS" + "&" + code);//서버로 전송
                    else if(motion.equals("MOUSE RIGHT RELEASE"))//마우스 오른쪽 버튼 떼기
                       outputStream.writeObject("MOUSE RIGHT RELEASE" + "&" + code);//서버로 전송
                    else if(motion.equals("MOUSE WHEEL UP"))//마우스 휠 업
                        outputStream.writeObject("MOUSE WHEEL UP" + "&" + code + "&" + wheel_sensitivity);
                    else if(motion.equals("MOUSE WHEEL DOWN"))//마우스 휠 다운
                        outputStream.writeObject("MOUSE WHEEL DOWN" + "&" + code + "&" + wheel_sensitivity);
                    else if(motion.equals("MOUSE WHEEL DRAG"))//마우스 휠 다운
                        outputStream.writeObject("MOUSE WHEEL DRAG" + "&" + code + "&" + wheel_value_Y * wheel_sensitivity);


                    Log.d(TAG,"event 보냄");
                    inputStream = new ObjectInputStream(sock.getInputStream());//서버에서 return 받음

                    msg_1 = String.valueOf(inputStream.readObject());//서버에서 돌려받은 메시지
                    outputStream.flush();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    if(sock!=null&&!sock.isClosed())
                    {
                        try
                        {
                            //sock.close();
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();

                        }
                    }

                    return ;

                }

            }

        });
        thread.start();
    }
}
