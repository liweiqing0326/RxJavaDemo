package lwq.com.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;


public class Test2Activity extends AppCompatActivity {

    @BindView(R.id.test3_btn1)
    Button test3Btn1;
    @BindView(R.id.test3_btn2)
    Button test3Btn2;
    @BindView(R.id.test3_btn3)
    Button test3Btn3;
    @BindView(R.id.test3_btn5)
    Button test3Btn5;
    @BindView(R.id.test3_btn6)
    Button test3Btn6;
    @BindView(R.id.test3_btn7)
    Button test3Btn7;
    @BindView(R.id.test3_btn8)
    Button test3Btn8;
    @BindView(R.id.test3_btn9)
    Button test3Btn9;
    @BindView(R.id.test3_btn10)
    Button test3Btn10;
    @BindView(R.id.test3_btn11)
    Button test3Btn11;
    @BindView(R.id.test3_btn12)
    Button test3Btn12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.test3_btn1, R.id.test3_btn2, R.id.test3_btn3, R.id.test3_btn5, R.id.test3_btn6, R.id.test3_btn7, R.id.test3_btn8, R.id.test3_btn9, R.id.test3_btn10, R.id.test3_btn11, R.id.test3_btn12})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test3_btn1://creat创建操作符
                //creat
                break;
            case R.id.test3_btn2://just创建操作符
                //just
                just();
                break;
            case R.id.test3_btn3://from创建操作符
                //from
                from();
                break;
            case R.id.test3_btn5://defer创建操作符
                //defer
                defer();
                break;
            case R.id.test3_btn6://range创建操作符
                //range
                range();
                break;
            case R.id.test3_btn7://interval创建操作符
                //interval
                interval();
                break;
            case R.id.test3_btn8://timer创建操作符
                //timer1
                timer1();
                break;
            case R.id.test3_btn9://timer创建操作符
                //timer2
                timer2();
                break;
            case R.id.test3_btn10://empty创建操作符
                //empty
                empty();
                break;
            case R.id.test3_btn11://never创建操作符
                //never
                never();
                break;
            case R.id.test3_btn12://error创建操作符
                //error
                error();
                break;
        }
    }

    /**
     * error创建操作符
     * 创建一个不发射数据以一个错误终止的Observable
     */
    private void error() {
        Observable<Integer> observable = Observable.error(new Exception("Oops"));
        observable.subscribe(new Subscriber<Integer>() {
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
     * never创建操作符
     * 创建一个不发射数据也不终止的Observable
     */
    private void never() {
        Observable<Integer> observable = Observable.never();
        observable.subscribe(new Subscriber<Integer>() {
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
     * empty创建操作符
     * 创建一个不发射任何数据但是正常终止的Observable
     */
    private void empty() {
        Observable<Integer> observable = Observable.empty();
        observable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.i("liweiqing", "Complete");
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
     * timer创建操作符
     * 创建隔离一段时间发射一个数据的Observable，只发送两次
     */
    private void timer2() {
        Observable.timer(4, 2, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.i("liweiqing", aLong + "");
                    }
                });
    }

    /**
     * timer创建操作符
     * 创建隔离一段时间发射一个数据的Observable，只发送一次
     */
    private void timer1() {
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.i("liweiqing", aLong + "");
                    }
                });
    }

    /**
     * interval创建操作符
     * 创建一个每隔一段时间发射数据的Observable
     */
    private void interval() {
        Observable.interval(3, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.i("liweiqing", aLong + "");
                    }
                });
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * range创建操作符
     * 创建一个发射特定整数序列的Observable，可以通过参数指定线程
     */
    private void range() {
        Observable.range(5, 3)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i("liweiqing", integer + "");
                    }
                });
    }

    /**
     * defer创建操作符
     * 直到有观察者订阅时才会创建Observable，并且为每一个观察者创建一个新的Obsevable
     */
    private void defer() {
        Observable<Integer> observable2 = Observable.defer(new Func0<Observable<Integer>>() {
            @Override
            public Observable<Integer> call() {
                return Observable.just(28);
            }
        });
        observable2.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.i("liweiqing", "onCompleted:完成.");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("liweiqing", "onError:" + e.toString());
            }

            @Override
            public void onNext(Integer integer) {
                Log.i("liweiqing", "onNext:" + integer + " 所在线程："
                        + Thread.currentThread().getName());
            }
        });
    }

    /**
     * just创建操作符
     * 创建一个发射指定值的Observable，Just只是简单的将数据原样发射
     */
    private void just() {
        Observable.just(1, 2, 3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.i("liweiqing", "Complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("liweiqing", "e.getMessage().toString()");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("liweiqing", integer + " ");
                    }
                });
    }

    /**
     * from创建操作符
     * 产生的Observable会发射Iterable或者数组的每一项数据
     */
    private void from() {
        Integer[] items = new Integer[]{1, 2, 3};
        Observable.from(items)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i("liweiqing", integer + "");
                    }
                });

    }
}
