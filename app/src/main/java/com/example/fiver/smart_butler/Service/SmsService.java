package com.example.fiver.smart_butler.Service;
/*
 *  项目名:  Smart_Butler
 *  包名:  com.example.fiver.smart_butler.Service
 *  文件名:  SmsService
 *  创建者:  YYC
 *  创建时间:  17/7/4 上午11:27
 *  描述:  TODO
 */

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.fiver.smart_butler.R;
import com.example.fiver.smart_butler.utils.L;
import com.example.fiver.smart_butler.utils.StaticClass;
import com.example.fiver.smart_butler.view.DispatchLinearLayout;

public class SmsService extends Service implements View.OnClickListener {
    //发件人号码
    private String smsPhone;
    //短信内容
    private String smsContent;
    private SmsReceiver receiver;
    //窗口管理
    private WindowManager wm;
    //布局参数
    private WindowManager.LayoutParams layoutParams;
    //view
    private DispatchLinearLayout mView;

    private TextView tv_phone;
    private TextView tv_content;
    private Button btn_send_sms;

    private HomeWatchReceiver mHomeWatchReceiver;

    public static final String SYSTEM_DIALOGS_REASON_KEY = "reason";
    public static final String SYSTEM_DIALOGS_HOME_KEY = "homekey";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        L.i("init service");
        //动态注册
        receiver = new SmsReceiver();
        IntentFilter intent = new IntentFilter();
        intent.addAction(StaticClass.SMS_ACTION);
        //设置权限
        intent.setPriority(Integer.MAX_VALUE);
        registerReceiver(receiver, intent);

        mHomeWatchReceiver = new HomeWatchReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mHomeWatchReceiver,intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        L.i("stop service");
        unregisterReceiver(mHomeWatchReceiver );
    }


    //短信广播
    public class SmsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (StaticClass.SMS_ACTION.equals(action)) {
                L.i("来短信了");
                //获取短信内容返回
                Object[] objs = (Object[]) intent.getExtras().get("pdus");
                //遍历数组得到数据
                for (Object obj : objs) {
                    SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
                    //发件人
                    smsPhone = sms.getOriginatingAddress();
                    //内容
                    smsContent = sms.getMessageBody();
                    L.i("短信的内容：" + "电话：" + smsPhone + "内容:" + smsContent);
                    showWindow();
                }
            }
        }
    }

    //窗口提示
    private void showWindow() {
        //获取系统服务
        wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //获取布局参数
        layoutParams = new WindowManager.LayoutParams();
        //定义宽高
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        //定义标记
        layoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //定义格式
        layoutParams.format = PixelFormat.TRANSPARENT;
        //定义类型
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //加载布局
        mView = (DispatchLinearLayout) View.inflate(getApplicationContext(), R.layout.sms_item, null);
        //初始化控件
        tv_phone = (TextView) mView.findViewById(R.id.tv_phone);
        tv_content = (TextView) mView.findViewById(R.id.tv_content);
        btn_send_sms = (Button) mView.findViewById(R.id.btn_send_sms);
        btn_send_sms.setOnClickListener(this);
        tv_phone.setText("发件人:" + smsPhone);
        tv_content.setText(smsContent);
        //添加view到窗口
        wm.addView(mView, layoutParams);

        mView.setDispatchKeyEventListener(mDispatchKeyEventListener);


    }
    private DispatchLinearLayout.DispatchKeyEventListener mDispatchKeyEventListener = new DispatchLinearLayout.DispatchKeyEventListener() {
        @Override
        public boolean dispatchKeyEvent(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK){
                if (mView.getParent()!=null){
                    wm.removeView(mView);
                }
                return true;
            }
            return false;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_sms:
                sendSms();
                //消失窗口
                if (mView.getParent() != null) {
                    wm.removeView(mView);
                }
                break;
        }
    }

    //回复短信
    private void sendSms() {
        Uri uri = Uri.parse("smsto:" + smsPhone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        //设置启动模式
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sms_body", "");
        startActivity(intent);
    }

    //监听home键的广播
    class HomeWatchReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)){
                String reason = intent.getStringExtra(SYSTEM_DIALOGS_REASON_KEY);
                if (SYSTEM_DIALOGS_HOME_KEY.equals(reason)){
                    if (mView.getParent()!=null){
                        wm.removeView(mView);
                    }
                }
            }

        }
    }

}
