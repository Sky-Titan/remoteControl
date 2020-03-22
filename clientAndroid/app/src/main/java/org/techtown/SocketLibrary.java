package org.techtown;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import org.techtown.remotecontrol.Activity.MainActivity;
import org.techtown.remotecontrol.Fragment.SettingFragment;
import org.techtown.remotecontrol.Myapplication;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketLibrary {

    View view;

    private String TAG = "SocketLibrary";
    Myapplication myapplication;
    NumberPicker mouseSensitivity, mouseWheelSensitivity;

    LinearLayout socket_layout, bluetooth_layout;

    EditText ip_address;
    EditText port_number;
    EditText certify_number;

    SharedPreferences sf;

    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    int port ;
    String ip = "";
    Socket sock;

    String msg_1="";//서버에서 받은 리턴 메시지

    Context context;

    public SocketLibrary(Myapplication myapplication)
    {
        this.myapplication = myapplication;
    }

    public void reconnect(final Context context, final Activity activity)//재연결
    {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                Log.d(TAG,"disconnect");
                String code = myapplication.getCertifyNumber();

                sock = myapplication.getSocket();

                if(sock!=null)//기존 연결 있을경우에만
                {
                    try {
                        //연결 해제
                        outputStream = new ObjectOutputStream(sock.getOutputStream());
                        outputStream.writeObject("FINISH"+"&"+code);
                        outputStream.flush();

                        inputStream = new ObjectInputStream(sock.getInputStream());
                        Object object = inputStream.readObject();
                        if (!sock.isClosed() && sock != null && object.toString().equals("OK"))
                            sock.close();

                        sock=null;

                        myapplication.setSocket(sock);

                        SettingFragment settingFragment = myapplication.getSettingFragment();
                        settingFragment.changeConnectBtnText("연결 하기");

                        //연결
                        ip = myapplication.getIp();
                        port = myapplication.getPort();

                        sock = new Socket(ip,port);//소켓 염

                        myapplication.setSocket(sock);//전역변수로 등록

                        code = myapplication.getCertifyNumber();

                        outputStream = new ObjectOutputStream(sock.getOutputStream());
                        outputStream.writeObject("START"+"&" + code);
                        outputStream.flush();

                        inputStream = new ObjectInputStream(sock.getInputStream());

                        object = inputStream.readObject();

                        final String str = object.toString();
                        System.out.println("object : "+object.toString());

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast toast = Toast.makeText(context,str,Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });


                        settingFragment.changeConnectBtnText("연결 끊기");


                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,"기존 연결이 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
        thread.start();
    }

    public void disconnect(final Context context, final Activity activity)
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d(TAG,"disconnect");
                String code = myapplication.getCertifyNumber();
                sock = myapplication.getSocket();
                try {

                    outputStream = new ObjectOutputStream(sock.getOutputStream());
                    outputStream.writeObject("FINISH"+"&"+code);
                    outputStream.flush();

                    inputStream = new ObjectInputStream(sock.getInputStream());
                    final Object object = inputStream.readObject();
                    System.out.println("object : "+object.toString());

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,object.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });


                    if (!sock.isClosed() && sock != null && object.toString().equals("OK"))
                        sock.close();


                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SettingFragment settingFragment = myapplication.getSettingFragment();
                            settingFragment.changeConnectBtnText("연결 하기");
                        }
                    });


                    myapplication.setSocket(null);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });
        thread.start();

    }

    public void connect(final Context context, final Activity activity)
    {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d(TAG,"connect");

                try
                {
                    ip = myapplication.getIp();
                    port = myapplication.getPort();

                    sock = new Socket(ip,port);//소켓 염
                    sock.setKeepAlive(true);//소켓 연결 알기 위해서
                    myapplication.setSocket(sock);//전역변수로 등록

                    String code = myapplication.getCertifyNumber();

                    outputStream = new ObjectOutputStream(sock.getOutputStream());
                    outputStream.writeObject("START"+"&" + code);
                    outputStream.flush();

                    inputStream = new ObjectInputStream(sock.getInputStream());

                    final Object object = inputStream.readObject();
                    System.out.println("object : "+object.toString());

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast toast = Toast.makeText(context,object.toString(),Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });


                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SettingFragment settingFragment = myapplication.getSettingFragment();
                            settingFragment.changeConnectBtnText("연결 하기");
                        }
                    });

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
