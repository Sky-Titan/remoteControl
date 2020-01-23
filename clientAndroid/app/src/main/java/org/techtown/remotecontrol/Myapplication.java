package org.techtown.remotecontrol;

import android.app.Application;

import java.net.Socket;
import java.util.ArrayList;

public class Myapplication extends Application {

    private Socket socket=null;

    private String ip = "";
    private int port = 0;
    private String certifyNumber;//인증코드
    private int mouseSensitivity;//마우스 감도
    private int mouseWheelSensitivity;//휠감도
    private ArrayList<KeyboardItem> keyboardItemlist;//전체 키보드 버튼 리스트

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ArrayList<KeyboardItem> getKeyboardItemlist() {
        return keyboardItemlist;
    }

    public void setKeyboardItemlist(ArrayList<KeyboardItem> keyboardItemlist) {
        this.keyboardItemlist = keyboardItemlist;
    }

    public int getMouseWheelSensitivity() {
        return mouseWheelSensitivity;
    }

    public void setMouseWheelSensitivity(int mouseWheelSensitivity) {
        this.mouseWheelSensitivity = mouseWheelSensitivity;
    }

    public int getMouseSensitivity() {
        return mouseSensitivity;
    }

    public void setMouseSensitivity(int mouseSensitivity) {
        this.mouseSensitivity = mouseSensitivity;
    }

    public String getCertifyNumber() {
        return certifyNumber;
    }

    public void setCertifyNumber(String certifyNumber) {
        this.certifyNumber = certifyNumber;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
