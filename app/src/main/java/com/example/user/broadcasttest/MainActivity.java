package com.example.user.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private IntentFilter mIntentFilter;

    private LocalReceiver mLocalReceiver;
    private LocalBroadcastManager mLocalBroadcastManager;
//    private NetworkChangeReceiver mNetworkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);//获取实例

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.broadcasttest.LOCAL_BROADCAST");
                mLocalBroadcastManager.sendBroadcast(intent);//发送本地广播(从sendBroadcast改进而来)
            }
        });
        mIntentFilter  = new IntentFilter();
//        mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        mNetworkChangeReceiver = new NetworkChangeReceiver();
//        registerReceiver(mNetworkChangeReceiver,mIntentFilter);

        mIntentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
        mLocalReceiver = new LocalReceiver();
        mLocalBroadcastManager.registerReceiver(mLocalReceiver,mIntentFilter);//注册本地广播监听器
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //unregisterReceiver(mNetworkChangeReceiver);
        mLocalBroadcastManager.unregisterReceiver(mLocalReceiver);//使用LocalBroadcastManager后
    }

//    class NetworkChangeReceiver extends BroadcastReceiver{
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            //Toast.makeText(context,"network changes",Toast.LENGTH_SHORT).show();
//            //修改代码,可以广播到是否可以看到网络状态。会遇到权限问题
//            ConnectivityManager connectivityManager = (ConnectivityManager)
//                    getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//            if (networkInfo != null && networkInfo.isAvailable()){
//                Toast.makeText(context,"network is available",Toast.LENGTH_SHORT).show();
//            }else{
//                Toast.makeText(context,"network is not available",Toast.LENGTH_SHORT).show();
//            }
//
//
//        }
//    }
    class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"received local broadcast",Toast.LENGTH_SHORT).show();
        }
    }
}
