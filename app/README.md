

# RxBus
通过RxJava实现Rxbus。

相信大家已经非常熟悉EventBus了。最近正在学习Rxjava，如果在项目中已经使用了Rxjava，使用RxBus来代替EventBus应该是不错的选择。
```
public class RxBus {

  private static RxBus rxBus;
  private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

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




  public void send(Object o) {
    _bus.onNext(o);
  }

  public Observable<Object> toObserverable() {
    return _bus;
  }
}
```
## 推荐文章


- [给 Android 开发者的 RxJava 详解](http://gank.io/post/560e15be2dca930e00da1083)

- [深入浅出RxJava](http://blog.csdn.net/lzyzsd/article/details/41833541/)

- [RxJava操作符](http://blog.chinaunix.net/uid-20771867-id-5187376.html)

- [Retrofit](http://square.github.io/retrofit/)

- [使用RxBinding响应控件的异步事件](http://www.jianshu.com/p/c2c7c46e6b97/comments/1338430)

- [MVC,MVP,MVVM与架构经验谈](https://www.sdk.cn/news/2501)

- [Android平台上MVP的介绍](https://github.com/bboyfeiyu/android-tech-frontier/blob/master/issue-12%2FAndroid%E4%B8%8AMVP%E7%9A%84%E4%BB%8B%E7%BB%8D.md#使用mvp)


## 参考资料
- [FlowGeek](https://git.oschina.net/handoop/FlowGeek):开源中国Android客户端MVP架构Material Design设计风格版

- [Implementing an Event Bus With RxJava – RxBus](http://nerds.weddingpartyapp.com/tech/2014/12/24/implementing-an-event-bus-with-rxjava-rxbus/)


## Note
- 本人技术和见识都有限，一些解决方案可能幼稚的可怕，希望大家不吝赐教，共同进步。

- 请使用android版本5.1以上的手机以保证最佳效果。

- 数据接口来自网路，如有侵权，立刻删除。

- Stay hungry, Stay foolish。