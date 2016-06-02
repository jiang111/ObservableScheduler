# ObservableScheduler

[ ![Download](https://api.bintray.com/packages/yuesong/maven/ObservableScheduler/images/download.svg) ](https://bintray.com/yuesong/maven/ObservableScheduler/_latestVersion)

你的app是不是经常做一些很耗费是工作,但这些工作都是在主线程完成的,因为开线程然后回调主线程很麻烦的说。当然,我们可以用RxJava轻松实现，
但是如果你的项目没有集成RxJava怎么办,集成进去? 如果集成RxJava只为做这些事岂不是太浪费了。你最好集成RxAndroid,RxBus,RxLifecycle,RxBinding
是不是感觉要改架构了😂

ObservableScheduler 最主要的目的就是轻松的帮你在子线程和主线程之间做转换, 项目的思想来自RxJava,代码很有可能也是模仿RxJava,没关系,人家牛我们就得模仿。


##Demo:
![](https://raw.githubusercontent.com/jiang111/ObservableScheduler/master/art/art.gif)

##示例代码:
```java
JObservable.create(new JObservable.OnSubscribe<List<Bitmap>>() {
            @Override
            public void call(SubscribeManager<List<Bitmap>> mSubscriber) {
                try {
                    //TODO... 
                    mSubscriber.notifyData(bitmaps);
                } catch (Exception e) {
                    mSubscriber.error(e);
                }
            }
        }).workedOn(Schedules.background())
                .subscribeOn(Schedules.mainThread())
                .subscribe(new Subscriber<List<Bitmap>>() {
                    @Override
                    public void notifyData(List<Bitmap> strings) {
                      //TODO... 
                    }
                    @Override
                    public void error(Throwable t) {
                      //TODO... 
                    }
                });
```

##用法:
###第一步:
gradle:
```
compile 'com.jiang.android.observablescheduler:schedule:1.0.1'
```
Maven:
```
<dependency>
  <groupId>com.jiang.android.observablescheduler</groupId>
  <artifactId>schedule</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```

###第二步:
#### [阅读Wiki](https://github.com/jiang111/ObservableScheduler/wiki)


##推荐

>* 更好的管理线程间跳转的库 https://github.com/jiang111/ObservableScheduler

>* 收集Android你不知道的那些小技巧:https://github.com/jiang111/awesome-android-tips

>* 收集Android studio 常用的插件,请看这里:https://github.com/jiang111/awesome-androidstudio-plugins

>* 收集程序员必备的那些Chrome插件: https://github.com/jiang111/chrome-plugin-recommand

>* 通过RecyclerView实现的联系人: https://github.com/jiang111/IndexRecyclerView

>* 用于学习RxJava操作符的APP: https://github.com/jiang111/RxJavaApp

>* 展示注册进度的view: https://github.com/jiang111/ProgressView

>* 通过viewpager的滑动来对fragment内的元素进行动画操作: https://github.com/jiang111/ViewPagerTransformer

