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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by jiang on 5/29/16.
 */
public class WorkScheduler extends Scheduler {
    public ExecutorService fixedThreadPool;


    public WorkScheduler() {
    }

    public WorkScheduler(ExecutorService fixedThreadPool) {
        this.fixedThreadPool = fixedThreadPool;
    }

    public ExecutorService getFixedThreadPool() {
        return fixedThreadPool;
    }

    public void setFixedThreadPool(ExecutorService fixedThreadPool) {
        this.fixedThreadPool = fixedThreadPool;
    }

    @Override
    public void execute(Runnable runnable) {
        if (fixedThreadPool == null) {
            runnable.run();
        } else {
            fixedThreadPool.execute(runnable);
        }

    }

    @Override
    public Future<?> submit(Runnable runnable) {
        if (fixedThreadPool == null) {
            runnable.run();
            return null;
        } else {
            return fixedThreadPool.submit(runnable);
        }
    }
}
