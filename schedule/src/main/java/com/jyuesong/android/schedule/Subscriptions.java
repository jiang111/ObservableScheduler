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

package com.jyuesong.android.schedule;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 内部维护了LinkedList的集合。
 * Created by jiang on 5/29/16.
 */
public class Subscriptions {


    private LinkedList<Subscription> subscriptions;

    public Subscriptions() {
    }

    public Subscriptions(final Subscription... subscriptions) {
        this.subscriptions = new LinkedList<>(Arrays.asList(subscriptions));
    }

    public Subscriptions(Subscription s) {
        this.subscriptions = new LinkedList<>();
        this.subscriptions.add(s);
    }


    public void add(final Subscription s) {
        synchronized (this) {
            LinkedList<Subscription> subs = subscriptions;
            if (subs == null) {
                subs = new LinkedList<Subscription>();
                subscriptions = subs;
            }
            subs.add(s);
            return;
        }

    }

    public void unsubscribeNow() {
        unSubscribeList(true);
    }

    public void unsubscribe() {
        unSubscribeList(false);
    }

    private void unSubscribeList(boolean now) {
        List<Subscription> list;
        synchronized (this) {
            list = subscriptions;
            subscriptions = null;
        }
        unsubscribeFromAll(list, now);
    }

    private static void unsubscribeFromAll(Collection<Subscription> subscriptions, boolean now) {
        if (subscriptions == null) {
            return;
        }
        for (Subscription s : subscriptions) {
            try {
                if (!s.isUnsubscribed())
                    if (now)
                        s.unsubscribeNow();
                    else
                        s.unsubscribe();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public void clear() {
        clearList(false);
    }

    public void clearNow() {
        clearList(true);
    }

    public void clearList(boolean now) {
        List<Subscription> list;
        synchronized (this) {
            list = subscriptions;
            subscriptions = null;
        }
        unsubscribeFromAll(list, now);
    }

}
