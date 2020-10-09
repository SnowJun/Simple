package org.simple.image.proxy;

import android.app.Activity;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

/**
 * org.simple.image.proxy
 *
 * @author Simple
 * @date 2020/10/9
 * @desc
 */
public interface BaseImageProxy<R extends BaseImageProxy<R>> extends ImageProxy<R>{


    /**
     * 生成对象
     * @param activity
     * @return
     */
    R with(Activity activity);

    /**
     * 生成对象
     * @param fragment
     * @return
     */
    R with(Fragment fragment);

    /**
     * 加载到指定的View
     * @param imageView
     * @return
     */
     R to(ImageView imageView);

}
