package lwq.com.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
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

public class Test4Activity extends AppCompatActivity {

    @BindView(R.id.test5_btn1)
    Button test5Btn1;
    @BindView(R.id.test5_btn2)
    Button test5Btn2;
    @BindView(R.id.test5_btn3)
    Button test5Btn3;
    @BindView(R.id.test5_btn4)
    Button test5Btn4;
    @BindView(R.id.test5_btn5)
    Button test5Btn5;
    @BindView(R.id.test5_btn6)
    Button test5Btn6;
    @BindView(R.id.test5_btn7)
    Button test5Btn7;
    @BindView(R.id.test5_btn8)
    Button test5Btn8;
    @BindView(R.id.test5_btn9)
    Button test5Btn9;
    @BindView(R.id.test4_btn10)
    Button test4Btn10;
    @BindView(R.id.test4_btn11)
    Button test4Btn11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test4);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.test5_btn1, R.id.test5_btn2, R.id.test5_btn3, R.id.test5_btn4, R.id.test5_btn5, R.id.test5_btn6, R.id.test5_btn7, R.id.test5_btn8, R.id.test5_btn9, R.id.test4_btn10, R.id.test4_btn11})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test5_btn1://all:满足条件
                all1();
                break;
            case R.id.test5_btn2://all:不满足条件
                all2();
                break;
            case R.id.test5_btn3://all:数据错误
                all3();
                break;
            case R.id.test5_btn4://exists
                exists();
                break;
            case R.id.test5_btn5://isEmpty
                isEmpty();
                break;
            case R.id.test5_btn6://contains
                contains();
                break;
            case R.id.test5_btn7://defaultIfEmpty
                defaultIfEmpty();
                break;
            case R.id.test5_btn8://sequenceEqual
                sequenceEqual();
                break;
            case R.id.test5_btn9://takeLastAndSkipLast
                break;
            case R.id.test4_btn10://ofType
                ofType();
                break;
            case R.id.test4_btn11://single
                single();
                break;
        }
    }

    /**
     * all条件和布尔操作符
     * 满足条件
     * 判断源Observble发射的每一项数据，如果都满足条件的话就给订阅者返回一个true，
     * 如果有一个数据项不满足条件就给订阅者返回一个false
     */
    private void all1() {
        Observable<Integer> values = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(0);
                subscriber.onNext(10);
                subscriber.onNext(20);
                subscriber.onNext(2);
                subscriber.onCompleted();
            }
        });

        values
                .all(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 == 0;
                    }
                })
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        log("Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        log(aBoolean + "");
                    }
                });

    }

    /**
     * all条件和布尔操作符
     * 不满足条件
     * 判断源Observble发射的每一项数据，如果都满足条件的话就给订阅者返回一个true，
     * 如果有一个数据项不满足条件就给订阅者返回一个false
     */
    private void all2() {
        Observable<Long> values = Observable.interval(150, TimeUnit.MILLISECONDS).take(5);
        values
                .all(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long aLong) {
                        return aLong < 3;
                    }
                })
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        log("First:Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("First:" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        log("First:" + aBoolean);
                    }
                });
        values
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        log("Second:Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("Second:" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        log("Second:" + aLong);
                    }
                });
    }

    /**
     * all条件和布尔操作符
     * 数据错误
     * 判断源Observble发射的每一项数据，如果都满足条件的话就给订阅者返回一个true，
     * 如果有一个数据项不满足条件就给订阅者返回一个false
     */
    private void all3() {
        Observable<Integer> values = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(0);
//                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onError(new Exception("Oops"));
            }
        });
        values
                .all(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 == 0;
                    }
                })
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        log("Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        log(aBoolean + "");
                    }
                });
    }

    /**
     * ofType条件和布尔操作符
     * 判断源Observble发射的每一项数据的类型
     */
    private void ofType() {
        Observable.just(0, "1", 2, "3")
                .ofType(String.class)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object object) {
                        log(object.toString() + ":" + object.getClass());
                    }
                });
    }

    /**
     * single条件和布尔操作符
     * 获取源Observble发射的某一项数据
     */
    private void single() {
        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);
        values.take(10) // 获取前 10 个数据 的 Observable
                .single(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long aLong) {
                        return aLong == 5L;
                    }
                })  // 有且仅有一个 数据为 5L
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        log("Single1:Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("Single1:" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        log("Single1:" + aLong);
                    }
                });
        values
                .single(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long aLong) {
                        return aLong == 5L;
                    }
                }) // 由于源 Observable 为无限的，所以这个不会打印任何东西
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        log("Single2:Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("Single2:" + e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        log("Single2:" + aLong);
                    }
                });
    }

    /**
     * exists条件和布尔操作符
     * 通过一个函数来判断源Observable是否发射过指定的数据项，
     * 如果发射过，操作符就给订阅者返回一个true,否则false
     */
    private void exists() {
        Observable.range(0, 2)
                .exists(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 2;
                    }
                })
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        log("Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        log(aBoolean + "");
                    }
                });
    }

    /**
     * isEmpty条件和布尔操作符
     * 判断源Observable是否发射过数据，如果没有发射过数据，
     * 操作符就给订阅者返回一个true,否则就返回false
     */
    private void isEmpty() {
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .isEmpty()
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        log("Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        log(aBoolean + "");
                    }
                });
    }

    /**
     * contains条件和布尔操作符
     * 传入一个指定数据项，如果源Observbale发射过这个数据项，
     * 操作符将给订阅者返回一个true，否则就返回一个false
     */
    private void contains() {
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .contains(4L)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        log("Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        log(aBoolean + "");
                    }
                });
    }

    /**
     * defaultIfEmpty条件和布尔操作符
     * 判断源Observable是否发射过某一项数据，如果没有发射过数据，
     * 操作符就给订阅者返回一个true,否则就返回false
     */
    private void defaultIfEmpty() {
        Observable<Integer> values = Observable.empty();
        values.defaultIfEmpty(2)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        log("Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        log(integer + "");
                    }
                });
    }

    /**
     * sequenceEqual条件和布尔操作符
     * 比较两个Observable发射的数据项，
     * 如果相同的数据，相同的顺序，相同的终止状态，操作符就给订阅者返回true，否则返回false，
     */
    private void sequenceEqual() {
        Observable<String> strings = Observable.just("1", "2", "3");
        Observable<Integer> ints = Observable.just(1, 2, 3);
        Observable
                .sequenceEqual(strings, ints, new Func2<Serializable, Serializable, Boolean>() {
                    @Override
                    public Boolean call(Serializable serializable, Serializable serializable2) {
                        return serializable.equals(serializable2.toString());
                    }
                })
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        log("Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log(e.getMessage().toString());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        log(aBoolean + "");
                    }
                });
    }

    private void log(String string) {
        Log.d("liweiqing", string);
    }
}
