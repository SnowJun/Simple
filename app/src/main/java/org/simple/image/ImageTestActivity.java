package org.simple.image;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;

import org.simple.R;

/**
 * org.simple
 *
 * @author Simple
 * @date 2020/10/9
 * @desc
 */
public class ImageTestActivity extends AppCompatActivity {

    private SimpleDraweeView simpleDraweeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_test);
        initView();
        loadImage();
    }

    private void loadImage() {
//        Uri uri = Uri.parse("http://photocdn.sohu.com/20151007/mp34458815_1444187835381_1_th.jpeg");
//        simpleDraweeView.setImageURI(uri);
        SimpleImage.getInstance().with(simpleDraweeView).load("http://photocdn.sohu.com/20151007/mp34458815_1444187835381_1_th.jpeg")
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .roundCorner(32)
                .excute();


        SimpleImage.getInstance().with(this)
                .load("http://photocdn.sohu.com/20151007/mp34458815_1444187835381_1_th.jpeg")
                .to(simpleDraweeView);
    }

    private void initView() {
        simpleDraweeView = findViewById(R.id.sv_test);
    }

}
