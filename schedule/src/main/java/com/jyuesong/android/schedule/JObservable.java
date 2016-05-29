package com.jyuesong.android.schedule;


import com.jyuesong.android.schedule.schedule.Scheduler;

/**
 * Observable 工作类
 * Created by jiang on 5/29/16.
 */
public class JObservable<T> {

    private OnSubscribe<T> mOnSubscribe;
    private SubscribeManager<T> mSubscriber;
    /**
     * Subscribe工作的类
     */
    private Scheduler mSubscribeScheduler;
    /**
     * Observable工作的类
     */
    private Scheduler mWorkScheduler;


    public JObservable(OnSubscribe<T> onSubscribe) {
        mOnSubscribe = onSubscribe;
    }

    /**
     * 当开始调用subscribe之后，才真正的开始工作.
     * 如果subscribe为null，也是可以执行OnSubscribe的call方法的，并且可以指定执行的线程,只是subscriber相关的功能都不能用
     *
     * @param subscriber
     * @return
     */
    public Subscription subscribe(Observer<T> subscriber) {
        mSubscriber = new SubscribeManager<T>(subscriber);
        mSubscriber.setScheduler(mSubscribeScheduler);
        if (mSubscribeScheduler != null) {
            mSubscribeScheduler.execute(new Runnable() {
                @Override
                public void run() {
                    mSubscriber.onStart();
                }
            });
        } else {
            mSubscriber.onStart();
        }
        if (mWorkScheduler != null) {
            mSubscriber.setFuture(mWorkScheduler.submit(new Runnable() {
                @Override
                public void run() {
                    mOnSubscribe.call(mSubscriber);
                }
            }));
        } else {
            mOnSubscribe.call(mSubscriber);
        }
        return mSubscriber;
    }

    public static <T> JObservable<T> create(OnSubscribe<T> onSubscribe) {
        if (onSubscribe == null) {
            throw new NullPointerException("onSubscribe can not be null");
        }
        return new JObservable<T>(onSubscribe);
    }

    /**
     * 指定Subscribe执行的线程
     *
     * @param scheduler
     * @return
     */
    public JObservable<T> subscribeOn(Scheduler scheduler) {
        mSubscribeScheduler = scheduler;
        return this;
    }

    /**
     * 指定Observable执行的线程
     *
     * @param scheduler
     * @return
     */
    public JObservable<T> workedOn(Scheduler scheduler) {
        mWorkScheduler = scheduler;
        return this;
    }


    public interface OnSubscribe<T> {
        void call(SubscribeManager<T> mSubscriber);
    }

}
