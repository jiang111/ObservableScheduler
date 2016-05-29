package com.jyuesong.android.schedule;

import com.jyuesong.android.schedule.schedule.Scheduler;

import java.util.concurrent.Future;

/**
 * 订阅者管理器
 * Created by jiang on 5/29/16.
 */
public class SubscribeManager<T> implements Subscription {

    /**
     * Scheduler用于对线程的管理
     */
    private Scheduler mScheduler;
    /**
     * 是否订阅的状态
     */
    private boolean unsubscribed;
    /**
     * 如果是子线程执行的Observable,则用于取消作业,否则为null
     */
    private Future<?> mFuture;

    private Observer<T> mObserver;

    public Scheduler getScheduler() {
        return mScheduler;
    }

    public Observer<T> getObserver() {
        return mObserver;
    }

    public SubscribeManager() {
    }

    public SubscribeManager(Observer<T> observer) {
        mObserver = observer;
    }

    /**
     * 设置 线程管理
     *
     * @param scheduler
     */
    public void setScheduler(Scheduler scheduler) {
        mScheduler = scheduler;
    }

    /**
     * 通知observer接受数据
     *
     * @param t
     */
    public void notifyData(final T t) {
        if (!unsubscribed && mObserver != null) {
            unsubscribed = true;
            if (mScheduler != null) {
                mScheduler.execute(new Runnable() {
                    @Override
                    public void run() {
                        mObserver.notifyData(t);
                        onAfter();
                    }
                });
            } else {
                mObserver.notifyData(t);
                onAfter();
            }

        }
    }

    /**
     * 通知observer接受数据
     *
     * @param t
     */
    public void error(final Throwable t) {
        if (!unsubscribed && mObserver != null) {
            unsubscribed = true;
            if (mScheduler != null) {
                mScheduler.execute(new Runnable() {
                    @Override
                    public void run() {
                        mObserver.error(t);
                        onAfter();
                    }
                });
            } else {
                mObserver.error(t);
                onAfter();
            }
        }
    }


    public void setObserver(Observer<T> observer) {
        mObserver = observer;
    }

    public void setFuture(Future<?> future) {
        this.mFuture = future;
    }

    @Override
    public void unsubscribe() {
        if (unsubscribed == false) {
            unsubscribed = true;
            cancel(false);
        }
    }

    @Override
    public void unsubscribeNow() {
        if (unsubscribed == false) {
            unsubscribed = true;
            cancel(true);
        }
    }

    private void cancel(boolean interruptNow) {
        if (mFuture != null) {
            mFuture.cancel(interruptNow);
        }

    }

    @Override
    public boolean isUnsubscribed() {
        return unsubscribed;
    }


    /**
     * 在任务执行之前会被调用
     */
    public void onStart() {
        mObserver.onStart();
    }


    /**
     * 在任务执行之后会被调用
     */
    public void onAfter() {
        mObserver.onAfter();
    }
}
