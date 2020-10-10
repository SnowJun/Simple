package org.simple.image;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;

import org.simple.R;
import org.simple.image.agency.ImageProxyEnum;

/**
 * org.simple
 *
 * @author Simple
 * @date 2020/10/9
 * @desc
 */
public class ImageTestActivity extends AppCompatActivity implements View.OnClickListener {

    private SimpleDraweeView svTest;
    private ImageView ivTest;

    private TextView tvAgency;
    private TextView tvOperate;
    private Button btnFresco;
    private Button btnGlide;
    private Button btnPicasso;
    private Button btnNormal;
    private Button btnCircle;
    private Button btnRound;

    private boolean isFresco;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_test);
        initView();
    }


    private void initView() {
        svTest = findViewById(R.id.sv_test);
        ivTest = findViewById(R.id.iv_test);

        tvAgency = findViewById(R.id.tv_agency);
        tvOperate = findViewById(R.id.tv_operate);

        btnFresco = findViewById(R.id.btn_fresco);
        btnGlide = findViewById(R.id.btn_glide);
        btnPicasso = findViewById(R.id.btn_picasso);
        btnNormal = findViewById(R.id.btn_normal);
        btnCircle = findViewById(R.id.btn_circle);
        btnRound = findViewById(R.id.btn_round);

        btnFresco.setOnClickListener(this);
        btnGlide.setOnClickListener(this);
        btnPicasso.setOnClickListener(this);
        btnNormal.setOnClickListener(this);
        btnCircle.setOnClickListener(this);
        btnRound.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fresco:
                SimpleImage.getInstance()
                        .init(new SimpleImageBuilder().initFresco(this).setProxy(ImageProxyEnum.AGENCY_FRESCO));
                isFresco = true;
                tvAgency.setText("代理：Fresco");
                break;
            case R.id.btn_glide:
                SimpleImage.getInstance().init(new SimpleImageBuilder().setProxy(ImageProxyEnum.AGENCY_GLIDE));
                isFresco = false;
                tvAgency.setText("代理：Glide");
                break;
            case R.id.btn_picasso:
                SimpleImage.getInstance().init(new SimpleImageBuilder().setProxy(ImageProxyEnum.AGENCY_PICASSO));
                isFresco = false;
                tvAgency.setText("代理：Picasso");
                break;
            case R.id.btn_normal:
                loadNormal();
                tvOperate.setText("操作：普通");
                break;
            case R.id.btn_circle:
                loadCircle();
                tvOperate.setText("操作：圆形");
                break;
            case R.id.btn_round:
                loadRound();
                tvOperate.setText("操作：圆角");
                break;
            default:
                break;
        }
    }

    private void loadRound() {
        if (isFresco) {
            svTest.setVisibility(View.VISIBLE);
            ivTest.setVisibility(View.GONE);
            SimpleImage.getInstance().with(svTest)
                    .load("http://photocdn.sohu.com/20151007/mp34458815_1444187835381_1_th.jpeg")
                    .roundCorner(32)
                    .excute();
            return;
        }
        svTest.setVisibility(View.GONE);
        ivTest.setVisibility(View.VISIBLE);
        SimpleImage.getInstance().with(this)
                .load("http://photocdn.sohu.com/20151007/mp34458815_1444187835381_1_th.jpeg")
                .roundCorner(32)
                .to(ivTest);
    }

    private void loadCircle() {
        if (isFresco) {
            svTest.setVisibility(View.VISIBLE);
            ivTest.setVisibility(View.GONE);
            SimpleImage.getInstance().with(svTest)
                    .load("http://photocdn.sohu.com/20151007/mp34458815_1444187835381_1_th.jpeg")
                    .circle()
                    .excute();
            return;
        }
        svTest.setVisibility(View.GONE);
        ivTest.setVisibility(View.VISIBLE);
        SimpleImage.getInstance().with(this)
                .load("http://photocdn.sohu.com/20151007/mp34458815_1444187835381_1_th.jpeg")
                .circle()
                .to(ivTest);
    }

    private void loadNormal() {
        if (isFresco) {
            svTest.setVisibility(View.VISIBLE);
            ivTest.setVisibility(View.GONE);
            SimpleImage.getInstance().with(svTest)
                    .load("http://photocdn.sohu.com/20151007/mp34458815_1444187835381_1_th.jpeg")
                    .excute();
            return;
        }
        svTest.setVisibility(View.GONE);
        ivTest.setVisibility(View.VISIBLE);
        SimpleImage.getInstance().with(this)
                .load("http://photocdn.sohu.com/20151007/mp34458815_1444187835381_1_th.jpeg")
                .to(ivTest);
    }

}
