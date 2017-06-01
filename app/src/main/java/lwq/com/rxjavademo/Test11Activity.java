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
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class Test11Activity extends AppCompatActivity {

    @BindView(R.id.test13_btn1)
    Button test13Btn1;
    @BindView(R.id.test13_btn2)
    Button test13Btn2;
    @BindView(R.id.test13_btn3)
    Button test13Btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test11);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.test13_btn1, R.id.test13_btn2, R.id.test13_btn3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test13_btn1://
                test1();
                break;
            case R.id.test13_btn2://
                test2();
                break;
            case R.id.test13_btn3://
                test3();
                break;
        }
    }

    private void test1() {
        Observable<String> values = Observable.just("请", "不要", "有", "副作用");

        final Inc index = new Inc();
        Observable<String> indexed = values.map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                index.inc();
                return s;
            }
        });
        indexed.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                log(index.getCount() + ": " + s);
            }
        });
    }

    private void test2() {
        Observable<String> values = Observable.just("请", "不要", "有", "副作用");

        final Inc index = new Inc();
        Observable<String> indexed = values.map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                index.inc();
                return s;
            }
        });
        indexed.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                log("1st observer: " + index.getCount() + ": " + s);
            }
        });
        indexed.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                log("2nd observer: " + index.getCount() + ": " + s);
            }
        });
    }

    private void test3() {
        Observable<String> values = Observable.just("No", "side", "effects", "please");
        //这里用了scan的一个重载方法，传的new Indexed<String>(0, null)作为额外添加的第一个参数，定义了scan返回的类型，并且这个参数直接返回给indexed
        Observable<Indexed<String>> indexed =
                values
                        .scan(new Indexed<String>(0, null), new Func2<Indexed<String>, String, Indexed<String>>() {
                            @Override
                            public Indexed<String> call(Indexed<String> stringIndexed, String s) {
                                log(stringIndexed.index + "");
                                return new Indexed<String>(stringIndexed.index + 1, s);
                            }
                        })
                        .skip(1);
        indexed.subscribe(new Action1<Indexed<String>>() {
            @Override
            public void call(Indexed<String> stringIndexed) {
                log("1st observer: " + stringIndexed.index + ": " + stringIndexed.item);
            }
        });
        indexed.subscribe(new Action1<Indexed<String>>() {
            @Override
            public void call(Indexed<String> stringIndexed) {
                log("2nd observer: " + stringIndexed.index + ": " + stringIndexed.item);
            }
        });
    }

    class Inc {
        private int count = 0;

        public void inc() {
            count++;
        }

        public int getCount() {
            return count;
        }
    }

    private void log(String string) {
        Log.d("liweiqing", string);
    }

    class Indexed<T> {
        public final int index;
        public final T item;

        public Indexed(int index, T item) {
            this.index = index;
            this.item = item;
        }
    }
}
