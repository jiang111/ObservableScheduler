# ObservableScheduler

你的app是不是经常做一些很耗费是工作,但这些工作都是在主线程完成的,因为开线程然后回调主线程很麻烦的说。当然,我们可以用RxJava轻松实现，
但是如果你的项目没有集成RxJava怎么办,集成进去? 如果集成RxJava只为做这些事岂不是太浪费了。你最好集成RxAndroid,RxBus,RxLifecycle,RxBinding
是不是感觉要改架构了😂

ObservableScheduler 最主要的目的就是轻松的帮你在子线程和主线程之间做转换, 项目的思想来自RxJava,代码很有可能也是模仿RxJava,没关系,人家牛我们就得模仿。


##Demo:
![](https://raw.githubusercontent.com/jiang111/ObservableScheduler/master/art/art.gif)

##用法:
###第一步:
gradle:
```
compile 'com.jiang.android.observablescheduler:schedule:1.0.0'
```
Maven:
```
<dependency>
  <groupId>com.jiang.android.observablescheduler</groupId>
  <artifactId>schedule</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

###第二步:
