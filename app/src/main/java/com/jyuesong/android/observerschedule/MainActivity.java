package com.jyuesong.android.observerschedule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jyuesong.android.schedule.JObservable;
import com.jyuesong.android.schedule.Subscribe;
import com.jyuesong.android.schedule.SubscribeManager;
import com.jyuesong.android.schedule.Subscription;
import com.jyuesong.android.schedule.schedule.Schedules;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "JObservable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = (TextView) findViewById(R.id.tv);

        Schedules.init(5);

        final Subscription subscription = JObservable.create(new JObservable.OnSubscribe<String>() {
            @Override
            public void call(SubscribeManager<String> mSubscriber) {
                try {
                    Thread.sleep(5000);
                    int i = 1 / 0;
                    mSubscriber.notifyData("call result 2 subscriber");
                } catch (Exception e) {
                    mSubscriber.error(e);
                } finally {
                }
            }
        }).workedOn(Schedules.background()).subscribeOn(Schedules.mainThread()).subscribe(new Subscribe<String>() {
            @Override
            public void notifyData(String s) {
                tv.setText(s);
            }

            @Override
            public void error(Throwable t) {
                tv.setText(t.getMessage());
            }
        });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(5000);
//                    subscription.unsubscribeNow();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }
}
