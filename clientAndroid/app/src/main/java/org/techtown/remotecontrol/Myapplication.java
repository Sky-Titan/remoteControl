package org.techtown.remotecontrol;

import android.app.Application;

public class Myapplication extends Application {

    private String ip = "";
    private int port = 0;

    @Override
    public void onCreate() {
        super.onCreate();


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
