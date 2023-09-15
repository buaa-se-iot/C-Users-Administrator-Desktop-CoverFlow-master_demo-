package com.topelec.smartagriculture;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.topelec.zigbeecontrol.Command;
import com.topelec.zigbeecontrol.SensorControl;

import it.moondroid.coverflowdemo.R;


public class MainActivity extends Activity implements View.OnClickListener,SensorControl.LedListener{
    private boolean isLed1On;
    private Button testBtn;

    SensorControl mSensorControl;
    Handler myHandler = new Handler() {

        //2.重写消息处理函数
        public void handleMessage(Message msg) {
            Bundle data;
            data = msg.getData();
            if (data.getByte("led_status") == 0x01) {
                isLed1On = true;
            }else {
                isLed1On = false;
            }
            super.handleMessage(msg);
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smartagriculture);
        isLed1On=false;
        testBtn = (Button) findViewById(R.id.testBtn);
        testBtn.setOnClickListener(this);
        mSensorControl = new SensorControl();
        mSensorControl.addLedListener(this);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.testBtn:
                Log.v("DEV","点击了testBtn");
                if (isLed1On)
                {
                    mSensorControl.led1_Off(false);
                }else{
                    mSensorControl.led1_On(false);
                }
                break;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mSensorControl.actionControl(true);
    }
    @Override
    protected void onStop() {
        super.onStop();
        mSensorControl.actionControl(false);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorControl.removeLedListener(this);
        mSensorControl.closeSerialDevice();
    }
    @Override
    public void LedControlResult(byte led_id,byte led_status) {
        Message msg = new Message();
        msg.what = 0x01;
        Bundle data = new Bundle();
        data.putByte("led_id",led_id);
        data.putByte("led_status",led_status);
        Log.v("DEV","LedControlResult:ledid="+led_id+",led_status="+led_status);
        msg.setData(data);
        myHandler.sendMessage(msg);
    }
}
