package com.haomiao.cloud.rxbusdemo;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void sendTap(View view){
        RxBus.getInstance().send(Events.TAP, "Tap传了一个String");
    }

    public void sendOther(View view){
//        RxBus.getInstance().send(Events.OTHER, null);
        RxBus.getInstance().send(Events.OTHER, new OtherEvent("Cloud", 25));
    }
}
