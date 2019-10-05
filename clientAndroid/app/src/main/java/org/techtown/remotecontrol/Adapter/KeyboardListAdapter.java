package org.techtown.remotecontrol.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.techtown.remotecontrol.KeyboardItem;
import org.techtown.remotecontrol.R;

import java.util.ArrayList;

public class KeyboardListAdapter extends BaseAdapter {

    private ArrayList<KeyboardItem> keyboardItemList = new ArrayList<KeyboardItem>();

    public KeyboardListAdapter()
    {

    }
    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return keyboardItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.keyboard_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        Button btn_keyboard = (Button)convertView.findViewById(R.id.btn_keyboard_item);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        KeyboardItem keyboardItem = keyboardItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        btn_keyboard.setText(keyboardItem.getButton_name());

        //TODO : 클릭리스너

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return keyboardItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String btn_name,ArrayList<String> btn_list, ArrayList<Integer> keycode_list)
    {
        KeyboardItem keyboardItem = new KeyboardItem(btn_name,btn_list,keycode_list);
        keyboardItemList.add(keyboardItem);
    }
}
