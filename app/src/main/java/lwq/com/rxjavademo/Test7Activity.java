package lwq.com.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;

public class Test7Activity extends AppCompatActivity {

    @BindView(R.id.test8_btn4)
    Button test8Btn4;
    @BindView(R.id.test8_btn5)
    Button test8Btn5;
    @BindView(R.id.test8_btn6)
    Button test8Btn6;
    @BindView(R.id.test8_btn9)
    Button test8Btn9;
    @BindView(R.id.test8_btn10)
    Button test8Btn10;
    @BindView(R.id.test8_btn11)
    Button test8Btn11;
    @BindView(R.id.test8_btn12)
    Button test8Btn12;
    @BindView(R.id.test8_btn13)
    Button test8Btn13;
    @BindView(R.id.test8_btn14)
    Button test8Btn14;
    @BindView(R.id.test8_btn15)
    Button test8Btn15;
    @BindView(R.id.test8_btn16)
    Button test8Btn16;
    @BindView(R.id.test8_btn17)
    Button test8Btn17;
    @BindView(R.id.test8_btn18)
    Button test8Btn18;
    @BindView(R.id.test8_btn19)
    Button test8Btn19;
    @BindView(R.id.test8_btn20)
    Button test8Btn20;
    @BindView(R.id.test8_btn21)
    Button test8Btn21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test7);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.test8_btn4, R.id.test8_btn5, R.id.test8_btn6, R.id.test8_btn9, R.id.test8_btn10, R.id.test8_btn12, R.id.test8_btn13, R.id.test8_btn14, R.id.test8_btn15, R.id.test8_btn16, R.id.test8_btn17, R.id.test8_btn18, R.id.test8_btn19, R.id.test8_btn20, R.id.test8_btn21})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test8_btn4://repeat
                repeat();
                break;
            case R.id.test8_btn5://repeatWhen
                repeatWhen();
                break;
            case R.id.test8_btn6://startWith
                startWith();
                break;
            case R.id.test8_btn9://merge
                merge();
                break;
            case R.id.test8_btn10://mergeWith
                mergeWith();
                break;
            case R.id.test8_btn11://mergeDelayError
                mergeDelayError();
                break;
            case R.id.test8_btn12://switchOnNext
                switchOnNext();
                break;
            case R.id.test8_btn13://switchMap
                switchMap();
                break;
            case R.id.test8_btn14://zip
                zip();
                break;
            case R.id.test8_btn15://zip2
                zip2();
                break;
            case R.id.test8_btn16://zip3
                zip3();
                break;
            case R.id.test8_btn17://zip4
                zip4();
                break;
            case R.id.test8_btn18://zipWith
                zipWith();
                break;
            case R.id.test8_btn19://combineLatest
                combineLatest();
                break;
            case R.id.test8_btn20://join
                join();
                break;
            case R.id.test8_btn21://groupJoin
                groupJoin();
                break;
        }
    }

    private void repeat() {
        Observable.just(1, 2)
//                .repeat()//无限循环
                .repeat(2)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(integer + "");
                    }
                });
    }

    private void repeatWhen() {
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(2)
                .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Void> observable) {
                        return observable.take(2);
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void startWith() {
        Observable.range(0, 3)
                .startWith(-1, -2)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }


    private void merge() {
        Observable.merge(
                Observable.interval(250, TimeUnit.MILLISECONDS).map(new Func1<Long, Object>() {
                    @Override
                    public Object call(Long aLong) {
                        return "First";
                    }
                }),
                Observable.interval(150, TimeUnit.MILLISECONDS).map(new Func1<Long, Object>() {
                    @Override
                    public Object call(Long aLong) {
                        return "Second";
                    }
                }))
                .take(10)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void mergeWith() {
        Observable.interval(250, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, Object>() {
                    @Override
                    public Object call(Long aLong) {
                        return "First";
                    }
                })
                .mergeWith(Observable.interval(150, TimeUnit.MILLISECONDS)
                        .map(new Func1<Long, Object>() {
                            @Override
                            public Object call(Long aLong) {
                                return "Second";
                            }
                        }))
                .take(10)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void mergeDelayError() {
        Observable<Object> failAt200 = Observable.concat(
                Observable.interval(100, TimeUnit.MILLISECONDS).take(2),
                Observable.error(new Exception("Failed")));
        Observable<Long> completeAt400 = Observable.interval(100, TimeUnit.MILLISECONDS).take(4);
        Observable.mergeDelayError(failAt200, completeAt400)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        log(throwable.getMessage().toString());
                    }
                });
    }

    private void switchOnNext() {
        Observable.switchOnNext(
                Observable.interval(100, TimeUnit.MILLISECONDS).map(new Func1<Long, Observable<?>>() {
                    @Override
                    public Observable<?> call(final Long aLong1) {
                        return Observable.interval(30, TimeUnit.MILLISECONDS).map(new Func1<Long, Object>() {
                            @Override
                            public Object call(Long aLong2) {
                                return aLong1;
                            }
                        });
                    }
                }))
                .take(9)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void switchMap() {
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .switchMap(new Func1<Long, Observable<?>>() {
                    @Override
                    public Observable<?> call(final Long aLong1) {
                        return Observable.interval(30, TimeUnit.MILLISECONDS)
                                .map(new Func1<Long, Object>() {
                                    @Override
                                    public Object call(Long aLong2) {
                                        return aLong1;
                                    }
                                });
                    }
                })
                .take(9)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void zip() {
        Observable.zip(
                Observable.interval(100, TimeUnit.MILLISECONDS).doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        log("Left emits " + aLong);
                    }
                }),
                Observable.interval(150, TimeUnit.MILLISECONDS).doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        log("Right emits " + aLong);
                    }
                }),
                new Func2<Long, Long, Object>() {
                    @Override
                    public Object call(Long aLong, Long aLong2) {
                        return aLong + " - " + aLong2;
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

    private void zip2() {
        Observable.zip(
                Observable.interval(100, TimeUnit.MILLISECONDS),
                Observable.interval(150, TimeUnit.MILLISECONDS),
                Observable.interval(50, TimeUnit.MILLISECONDS),
                new Func3<Long, Long, Long, Object>() {
                    @Override
                    public Object call(Long aLong, Long aLong2, Long aLong3) {
                        return aLong + " - " + aLong2 + " - " + aLong3;
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

    private void zip3() {
        Observable.zip(
                Observable.range(0, 5),
                Observable.range(0, 3),
                Observable.range(0, 8),
                new Func3<Integer, Integer, Integer, Object>() {
                    @Override
                    public Object call(Integer integer, Integer integer2, Integer integer3) {
                        return integer + " - " + integer2 + " - " + integer3;
                    }
                })
                .count()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void zip4() {
        Observable.range(0, 5)
                .zipWith(Arrays.asList(0, 2, 4, 6, 8), new Func2<Integer, Integer, Object>() {
                    @Override
                    public Object call(Integer integer, Integer integer2) {
                        return integer + " - " + integer2;
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void zipWith() {
        Observable.interval(100, TimeUnit.MILLISECONDS).zipWith(
                Observable.interval(150, TimeUnit.MILLISECONDS),
                new Func2<Long, Long, Object>() {
                    @Override
                    public Object call(Long aLong, Long aLong2) {
                        return aLong + " - " + aLong2;
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

    private void combineLatest() {
        Observable.combineLatest(
                Observable.interval(100, TimeUnit.MILLISECONDS).doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        log("Left emits " + aLong);
                    }
                }),
                Observable.interval(150, TimeUnit.MILLISECONDS).doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        log("Right emits " + aLong);
                    }
                })
                , new Func2<Long, Long, Object>() {
                    @Override
                    public Object call(Long aLong, Long aLong2) {
                        return aLong + " - " + aLong2;
                    }
                }
        )
                .take(6)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        log(o.toString());
                    }
                });
    }

    private void join() {
        //产生0,2,4,6,8数列
        Observable<Long> observable1 = Observable.timer(0, 1000, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return aLong * 2;
                    }
                }).take(5);

        //产生0,3,6,9,12数列
        Observable<Long> observable2 = Observable.timer(500, 1000, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return aLong * 3;
                    }
                }).take(5);

        observable1.join(observable2, new Func1<Long, Observable<Long>>() {
            @Override
            public Observable<Long> call(Long aLong) {
                //使Observable持续600毫秒有效
                return Observable.just(aLong).delay(600, TimeUnit.MILLISECONDS);
            }
        }, new Func1<Long, Observable<Long>>() {
            @Override
            public Observable<Long> call(Long aLong) {
                //使Observable持续600毫秒有效
                return Observable.just(aLong).delay(600, TimeUnit.MILLISECONDS);
            }
        }, new Func2<Long, Long, Long>() {
            @Override
            public Long call(Long aLong, Long aLong2) {
                return aLong + aLong2;
            }
        })
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        log(aLong + "");
                    }
                });
    }

    private void groupJoin() {
        //产生100,110,120,130,140数列
        Observable<Long> observable1 = Observable.timer(0, 1000, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        log(System.currentTimeMillis() + "--1--");
                        return aLong * 10 + 100;
                    }
                }).take(5);

        //产生1,3,5,7,9数列
        Observable<Long> observable2 = Observable.timer(500, 1000, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        log(System.currentTimeMillis() + "--2--");
                        return aLong * 2 + 1;
                    }
                }).take(5);

        observable1.groupJoin(observable2, new Func1<Long, Observable<Long>>() {
            @Override
            public Observable<Long> call(Long aLong) {
                //使Observable持续1600毫秒有效
                return Observable.just(aLong).delay(1600, TimeUnit.MILLISECONDS);
            }
        }, new Func1<Long, Observable<Long>>() {
            @Override
            public Observable<Long> call(Long aLong) {
                //使Observable持续600毫秒有效
                return Observable.just(aLong).delay(600, TimeUnit.MILLISECONDS);
            }
        }, new Func2<Long, Observable<Long>, Observable<Long>>() {
            @Override
            public Observable<Long> call(final Long aLong, Observable<Long> observable) {
                return observable.map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong2) {
                        return aLong + aLong2;
                    }
                });
            }
        })
                .subscribe(new Action1<Observable<Long>>() {
                    @Override
                    public void call(Observable<Long> longObservable) {
                        longObservable.subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                log(aLong + "");
                            }
                        });
                    }
                });
    }

    private void log(String string) {
        Log.d("liweiqing", string);

    }
}
