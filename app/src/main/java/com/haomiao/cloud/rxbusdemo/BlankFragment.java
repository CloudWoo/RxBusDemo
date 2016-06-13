package com.haomiao.cloud.rxbusdemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxFragment;

import rx.functions.Action1;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends RxFragment {


    public BlankFragment() {
        // Required empty public constructor
    }

    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_blank, null);
        textView = (TextView) inflate.findViewById(R.id.text);

        RxBus.with(this)
                .setEvent(Events.TAP)
//                .setEndEvent(FragmentEvent.DESTROY_VIEW) //不设置默认与fragment生命周期同步
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        String content = events.getContent();
                        textView.setText(content);
                    }
                })
                .create();

        RxBus.with(this)
                .setEvent(Events.OTHER)
                .setEndEvent(FragmentEvent.DESTROY_VIEW) //不设置默认与fragment生命周期同步
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        OtherEvent event = events.getContent();
                        textView.setText("Name: "  + event.getName() + ",Age: "+ event.getAge());
                    }
                })
                .onError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        textView.setText(throwable.toString());
                    }
                }) // 异常处理，默认捕获异常，不做处理，程序不会crash。
                .create();

        return inflate;
    }

}
