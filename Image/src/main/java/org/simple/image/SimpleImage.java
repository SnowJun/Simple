package org.simple.image;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.facebook.drawee.view.SimpleDraweeView;

import org.simple.image.agency.ImageProxyFactory;
import org.simple.image.proxy.BaseImageProxy;
import org.simple.image.proxy.FrescoImageProxy;
import org.simple.image.proxy.ImageProxy;

/**
 * org.simple.image
 *
 * @author Simple
 * @date 2020/9/9
 * @desc
 */
public class SimpleImage {

    private ImageProxy<?> proxy;

    private static SimpleImage ourInstance;
    private SimpleImageBuilder builder;

    private SimpleImage() {
    }

    public static SimpleImage getInstance() {
        if (null == ourInstance) {
            synchronized (SimpleImage.class) {
                if (null == ourInstance) {
                    ourInstance = new SimpleImage();
                }
            }
        }
        return ourInstance;
    }


    public void init(SimpleImageBuilder builder) {
        this.builder = builder;
        if (null == builder) {
            SimpleLog.e("初始化builder不能为空");
            return;
        }
        proxy = ImageProxyFactory.genProxy(builder.getProxy().getImageAgencyClass());
    }


    public SimpleImageBuilder getBuilder() {
        if (null == builder){
            builder = new SimpleImageBuilder();
        }
        return builder;
    }

    /**
     * Glide或者Picasso
     * @param activity
     * @return
     */
    public BaseImageProxy<? extends BaseImageProxy<?>> with(Activity activity) {
        if (!(proxy instanceof BaseImageProxy)) {
            SimpleLog.e("with（Activity activity）代理不能使用fresco");
            throw new RuntimeException("初始化失败,with（Activity activity）代理不能使用fresco");
        }
        BaseImageProxy<? extends BaseImageProxy<?>> proxy1 = (BaseImageProxy<? extends BaseImageProxy<?>>) proxy;
        return proxy1.with(activity);
    }

    /**
     * Glide或者Picasso
     * @param fragment
     * @return
     */
    public BaseImageProxy<? extends BaseImageProxy<?>> with(Fragment fragment) {
        if (!(proxy instanceof BaseImageProxy)) {
            SimpleLog.e("with（Fragment fragment）代理不能使用fresco");
            throw new RuntimeException("初始化失败,with（Fragment fragment）代理不能使用fresco");
        }
        BaseImageProxy<? extends BaseImageProxy<?>> proxy1 = (BaseImageProxy<? extends BaseImageProxy<?>>) proxy;
        return proxy1.with(fragment);

    }

    /**
     * 针对fresco的加载
     * @param view
     * @return
     */
    public FrescoImageProxy with(SimpleDraweeView view) {
        if (!(proxy instanceof FrescoImageProxy)) {
            SimpleLog.e("代理请使用fresco");
            throw new RuntimeException("初始化失败,代理请使用fresco");
        }
        FrescoImageProxy frescoImageProxy = (FrescoImageProxy) proxy;
        return frescoImageProxy.with(view);
    }

}
