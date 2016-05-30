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
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程调度的配置类，这里你可以自己实现Scheduler,只需要继承 Scheduler即可,如果你不需要submit返回的Futrue对象的话，可以直接return null;
 * Created by jiang on 5/29/16.
 */
public class Schedules {
    private static int THREAD_SIZE = 5;

    /**
     * Observable用的线程调度器
     */
    private static Scheduler mWorkScheduler;

    /**
     * Subscriber用的线程调度器
     */
    private static Scheduler mMainScheduler;


    /**
     * 初始化线程池的数量
     *
     * @param thread_size
     */
    public static void init(int thread_size) {
        THREAD_SIZE = thread_size;
    }

    /**
     * 初始化配置Observable用的线程调度器的线程池
     *
     * @param executorService
     */
    public static void init(ExecutorService executorService) {
        if (executorService != null)
            mWorkScheduler = new WorkScheduler(executorService);
    }

    public static void setWorkScheduler(Scheduler mWorkScheduler) {
        Schedules.mWorkScheduler = mWorkScheduler;
    }

    public static void setMainScheduler(Scheduler mMainScheduler) {
        Schedules.mMainScheduler = mMainScheduler;
    }

    public static Scheduler background() {
        synchronized (Schedules.class) {
            if (mWorkScheduler == null) {
                mWorkScheduler = new WorkScheduler(Executors.newFixedThreadPool(THREAD_SIZE));
            }
        }
        return mWorkScheduler;
    }


    public static Scheduler mainThread() {
        synchronized (Schedules.class) {
            if (mMainScheduler == null) {
                mMainScheduler = new MainScheduler(new Handler(Looper.getMainLooper()));
            }
        }
        return mMainScheduler;
    }

}
