package org.techtown.remotecontrol.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.techtown.remotecontrol.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MouseFragment extends Fragment {

    View view;
    private String TAG = "MouseFragment";
    LinearLayout touchlayout;

    public MouseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mouse, container, false);

        touchlayout = (LinearLayout) view.findViewById(R.id.mouseTouch_layout);
        touchlayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int eventaction = motionEvent.getAction();

                int first_X = (int) motionEvent.getX();//첫 터치한 X 좌표
                int first_Y = (int) motionEvent.getY();//첫 터치한 Y 좌표

                Log.d(TAG,"first_x : "+ first_X+" "+"first_y : "+first_Y);

                switch (eventaction)
                {
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_MOVE://터치 움직일때

                        break;
                    case MotionEvent.ACTION_DOWN:
                        break;
                }

                return false;
            }
        });

        return view;
    }

}
