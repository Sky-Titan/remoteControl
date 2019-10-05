package org.techtown.remotecontrol;

import java.util.ArrayList;

public class KeyboardItem {
    private String button_name;//버튼 이름
    private String TAG;//분류
    private ArrayList<String> button_list;//조합된 버튼목록
    private ArrayList<Integer> keycode_list;//자바에서 해당하는 KeyEvent.VK~ 의 int값 코드

    public KeyboardItem()
    {

    }
    public KeyboardItem(String button_name)
    {
        this.button_name = button_name;
    }
    public KeyboardItem(String button_name,ArrayList<String> button_list, ArrayList<Integer> keycode_list,String TAG)
    {
        this.button_name = button_name;
        this.button_list = button_list;
        this.keycode_list = keycode_list;
        this.TAG = TAG;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public ArrayList<Integer> getKeycode_list() {
        return keycode_list;
    }

    public void setKeycode_list(ArrayList<Integer> keycode_list) {
        this.keycode_list = keycode_list;
    }
    public void addKeyCode(Integer keycode)
    {
        keycode_list.add(keycode);
    }
    public int getKeyCode(int index)
    {
        return keycode_list.get(index);
    }
    public void addButton(String button)
    {
        button_list.add(button);
    }
    public String getButton(int index)
    {
        return button_list.get(index);
    }
    public String getButton_name() {
        return button_name;
    }

    public void setButton_name(String button_name) {
        this.button_name = button_name;
    }

    public ArrayList<String> getButton_list() {
        return button_list;
    }

    public void setButton_list(ArrayList<String> button_list) {
        this.button_list = button_list;
    }
}
