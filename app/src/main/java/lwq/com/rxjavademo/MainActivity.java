package lwq.com.rxjavademo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_btn2)
    Button mainBtn2;
    @BindView(R.id.main_btn3)
    Button mainBtn3;
    @BindView(R.id.main_btn4)
    Button mainBtn4;
    @BindView(R.id.main_btn5)
    Button mainBtn5;
    @BindView(R.id.main_btn6)
    Button mainBtn6;
    @BindView(R.id.main_btn7)
    Button mainBtn7;
    @BindView(R.id.main_btn8)
    Button mainBtn8;
    @BindView(R.id.main_btn9)
    Button mainBtn9;
    @BindView(R.id.main_btn10)
    Button mainBtn10;
    @BindView(R.id.main_btn11)
    Button mainBtn11;
    @BindView(R.id.main_btn12)
    Button mainBtn12;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
    }

    @OnClick({R.id.main_btn2, R.id.main_btn3, R.id.main_btn4, R.id.main_btn5, R.id.main_btn6, R.id.main_btn7, R.id.main_btn8, R.id.main_btn9, R.id.main_btn10, R.id.main_btn11, R.id.main_btn12})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_btn2://基本使用
                startActivity(new Intent(mContext, Test1Activity.class));
                break;
            case R.id.main_btn3://操作符：创建操作符
                startActivity(new Intent(mContext, Test2Activity.class));
                break;
            case R.id.main_btn4://操作符：过滤操作符
                startActivity(new Intent(mContext, Test3Activity.class));
                break;
            case R.id.main_btn5://操作符：条件和布尔操作符
                startActivity(new Intent(mContext, Test4Activity.class));
                break;
            case R.id.main_btn6://操作符：算术和聚合操作符
                startActivity(new Intent(mContext, Test5Activity.class));
                break;
            case R.id.main_btn7://操作符：转换操作符
                startActivity(new Intent(mContext, Test6Activity.class));
                break;
            case R.id.main_btn8://操作符：组合操作符
                startActivity(new Intent(mContext, Test7Activity.class));
                break;
            case R.id.main_btn9://操作符：辅助操作符
                startActivity(new Intent(mContext, Test8Activity.class));
                break;
            case R.id.main_btn10://操作符：错误处理
                startActivity(new Intent(mContext, Test9Activity.class));
                break;
            case R.id.main_btn11://hot&cold
                startActivity(new Intent(mContext, Test10Activity.class));
                break;
            case R.id.main_btn12://副作用
                startActivity(new Intent(mContext, Test11Activity.class));
                break;
        }
    }
}
