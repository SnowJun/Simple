package org.simple.image.proxy;

import android.app.Activity;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.simple.image.SimpleImage;
import org.simple.image.SimpleImageBuilder;
import org.simple.image.transform.CircleTransform;
import org.simple.image.transform.RoundCornerTransform;

import java.io.File;

/**
 * org.simple.image.proxy
 *
 * @author Simple
 * @date 2020/10/9
 * @desc
 */
public class PicassoImageProxy implements BaseImageProxy<PicassoImageProxy>{

    private Picasso picasso;
    private RequestCreator creator;

    /**
     * 跳过内存缓存
     */
    private boolean isSkipMemory;
    /**
     * 跳过磁盘缓存
     */
    private boolean isSkipDisk;

    @Override
    public PicassoImageProxy with(Activity activity) {
        picasso = Picasso.get();
        return this;
    }

    @Override
    public PicassoImageProxy with(Fragment fragment) {
        picasso = Picasso.get();
        return this;
    }

    @Override
    public PicassoImageProxy to(ImageView imageView) {
        SimpleImageBuilder builder = SimpleImage.getInstance().getBuilder();
        if (isSkipMemory){
            creator.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_CACHE);
        }else {
            if (!builder.isCacheMemory()){
                creator.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_CACHE);
            }
        }
        if (isSkipDisk){
            creator.networkPolicy(NetworkPolicy.NO_CACHE,NetworkPolicy.NO_CACHE);
        }else {
            if (!builder.isCacheDisk()){
                creator.networkPolicy(NetworkPolicy.NO_CACHE,NetworkPolicy.NO_CACHE);
            }
        }
        creator.into(imageView);
        return this;
    }

    @Override
    public PicassoImageProxy load(String url) {
        creator = picasso.load(url);
        return this;
    }

    @Override
    public PicassoImageProxy load(File file) {
        creator = picasso.load(file);
        return this;
    }

    @Override
    public PicassoImageProxy load(int res) {
        creator = picasso.load(res);
        return this;
    }

    @Override
    public PicassoImageProxy placeholder(int res) {
        creator.placeholder(res);
        return this;
    }

    @Override
    public PicassoImageProxy error(int res) {
        creator.error(res);
        return this;
    }

    @Override
    public PicassoImageProxy roundCorner(int radius) {
        creator.transform(new RoundCornerTransform(radius));
        return this;
    }

    @Override
    public PicassoImageProxy circle() {
        creator.transform(new CircleTransform());
        return this;
    }

    @Override
    public PicassoImageProxy skipCacheMemory() {
        isSkipMemory = true;
        return this;
    }

    @Override
    public PicassoImageProxy skipCacheDisk() {
        isSkipDisk = true;
        return this;
    }
}
