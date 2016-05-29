/**
 * created by jiang, 5/29/16
 * Copyright (c) 2016, jyuesong@gmail.com All Rights Reserved.
 * *                #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #                                                   #
 */

package com.jyuesong.android.schedule.schedule;

import android.os.Handler;

import java.util.concurrent.Future;

/**
 * 用于回调到主线程的Scheduler
 * Created by jiang on 5/29/16.
 */
public class MainScheduler extends Scheduler {

    private Handler mHandler;

    public MainScheduler() {
    }

    public MainScheduler(Handler handler) {
        mHandler = handler;
    }

    public Handler getHandler() {
        return mHandler;
    }

    public void setHandler(Handler handler) {
        mHandler = handler;
    }

    @Override
    public void execute(Runnable runnable) {
        if (mHandler == null) {
            runnable.run();
        } else {
            mHandler.post(runnable);
        }
    }

    @Override
    public Future<?> submit(Runnable runnable) {
        execute(runnable);
        return null;
    }
}
