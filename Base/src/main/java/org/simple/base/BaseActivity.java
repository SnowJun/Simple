package org.simple.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * org.simple.base
 *
 * @author Simple
 * @date 2020/9/9
 * @desc
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initView();
    }

    /**
     * 获取布局文件
     *
     * @return
     */
    @LayoutRes
    protected abstract int getLayout();

    /**
     * 初始化View
     */
    protected abstract void initView();

}
