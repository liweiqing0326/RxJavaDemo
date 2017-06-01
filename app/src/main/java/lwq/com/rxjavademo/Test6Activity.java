package lwq.com.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

public class Test6Activity extends AppCompatActivity {

    @BindView(R.id.test7_btn1)
    Button test7Btn1;
    @BindView(R.id.test7_btn2)
    Button test7Btn2;
    @BindView(R.id.test7_btn8)
    Button test7Btn8;
    @BindView(R.id.test7_btn9)
    Button test7Btn9;
    @BindView(R.id.test7_btn10)
    Button test7Btn10;
    @BindView(R.id.test7_btn11)
    Button test7Btn11;
    @BindView(R.id.test7_btn12)
    Button test7Btn12;
    @BindView(R.id.test7_btn13)
    Button test7Btn13;
    @BindView(R.id.test7_btn14)
    Button test7Btn14;
    @BindView(R.id.test7_btn15)
    Button test7Btn15;
    @BindView(R.id.test7_btn16)
    Button test7Btn16;
    @BindView(R.id.test7_btn17)
    Button test7Btn17;
    @BindView(R.id.test7_btn18)
    Button test7Btn18;
    @BindView(R.id.test7_btn19)
    Button test7Btn19;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test6);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.test7_btn1, R.id.test7_btn2, R.id.test7_btn8, R.id.test7_btn9, R.id.test7_btn10, R.id.test7_btn11, R.id.test7_btn12, R.id.test7_btn13, R.id.test7_btn14, R.id.test7_btn15, R.id.test7_btn16, R.id.test7_btn17, R.id.test7_btn18, R.id.test7_btn19})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test7_btn1://map
                map();
                break;
            case R.id.test7_btn2://cast
                cast();
                break;
            case R.id.test7_btn8://flatMap1:转换
                flatMap1();
                break;
            case R.id.test7_btn9://flatMap2:过滤
                flatMap2();
                break;
            case R.id.test7_btn10://flatMap3:异步
                flatMap3();
                break;
            case R.id.test7_btn11://concatMap
                concatMap();
                break;
            case R.id.test7_btn12://switchMap
                switchMap();
                break;
            case R.id.test7_btn13://flatMapIterable1
                flatMapIterable1();
                break;
            case R.id.test7_btn14://flatMapIterable2
                flatMapIterable2();
                break;
            case R.id.test7_btn15://buffer
                buffer();
                break;
            case R.id.test7_btn16://window
                window();
                break;
            case R.id.test7_btn17://scan1
                scan1();
                break;
            case R.id.test7_btn18://scan2
                scan2();
                break;
            case R.id.test7_btn19://groupBy
                groupBy();
                break;
        }
    }

    /**
     * map转换操作符
     * 将Observable发射的数据转换，将这个转换后的数据提交给订阅者
     */
    private void map() {
        Observable<Integer> values = Observable.just("0", "1", "2", "3")
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return Integer.parseInt(s);
                    }
                });
        values.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log(integer + "");
            }
        });

    }

    /**
     * cast转换操作符
     * 主要是做类型转换的
     * 判断Observable发射的数据的数据类型
     */
    private void cast() {
        Observable<String> values = Observable.just("0", "1", "2", "3");
        values.cast(Object.class)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object object) {
                        log(object.toString() + ":" + object.getClass());
                    }
                });
    }

    /**
     * flatMap转换操作符
     */
    private void flatMap1() {
        Observable.range(1, 3)
                .flatMap(new Func1<Integer, Observable<?>>() {
                    @Override
                    public Observable<?> call(Integer integer) {
                        return Observable.range(0, integer);
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
        Observable.just(1)
                .flatMap(new Func1<Integer, Observable<?>>() {
                    @Override
                    public Observable<?> call(Integer integer) {
                        return Observable.just(Character.valueOf((char) (integer + 64)));
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    /**
     * 转换操作符
     */
    private void flatMap2() {
        Observable.range(0, 30)
                .flatMap(new Func1<Integer, Observable<?>>() {
                    @Override
                    public Observable<?> call(Integer integer) {
                        if (0 < integer && integer <= 26)
                            return Observable.just(Character.valueOf((char) (integer + 64)));
                        else
                            return Observable.empty();
                    }
                })
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onCompleted() {
                        log("Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Object o) {
                        log(o.toString());
                    }
                });
    }

    /**
     * 转换操作符
     */
    private void flatMap3() {
        Observable.just(100, 150)
                .flatMap(
                        new Func1<Integer, Observable<?>>() {
                            @Override
                            public Observable<?> call(final Integer integer) {
                                return Observable.interval(integer, TimeUnit.MILLISECONDS)
                                        .map(new Func1<Long, Object>() {
                                            @Override
                                            public Object call(Long aLong) {
                                                return integer;
                                            }
                                        });
                            }
                        })
                .take(6)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    /**
     * concatMap转换操作符
     * 次序连接那些生成的Observables，然后产生自己的数据序列，
     */
    private void concatMap() {
        Observable.just(100, 150)
                .concatMap(new Func1<Integer, Observable<?>>() {
                    @Override
                    public Observable<?> call(final Integer integer) {
                        return Observable.interval(integer, TimeUnit.MILLISECONDS)
                                .map(new Func1<Long, Object>() {
                                    @Override
                                    public Object call(Long aLong) {
                                        return integer;
                                    }
                                })
                                .take(3);
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    /**
     * 转换操作符
     */
    private void switchMap() {
        //flatMap操作符的运行结果
        Observable.just(10, 22, 34)
                .flatMap(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer integer) {
                        //10的延迟执行时间为200毫秒、20和30的延迟执行时间为180毫秒
                        int delay = 200;
                        if (integer > 10)
                            delay = 180;
                        return Observable.from(new Integer[]{integer, integer / 2}).delay(delay, TimeUnit.MILLISECONDS);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log("flatMap Next:" + integer);
                    }
                });

        //concatMap操作符的运行结果
        Observable.just(10, 22, 34)
                .concatMap(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer integer) {
                        //10的延迟执行时间为200毫秒、20和30的延迟执行时间为180毫秒
                        int delay = 200;
                        if (integer > 10)
                            delay = 180;
                        return Observable.from(new Integer[]{integer, integer / 2}).delay(delay, TimeUnit.MILLISECONDS);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log("concatMap Next:" + integer);
                    }
                });

        //switchMap操作符的运行结果
        Observable.just(10, 22, 34)
                .switchMap(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer integer) {
                        //10的延迟执行时间为200毫秒、20和30的延迟执行时间为180毫秒
                        int delay = 200;
                        if (integer > 10)
                            delay = 180;
                        return Observable.from(new Integer[]{integer, integer / 2}).delay(delay, TimeUnit.MILLISECONDS);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log("switchMap Next:" + integer);
                    }
                });
    }

    /**
     * 转换操作符
     */
    private void flatMapIterable1() {
        Observable.range(1, 3)
                .flatMapIterable(new Func1<Integer, Iterable<?>>() {
                    @Override
                    public Iterable<?> call(Integer integer) {
                        List<Integer> list = new ArrayList<>();
                        for (int i = 1; i <= integer; i++) {
                            list.add(i);
                        }
                        return list;
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    /**
     * flatMapIterable转换操作符
     */
    private void flatMapIterable2() {
        Observable.range(1, 3)
                .flatMapIterable(new Func1<Integer, Iterable<?>>() {
                    @Override
                    public Iterable<?> call(Integer integer) {
                        List<Integer> list = new ArrayList<>();
                        for (int i = 1; i <= integer; i++) {
                            list.add(i);
                        }
                        return list;
                    }
                }, new Func2<Integer, Object, Object>() {
                    @Override
                    public Object call(Integer integer, Object o) {
                        return integer * (Integer) o;
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    /**
     * buffer转换操作符
     * 定期收集Observable的数据放进一个数据包裹，然后发射这些数据包裹，而不是一次发射一个值。
     */
    private void buffer() {
        final int[] items = new int[]{1, 3, 5, 7, 9};
        Observable<Integer> observable = Observable.create(
                new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        try {
                            if (subscriber.isUnsubscribed()) return;
                            Random random = new Random();
                            while (true) {
                                int i = items[random.nextInt(items.length)];
                                subscriber.onNext(i);
                                Thread.sleep(1000);
                            }
                        } catch (InterruptedException e) {
                            subscriber.onError(e);
                        }
                    }
                })
                .subscribeOn(Schedulers.io());

        observable.buffer(3, TimeUnit.SECONDS)
                .subscribe(new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> integers) {
                        log(integers.toString());
                    }
                });
    }

    /**
     * 转换操作符
     */
    private void window() {
        Observable.interval(1, TimeUnit.SECONDS)
                .take(12)
                .window(3, TimeUnit.SECONDS)
                .subscribe(new Action1<Observable<Long>>() {
                    @Override
                    public void call(Observable<Long> observable) {
                        log("subdivide begin......");
                        observable.subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                log("Next:" + aLong);
                            }
                        });
                    }
                });
    }

    /**
     * scan转换操作符
     * 通过遍历原来Observable产生的结果，每一次对每一个结果按照指定规则进行运算
     */
    private void scan1() {
        Observable<Integer> values = Observable.range(0, 5);
        values
                .scan(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        return integer + integer2;
                    }
                })
//                .takeLast()//实现reduce
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        log("Sum:Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("Sum:" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        log("Sum:" + integer);
                    }
                });
    }

    /**
     * scan转换操作符
     * 通过遍历原来Observable产生的结果，每一次对每一个结果按照指定规则进行运算
     */
    private void scan2() {
        Subject<Integer, Integer> values = ReplaySubject.create();
        values
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        log("Values:Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("Values:" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        log("Values:" + integer);
                    }
                });
        values
                .scan(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        return (integer < integer2) ? integer : integer2;
                    }
                })
                .distinctUntilChanged()
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        log("Min:Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("Min:" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        log("Min:" + integer);
                    }
                });
        values.onNext(2);
        values.onNext(3);
        values.onNext(1);
        values.onNext(4);
        values.onCompleted();
    }

    /**
     * groupBy转换操作符
     */
    private void groupBy() {
        Observable<String> values = Observable.just(
                "first",
                "second",
                "third",
                "forth",
                "fifth",
                "sixth"
        );
        values
                .groupBy(new Func1<String, Object>() {
                    @Override
                    public Object call(String s) {
                        return s.charAt(0);
                    }
                })
                .subscribe(new Action1<GroupedObservable<Object, String>>() {
                    @Override
                    public void call(final GroupedObservable<Object, String> result) {
                        result.subscribe(new Action1<String>() {
                            @Override
                            public void call(String value) {
                                log("key:" + result.getKey() + ", value:" + value);
                            }
                        });
                    }
                });
    }


    private void log(String string) {
        Log.d("liweiqing", string);
    }
}
