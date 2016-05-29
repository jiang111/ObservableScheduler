package com.jyuesong.android.observerschedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "JObservable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = (TextView) findViewById(R.id.tv);
        tv.setClickable(true);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Test1Activity.class));

            }
        });
//        Schedules.init(5);
//
//        final Subscription subscription = JObservable.create(new JObservable.OnSubscribe<String>() {
//            @Override
//            public void call(SubscribeManager<String> mSubscriber) {
//                try {
//                    Thread.sleep(5000);
//                    int i = 1 / 0;
//                    mSubscriber.notifyData("call result 2 subscriber");
//                } catch (Exception e) {
//                    mSubscriber.error(e);
//                } finally {
//                }
//            }
//        }).workedOn(Schedules.background()).subscribeOn(Schedules.mainThread()).subscribe(new Subscribe<String>() {
//            @Override
//            public void notifyData(String s) {
//                tv.setText(s);
//            }
//
//            @Override
//            public void error(Throwable t) {
//                tv.setText(t.getMessage());
//            }
//        });

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
