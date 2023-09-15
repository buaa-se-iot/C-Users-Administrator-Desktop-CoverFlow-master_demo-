package com.topelec.smarthome;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.util.Log;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.topelec.zigbeecontrol.Command;
import com.topelec.zigbeecontrol.SensorControl;

import it.moondroid.coverflowdemo.R;


public class MainActivity extends Activity implements
        View.OnClickListener,SensorControl.LedListener
{
    private boolean isLed1On;
    private boolean isLed2On;
    private boolean isLed3On;
    private boolean isLed4On;
    private ImageButton btnLed1;
    private ImageButton btnLed2;
    private ImageButton btnLed3;
    private ImageButton btnLed4;
    private ImageButton btnStart;
    SensorControl mSensorControl;

    /**
     * 用于更新UI
     */
    Handler myHandler = new Handler() {
        //2.重写消息处理函数
        public void handleMessage(Message msg) {
            Bundle data;
            data = msg.getData();
            switch (msg.what) {
                //判断发送的消息
                case 0x01:
                    switch (data.getByte("led_id")) {
                        case 0x01:
                            if (data.getByte("led_status") == 0x01) {
                                btnLed1.setImageDrawable(getResources().getDrawable(R.drawable.smarthome_led_on));
                                isLed1On = true;
                            }else {
                                btnLed1.setImageDrawable(getResources().getDrawable(R.drawable.smarthome_led_off));
                                isLed1On = false;
                            }
                            break;
                        case 0x02:
                            if (data.getByte("led_status") == 0x01) {
                                btnLed2.setImageDrawable(getResources().getDrawable(R.drawable.smarthome_led_on));
                                isLed2On = true;
                            }else {
                                btnLed2.setImageDrawable(getResources().getDrawable(R.drawable.smarthome_led_off));
                                isLed2On = false;
                            }
                            break;
                        case 0x03:
                            if (data.getByte("led_status") == 0x01) {
                                btnLed3.setImageDrawable(getResources().getDrawable(R.drawable.smarthome_led_on));
                                isLed3On = true;
                            } else {
                                btnLed3.setImageDrawable(getResources().getDrawable(R.drawable.smarthome_led_off));
                                isLed3On = false;
                            }
                            break;
                        case 0x04:
                            if (data.getByte("led_status") == 0x01) {
                                btnLed4.setImageDrawable(getResources().getDrawable(R.drawable.smarthome_led_on));
                                isLed4On = true;
                            }else {
                                btnLed4.setImageDrawable(getResources().getDrawable(R.drawable.smarthome_led_off));
                                isLed4On = false;
                            }
                            break;
                        case 0x05:
                            if (data.getByte("led_status") == 0x01) {
                                btnLed1.setImageDrawable(getResources().getDrawable(R.drawable.smarthome_led_on));
                                btnLed2.setImageDrawable(getResources().getDrawable(R.drawable.smarthome_led_on));
                                btnLed3.setImageDrawable(getResources().getDrawable(R.drawable.smarthome_led_on));
                                btnLed4.setImageDrawable(getResources().getDrawable(R.drawable.smarthome_led_on));
                                isLed1On = true;
                                isLed2On = true;
                                isLed3On = true;
                                isLed4On = true;
                            } else {
                                btnLed1.setImageDrawable(getResources().getDrawable(R.drawable.smarthome_led_off));
                                btnLed2.setImageDrawable(getResources().getDrawable(R.drawable.smarthome_led_off));
                                btnLed3.setImageDrawable(getResources().getDrawable(R.drawable.smarthome_led_off));
                                btnLed4.setImageDrawable(getResources().getDrawable(R.drawable.smarthome_led_off));
                                isLed1On = false;
                                isLed2On = false;
                                isLed3On = false;
                                isLed4On = false;
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_smarthome);
        initialization();
        mSensorControl = new SensorControl();
        mSensorControl.addLedListener(this);
    }



    private void initialization()
    {
        isLed1On = false;
        isLed2On = false;
        isLed3On = false;
        isLed4On = false;


        btnLed1 = (ImageButton) findViewById(R.id.btnLed1);
        btnLed1.setOnClickListener(this);

        btnLed2 = (ImageButton) findViewById(R.id.btnLed2);
        btnLed2.setOnClickListener(this);

        btnLed3 = (ImageButton) findViewById(R.id.btnLed3);
        btnLed3.setOnClickListener(this);

        btnLed4 = (ImageButton) findViewById(R.id.btnLed4);
        btnLed4.setOnClickListener(this);
        btnStart = (ImageButton) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnLed1:
                if (isLed1On)
                {
                    mSensorControl.led1_Off(false);
                }else{
                    mSensorControl.led1_On(false);
                }
                break;
            case R.id.btnLed2:
                if (isLed2On)
                {
                    mSensorControl.led2_Off(false);
                }else{
                    mSensorControl.led2_On(false);
                }
                break;
            case R.id.btnLed3:
                if (isLed3On)
                {
                    mSensorControl.led3_Off(false);
                }else{
                    mSensorControl.led3_On(false);
                }
                break;
            case R.id.btnLed4:
                if (isLed4On)
                {
                    mSensorControl.led4_Off(false);
                }else{
                    mSensorControl.led4_On(false);
                }
                break;
        }
    }


    /**
     * 由不可见变为可见时调用
     */
    @Override
    protected void onStart() {
        super.onStart();

        mSensorControl.actionControl(true);
    }
    /**
     * 在完全不可见时调用
     */
    @Override
    protected void onStop() {

        super.onStop();
        mSensorControl.actionControl(false);
    }

    /**
     * 在活动销毁时调用
     */
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
