package lwq.com.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;
import rx.subscriptions.Subscriptions;

public class Test1Activity extends AppCompatActivity {

    @BindView(R.id.test2_btn1)
    Button test2Btn1;
    @BindView(R.id.test2_btn2)
    Button test2Btn2;
    @BindView(R.id.test2_btn3)
    Button test2Btn3;
    @BindView(R.id.test2_btn4)
    Button test2Btn4;
    @BindView(R.id.test2_btn5)
    Button test2Btn5;
    @BindView(R.id.test2_btn6)
    Button test2Btn6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.test2_btn1, R.id.test2_btn2, R.id.test2_btn3, R.id.test2_btn4, R.id.test2_btn5, R.id.test2_btn6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test2_btn1:
                //基本使用
                baseUse();
                break;
            case R.id.test2_btn2:
                //测试订阅者
                subscribeTest();
                break;
            case R.id.test2_btn3:
                //测试取消订阅者
                unSubscribetest2();
                break;
            case R.id.test2_btn4:
                //测试取消订阅者
                unSubscribetest3();
                break;
            case R.id.test2_btn5:
                //测试订阅者的onError 和 onCompleted
                errorAndCompletedTest();
                break;
            case R.id.test2_btn6:
                //释放资源
                unSubscribeTest3();
                break;
        }
    }

    /**
     * 释放资源
     */
    private void unSubscribeTest3() {
        Subscription subscription = Subscriptions.create(new Action0() {
            @Override
            public void call() {
                Log.i("liweiqing", "Clean");
            }
        });
        //释放资源
        subscription.unsubscribe();
    }

    /**
     * 测试订阅者的onError 和 onCompleted
     */
    private void errorAndCompletedTest() {
        //创建观察者，Subject可以订阅一个或多个可观察对象
        Subject<Integer, Integer> subject = ReplaySubject.create();
        //订阅，Subject执行subscrible之后，调用call方法
        subject.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.i("liweiqing", "Complete!");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("liweiqing", "First: " + e.getMessage().toString());
            }

            @Override
            public void onNext(Integer integer) {
                Log.i("liweiqing", "First: " + integer);
            }
        });
        subject.onNext(0);
        subject.onNext(1);
        //订阅结束
        subject.onCompleted();
        subject.onNext(2);
    }

    /**
     * 测试取消订阅者
     */
    private void unSubscribetest3() {
        //创建观察者，Subject可以订阅一个或多个可观察对象
        Subject<Integer, Integer> subject = ReplaySubject.create();
        //订阅，Subject执行subscrible之后，调用call方法
        subject.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.i("liweiqing", "Complete");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("liweiqing", "First: " + e.getMessage().toString());
            }

            @Override
            public void onNext(Integer integer) {
                Log.i("liweiqing", "First: " + integer);
            }
        });
        //订阅，Subject执行subscrible之后，调用call方法
        Subscription subscription1 = subject.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.i("liweiqing", "Complete");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("liweiqing", "Second:" + e.getMessage().toString());
            }

            @Override
            public void onNext(Integer integer) {
                Log.i("liweiqing", "Second: " + integer);
            }
        });
        subject.onNext(0);
        subject.onNext(1);
        //取消订阅者
        subscription1.unsubscribe();
        Log.i("liweiqing", "Unsubscribed first");
        subject.onNext(2);
    }

    /**
     * 测试取消订阅者
     */
    private void unSubscribetest2() {
        //创建观察者，Subject可以订阅一个或多个可观察对象
        Subject<Integer, Integer> subject = ReplaySubject.create();
        //订阅，Subject执行subscrible之后，调用call方法
        Subscription subscription = subject.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i("liweiqing", integer + "");
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i("liweiqing", throwable.getMessage().toString());
            }
        }, new Action0() {
            @Override
            public void call() {
                Log.i("liweiqing", "Done");
            }
        });
        subject.onNext(0);
        subject.onNext(1);
        //取消订阅者
        subscription.unsubscribe();
        subject.onNext(2);
    }

    /**
     * 测试订阅者
     */
    private void subscribeTest() {
        //创建观察者，Subject可以订阅一个或多个可观察对象
        Subject<Integer, Integer> subject = ReplaySubject.create();
        //订阅，Observable执行subscrible之后，调用call方法
        subject.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i("liweiqing", integer + "");
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i("liweiqing", throwable.getMessage().toString());
            }
        });
        subject.onNext(0);
        subject.onError(new Exception("0ops"));
    }

    /**
     * 基本使用
     */
    private void baseUse() {
        //创建订阅者（Subscriber）
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i("liweiqing", "Complete!");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("liweiqing", e.getMessage().toString());
            }

            @Override
            public void onNext(String s) {
                Log.i("liweiqing", s);
            }
        };
        //创建观察者
        Observable<String> observable = rx.Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello ,RxJava");
                subscriber.onCompleted();
            }
        });
        //订阅，Observable执行subscrible之后，调用call方法
        observable.subscribe(subscriber);
    }
}
