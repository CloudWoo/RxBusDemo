package com.haomiao.cloud.rxbusdemo;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.FragmentLifecycleProvider;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * 使用RxJava代替EventBus的方案
 * @author thanatos
 * @create 2016-01-05
 **/
public class RxBus {

    private static RxBus rxBus;
    private final Subject<Events<?>, Events<?>> _bus = new SerializedSubject<>(PublishSubject.<Events<?>>create());

    private RxBus(){}

    public static RxBus getInstance(){
        if (rxBus == null){
            synchronized (RxBus.class){
                if (rxBus == null){
                    rxBus = new RxBus();
                }
            }
        }
        return rxBus;
    }

    public void send(Events<?> o) {
        _bus.onNext(o);
    }

    public void send(@Events.EventCode int code, Object content){
        Events<Object> event = new Events<>();
        event.code = code;
        event.content = content;
        send(event);
    }

    public Observable<Events<?>> toObservable() {
        return _bus;
    }

    public static SubscriberBuilder with(FragmentLifecycleProvider provider){
        return new SubscriberBuilder(provider);
    }

    public static SubscriberBuilder with(ActivityLifecycleProvider provider){
        return new SubscriberBuilder(provider);
    }


    public static class SubscriberBuilder{

        private FragmentLifecycleProvider mFragLifecycleProvider;
        private ActivityLifecycleProvider mActLifecycleProvider;
        private FragmentEvent mFragmentEndEvent;
        private ActivityEvent mActivityEndEvent;
        private int event;
        private Action1<? super Events<?>> onNext;
        private Action1<Throwable> onError;

        public SubscriberBuilder(FragmentLifecycleProvider provider) {
            this.mFragLifecycleProvider = provider;
        }

        public SubscriberBuilder(ActivityLifecycleProvider provider){
            this.mActLifecycleProvider = provider;
        }

        public SubscriberBuilder setEvent(@Events.EventCode int event){
            this.event = event;
            return this;
        }

        public SubscriberBuilder setEndEvent(FragmentEvent event){
            this.mFragmentEndEvent = event;
            return this;
        }

        public SubscriberBuilder setEndEvent(ActivityEvent event){
            this.mActivityEndEvent = event;
            return this;
        }

        public SubscriberBuilder onNext(Action1<? super Events<?>> action){
            this.onNext = action;
            return this;
        }

        public SubscriberBuilder onError(Action1<Throwable> action){
            this.onError = action;
            return this;
        }


        public void create(){
            _create();
        }

        public Subscription _create(){
            if (mFragLifecycleProvider!=null){
                return RxBus.getInstance().toObservable()
                        .compose(mFragmentEndEvent == null ? mFragLifecycleProvider.<Events<?>>bindToLifecycle() :mFragLifecycleProvider.<Events<?>>bindUntilEvent(mFragmentEndEvent)) // 绑定生命周期
                        .filter(new Func1<Events<?>, Boolean>() {
                            @Override
                            public Boolean call(Events<?> events) {
                                return events.code == event;
                            }
                        })   //过滤 根据code判断返回事件
                        .subscribe(onNext, onError == null ? new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        } : onError);
            }
            if (mActLifecycleProvider!=null){
                return RxBus.getInstance().toObservable()
                        .compose(mActivityEndEvent == null ? mActLifecycleProvider.<Events<?>>bindToLifecycle() : mActLifecycleProvider.<Events<?>>bindUntilEvent(mActivityEndEvent))
                        .filter(new Func1<Events<?>, Boolean>() {
                            @Override
                            public Boolean call(Events<?> events) {
                                return events.code == event;
                            }
                        })
                        .subscribe(onNext, onError == null ? new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        } : onError);
            }
            return null;
        }
    }
}
