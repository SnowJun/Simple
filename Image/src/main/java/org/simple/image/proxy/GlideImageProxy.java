package org.simple.image.proxy;

import android.app.Activity;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import org.simple.image.SimpleImage;
import org.simple.image.SimpleImageBuilder;

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

    /**
     * 跳过内存缓存
     */
    private boolean isSkipMemory;
    /**
     * 跳过磁盘缓存
     */
    private boolean isSkipDisk;

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
        SimpleImageBuilder builder = SimpleImage.getInstance().getBuilder();
        if (isSkipMemory){
            requestBuilder.skipMemoryCache(true);
        }else {
            if (!builder.isCacheMemory()){
                requestBuilder.skipMemoryCache(true);
            }
        }
        if (isSkipDisk){
            requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE);
        }else {
            if (!builder.isCacheDisk()){
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE);
            }
        }
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
    public GlideImageProxy roundCorner(int radius) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.circleCrop();
        requestOptions.transform(new RoundedCorners(radius));
        requestBuilder.apply(requestOptions);
        return this;
    }

    @Override
    public GlideImageProxy circle() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.circleCrop();
        requestBuilder.apply(requestOptions);
        return this;
    }

    @Override
    public GlideImageProxy skipCacheMemory() {
        isSkipMemory = true;
        return this;
    }

    @Override
    public GlideImageProxy skipCacheDisk() {
        isSkipDisk = true;
        return this;
    }
}
