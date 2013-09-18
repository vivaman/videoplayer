package com.video;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
public class PopView extends Activity{
 private Handler mHandler = new Handler(){

 public void handleMessage(Message msg) {
 switch (msg.what) {
 case 1:
 showPopupWindow();
 break;
 }
};
 };

public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
 setContentView(R.layout.main);

 Timer timer = new Timer();
timer.schedule(new initPopupWindow(), 100);
}

 private class initPopupWindow extends TimerTask{

 @Override
 public void run() {

 Message message = new Message();
 message.what = 1;
 mHandler.sendMessage(message);

 }
 }

 public void showPopupWindow() {
 Context mContext = PopView.this;
 LayoutInflater mLayoutInflater = (LayoutInflater) mContext
 .getSystemService(LAYOUT_INFLATER_SERVICE);
 View music_popunwindwow = mLayoutInflater.inflate(R.layout.popupview, null);
 PopupWindow mPopupWindow = new PopupWindow(music_popunwindwow,
 LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
 mPopupWindow.showAtLocation(findViewById(R.id.surface_view), Gravity.CENTER, 0, 0);
 }
}
