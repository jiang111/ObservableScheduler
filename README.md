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

### License

    Copyright 2016 NewTab

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
