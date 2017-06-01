package lwq.com.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class Test3Activity extends AppCompatActivity {

    @BindView(R.id.test4_btn1)
    Button test4Btn1;
    @BindView(R.id.test4_btn2)
    Button test4Btn2;
    @BindView(R.id.test4_btn3)
    Button test4Btn3;
    @BindView(R.id.test4_btn4)
    Button test4Btn4;
    @BindView(R.id.test4_btn5)
    Button test4Btn5;
    @BindView(R.id.test4_btn6)
    Button test4Btn6;
    @BindView(R.id.test4_btn7)
    Button test4Btn7;
    @BindView(R.id.test4_btn8)
    Button test4Btn8;
    @BindView(R.id.test4_btn9)
    Button test4Btn9;
    @BindView(R.id.test4_btn10)
    Button test4Btn10;
    @BindView(R.id.test4_btn11)
    Button test4Btn11;
    @BindView(R.id.test4_btn12)
    Button test4Btn12;
    @BindView(R.id.test4_btn13)
    Button test4Btn13;
    @BindView(R.id.test4_btn14)
    Button test4Btn14;
    @BindView(R.id.test4_btn15)
    Button test4Btn15;
    @BindView(R.id.test4_btn16)
    Button test4Btn16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.test4_btn1, R.id.test4_btn2, R.id.test4_btn3, R.id.test4_btn4, R.id.test4_btn5, R.id.test4_btn6, R.id.test4_btn7, R.id.test4_btn8, R.id.test4_btn9, R.id.test4_btn10, R.id.test4_btn11, R.id.test4_btn12, R.id.test4_btn13, R.id.test4_btn14, R.id.test4_btn15, R.id.test4_btn16})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test4_btn1://filter过滤操作符
                filter();
                break;
            case R.id.test4_btn2://distinct过滤操作符
                distinct1();
                break;
            case R.id.test4_btn3://distinct(keySelector)过滤操作符
                distinct2();
                break;
            case R.id.test4_btn4://distinctUntilChanged过滤操作符
                distinctUntilChanged1();
                break;
            case R.id.test4_btn5://distinctUntilChanged(keySelector)过滤操作符
                distinctUntilChanged2();
                break;
            case R.id.test4_btn6://firstAndLast过滤操作符
                firstAndLast();
                break;
            case R.id.test4_btn7://ignoreElements过滤操作符
                ignoreElements();
                break;
            case R.id.test4_btn8://takeAndSkip过滤操作符
                takeAndSkip();
                break;
            case R.id.test4_btn9://takeLastAndSkipLast过滤操作符
                takeLastAndSkipLast();
                break;
            case R.id.test4_btn10://takeWhileAndSkipWhile过滤操作符
                takeWhileAndSkipWhile();
                break;
            case R.id.test4_btn11://takeUntilAndSkipUntil过滤操作符
                takeUntilAndSkipUntil();
                break;
            case R.id.test4_btn12://elementAt过滤操作符
                elementAt();
                break;
            case R.id.test4_btn13://sample过滤操作符
                sample();
                break;
            case R.id.test4_btn14://throttleFirst过滤操作符
                throttleFirst();
                break;
            case R.id.test4_btn15://throttleLast过滤操作符
                throttleLast();
                break;
            case R.id.test4_btn16://debounce过滤操作符
                debounce();
                break;
        }
    }

    /**
     * sample过滤操作符
     * 对Observable发射的数据定时进行采样，采的是这段时间间隔中最后一个数据项。
     */
    private void sample() {
        Observable.interval(150, TimeUnit.MILLISECONDS)
                .sample(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.i("liweiqing", aLong + "");
                    }
                });
    }

    /**
     * debounce过滤操作符
     */
    private void debounce() {
        Observable.concat(
                Observable.interval(100, TimeUnit.MILLISECONDS).take(3),
                Observable.interval(500, TimeUnit.MILLISECONDS).take(3),
                Observable.interval(100, TimeUnit.MILLISECONDS).take(3))
                .scan(0, new Func2<Integer, Long, Integer>() {
                    @Override
                    public Integer call(Integer integer, Long aLong) {
                        return integer + 1;
                    }
                })
                .debounce(150, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i("liweiqing", integer + "");
                    }
                });
    }

    /**
     * throttleLast过滤操作符
     */
    private void throttleLast() {
        Observable.interval(150, TimeUnit.MILLISECONDS)
                .throttleLast(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.i("liweiqing", aLong + "");
                    }
                });
    }

    /**
     * throttleFirst过滤操作符
     */
    private void throttleFirst() {
        Observable
                .interval(150, TimeUnit.MILLISECONDS)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.i("liweiqing", aLong + "");
                    }
                });
    }

    /**
     * elementAt过滤操作符
     * 将指定索引的数据项提交给订阅者，索引是从0开始，当没有这个索引或者索引为负数会抛异常。
     */
    private void elementAt() {
        Observable.range(0, 10)
                .elementAt(3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.i("liweiqing", "Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("liweiqing", e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("liweiqing", integer + "");
                    }
                });

        Observable.range(0, 10)
                .elementAtOrDefault(11, 5)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.i("liweiqing", "Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("liweiqing", e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("liweiqing", integer + "");
                    }
                });
    }

    /**
     * takeUntil过滤操作符
     * <p>
     * SkipUntil过滤操作符
     */
    private void takeUntilAndSkipUntil() {
        Observable<Long> cutoff = Observable.timer(250, TimeUnit.MILLISECONDS);

        Observable.interval(100, TimeUnit.MILLISECONDS)
                .skipUntil(cutoff)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        Log.i("liweiqing", "Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("liweiqing", e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.i("liweiqing", aLong + "");
                    }
                });

        Observable.interval(100, TimeUnit.MILLISECONDS)
                .takeUntil(cutoff)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        Log.i("liweiqing", "Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("liweiqing", e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.i("liweiqing", aLong + "");
                    }
                });
    }

    /**
     * takeWhile过滤操作符
     * 对于源Observable发射的数据项，提取前面的count项数据提交给订阅者
     * <p>
     * SkipWhile过滤操作符
     * 对于源Observable发射的数据项，跳过前count项，将后面的数据项提交给订阅者
     */
    private void takeWhileAndSkipWhile() {
        //takeWhile过滤操作符
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .takeWhile(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long aLong) {
                        return aLong < 2;
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        Log.i("liweiqing", "Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("liweiqing", e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.i("liweiqing", aLong + "");
                    }
                });
        //SkipWhile过滤操作符
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .skipWhile(new Func1<Long, Boolean>() {

                    @Override
                    public Boolean call(Long aLong) {
                        return aLong < 2;
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        Log.i("liweiqing", "Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("liweiqing", e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.i("liweiqing", aLong + "");
                    }
                });
    }

    /**
     * takeLast过滤操作符
     * 对于源Observable发射的数据项，提取前面的count项数据提交给订阅者，忽略后面的
     * <p>
     * SkipLast过滤操作符
     * 对于源Observable发射的数据项，省略最后count项，将前面的数据项提交给订阅者
     */
    private void takeLastAndSkipLast() {
        Observable<Integer> observable = Observable.range(0, 5);
        //SkipLast过滤操作符
        observable.skipLast(2)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.i("liweiqing", "Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("liweiqing", e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("liweiqing", integer + "");
                    }
                });
        //takeLast过滤操作符
        Observable.just(1, 2, 3, 4)
                .takeLast(2)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i("liweiqing", "takeLast(count) onNext:" + integer + " 所在线程：" + Thread.currentThread().getName());
                    }
                });
        //takeLast过滤操作符
        Observable
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        if (subscriber.isUnsubscribed()) return;
                        try {
                            for (int i = 1; i < 5; i++) {
                                subscriber.onNext(i);
                                Thread.sleep(1000);
                            }
                            subscriber.onCompleted();
                        } catch (InterruptedException e) {
                            subscriber.onError(e);
                        }
                    }
                })
                .takeLast(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i("liweiqing", "takeLast(long,TimeUnit) onNext:" + integer + " 所在线程：" + Thread.currentThread().getName());
                    }
                });
    }

    /**
     * take过滤操作符
     * 对于源Observable发射的数据项，提取前面的count项数据提交给订阅者
     * <p>
     * skip过滤操作符
     * 对于源Observable发射的数据项，跳过前count项，将后面的数据项提交给订阅者
     */
    private void takeAndSkip() {
        //take测试
        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onError(new Exception("Oops"));
                subscriber.onNext(2);
            }
        });
        // take过滤操作符
        observable.take(2)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.i("liweiqing", "Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("liweiqing", e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("liweiqing", integer + "");
                    }
                });

        //skip测试
        Observable<Integer> values = Observable.range(0, 5);
        values.skip(2)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.i("liweiqing", "Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("liweiqing", e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("liweiqing", integer + "");
                    }
                });
        //重载方法测试
//        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);
//        Subscription subscription = values
//                .take(250, TimeUnit.MILLISECONDS)
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onCompleted() {
//                        log("Complete!");
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        log(e.getMessage().toString());
//                    }
//                    @Override
//                    public void onNext(Long aLong) {
//                        log(aLong+"");
//                    }
//                });
    }

    /**
     * ignoreElements过滤操作符
     * 不提交任何数据给订阅者，只提交终止通知（onError或者onCompeleted）给订阅者，
     * 默认不在任何特定的调度器上执行
     */
    private void ignoreElements() {
        Observable<Integer> observable = Observable.range(0, 10);
        observable.ignoreElements()
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.i("liweiqing", "Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("liweiqing", e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("liweiqing", integer + "");
                    }
                });
    }

    /**
     * last过滤操作符
     * 只提交最后一个数据项给订阅者，如果只是作为过滤操作符，最好使用takeLast(1)
     * <p>
     * first过滤操作符
     * 提交源Observable发射的第一项数据，如果只是想要一个过滤符，最好使用take(2)或者elementAt(0)
     */
    private void firstAndLast() {
        Integer[] items = new Integer[]{1, 2, 3};
        List<Integer> list = Arrays.asList(items);
        //last过滤操作符
        Observable.from(list)
                .last()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i("liweiqing", integer + "");
                    }
                });
        //first过滤操作符
        Observable.from(list)
                .first()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i("liweiqing", integer + "");
                    }
                });
    }

    /**
     * distinct过滤操作符
     * 过滤掉重复的数据项。过滤规则是只允许没有发射过的数据项通过
     */
    private void distinctUntilChanged2() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("First");
                subscriber.onNext("Second");
                subscriber.onNext("Third");
                subscriber.onNext("Fourth");
                subscriber.onNext("Fifth");
                subscriber.onCompleted();
            }
        });
        observable
                .distinctUntilChanged(new Func1<String, Object>() {
                    @Override
                    public Object call(String s) {
                        return s.charAt(0);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i("liweiqing", s);
                    }
                });
    }

    /**
     * distinct过滤操作符
     * 过滤掉重复的数据项。过滤规则是只允许没有发射过的数据项通过
     */
    private void distinctUntilChanged1() {
        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onNext(2);
                subscriber.onCompleted();
            }
        });
        observable.distinctUntilChanged()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i("liweiqing", integer + "");
                    }
                });
    }

    /**
     * distinct过滤操作符
     * 过滤掉重复的数据项。过滤规则是只允许没有发射过的数据项通过
     */
    private void distinct2() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("First");
                subscriber.onNext("Second");
                subscriber.onNext("Third");
                subscriber.onNext("Fourth");
                subscriber.onNext("Fifth");
                subscriber.onCompleted();
            }
        });
        observable
                .distinct(new Func1<String, Object>() {
                    @Override
                    public Object call(String s) {
                        return s.charAt(0);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i("liweiqing", s);
                    }
                });
    }

    /**
     * distinct过滤操作符
     * 过滤掉重复的数据项。过滤规则是只允许没有发射过的数据项通过
     */
    private void distinct1() {
        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {

            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onNext(2);
                subscriber.onCompleted();
            }
        });
        observable.distinct()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i("liweiqing", integer + "");
                    }
                });
    }

    /**
     * Filter操作符
     * 对源Observable发射的数据项按照指定的条件进行过滤，满足的条件的才会调给订阅者
     */
    private void filter() {
        Observable<Integer> observable = Observable.range(0, 10);
        observable
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 == 0;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.i("liweiqing", "Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("liweiqing", e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("liweiqing", integer + "");
                    }
                });
    }
}
