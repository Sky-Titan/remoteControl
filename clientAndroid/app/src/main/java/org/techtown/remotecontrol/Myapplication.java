package org.techtown.remotecontrol;

import android.app.Application;

public class Myapplication extends Application {

    private String ip = "";
    private int port = 0;
    private String certifyNumber;

    @Override
    public void onCreate() {
        super.onCreate();


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
