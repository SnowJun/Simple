package org.simple.image.proxy;

import android.app.Activity;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;

import java.io.File;

/**
 * org.simple.image.proxy
 *
 * @author Simple
 * @date 2020/10/9
 * @desc
 */
public class GlideImageProxy implements BaseImageProxy<GlideImageProxy>{

    private RequestManager requestManager;
    private RequestBuilder requestBuilder;

    @Override
    public GlideImageProxy with(Activity activity) {
        requestManager =  Glide.with(activity);
        return this;
    }

    @Override
    public GlideImageProxy with(Fragment fragment) {
        requestManager = Glide.with(fragment);
        return this;
    }

    @Override
    public GlideImageProxy to(ImageView imageView) {
        requestBuilder.into(imageView);
        return this;
    }

    @Override
    public GlideImageProxy load(String url) {
        requestBuilder = requestManager.load(url);
        return this;
    }

    @Override
    public GlideImageProxy load(File file) {
        requestBuilder = requestManager.load(file);
        return this;
    }

    @Override
    public GlideImageProxy load(int res) {
        requestBuilder = requestManager.load(res);
        return this;
    }

    @Override
    public GlideImageProxy placeholder(int res) {
        requestBuilder.placeholder(res);
        return this;
    }

    @Override
    public GlideImageProxy error(int res) {
        requestBuilder.error(res);
        return this;
    }

    @Override
    public GlideImageProxy roundCorner(float radius) {
        return this;
    }

    @Override
    public GlideImageProxy circle() {
        return this;
    }
}
