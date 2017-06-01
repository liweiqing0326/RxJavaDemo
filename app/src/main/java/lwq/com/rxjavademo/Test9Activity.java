package lwq.com.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.TimeInterval;

public class Test9Activity extends AppCompatActivity {

    @BindView(R.id.test10_btn1)
    Button test10Btn1;
    @BindView(R.id.test10_btn2)
    Button test10Btn2;
    @BindView(R.id.test10_btn3)
    Button test10Btn3;
    @BindView(R.id.test10_btn4)
    Button test10Btn4;
    @BindView(R.id.test10_btn5)
    Button test10Btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test9);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.test10_btn1, R.id.test10_btn2, R.id.test10_btn3, R.id.test10_btn4, R.id.test10_btn5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test10_btn1://onErrorReturn
                onErrorReturn();
                break;
            case R.id.test10_btn2://onErrorResumeNext
                onErrorResumeNext();
                break;
            case R.id.test10_btn3://onExceptionResumeNext
                onExceptionResumeNext();
                break;
            case R.id.test10_btn4://retry
                retry();
                break;
            case R.id.test10_btn5://retryWhen
                retryWhen();
                break;
        }
    }
    private void onErrorReturn(){
        Observable<String> values =Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("1");
                subscriber.onNext("2");
                subscriber.onError(new Exception("It's a exception"));
            }});
        values.onErrorReturn(new Func1<Throwable, String>() {
            @Override
            public String call(Throwable throwable) {
                return "Error: " + throwable.getMessage();
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                log(s);
            }
        });
    }
    private void onErrorResumeNext(){
        Observable<String> values =Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("1");
                subscriber.onNext("2");
                subscriber.onError(new Exception("It's a exceptionn"));
            }});
        values.onErrorResumeNext(Observable.just("7", "8", "9")).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                log(s);
            }
        });
//        values.onErrorResumeNext(new Func1<Throwable, Observable<? extends String>>() {
//            @Override
//            public Observable<? extends String> call(Throwable throwable) {
//                return null;
//            }
//        });

    }
    private void onExceptionResumeNext(){
        Observable<String> values = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("1");
                subscriber.onNext("2");
//                subscriber.onError(new Throwable() {}); // 这个为 error 不会捕获
                subscriber.onError(new Exception("It's a exceptionn"));// 这个为 Exception 会被捕获
            }});

        values.onExceptionResumeNext(Observable.just("hard")).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                log(s);
            }
        });
    }
    private void retry(){
        final Random random = new Random();
        Observable<Integer> values = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(random.nextInt() % 20);
                subscriber.onNext(random.nextInt() % 20);
                subscriber.onError(new Exception("It's a exceptionn"));
            }});

        values.retry(1).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log(integer.toString());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                log(throwable.getMessage().toString());
            }
        });
    }
    private void retryWhen(){
        Observable<Integer> source = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onError(new Exception("Failed"));
            }});

        source.retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                return observable.take(2)
                        .delay(100, TimeUnit.MILLISECONDS);
            }
        }) .timeInterval()
                .subscribe(new Action1<TimeInterval<Integer>>() {
                    @Override
                    public void call(TimeInterval<Integer> integerTimeInterval) {
                        log(integerTimeInterval.toString());
                    }
                });
    }

    private void log(String string) {
        Log.d("liweiqing", string);
    }
}
