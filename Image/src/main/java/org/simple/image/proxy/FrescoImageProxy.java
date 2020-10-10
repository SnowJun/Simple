package org.simple.image.proxy;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;

import org.simple.image.SimpleImage;
import org.simple.image.SimpleImageBuilder;
import org.simple.image.SimpleLog;

import java.io.File;

/**
 * org.simple.image.proxy
 *
 * @author Simple
 * @date 2020/10/9
 * @desc
 */
public class FrescoImageProxy implements ImageProxy<FrescoImageProxy> {

    /**
     * 控件对象
     */
    private SimpleDraweeView view;
    /**
     * 参数设置
     */
    private GenericDraweeHierarchy hierarchy;
    /**
     * 资源url
     * 如果设置了res  则以res为准
     */
    private Uri uri;
    /**
     * 资源res
     */
    private int res = -1;

    /**
     * 跳过内存缓存
     */
    private boolean isSkipMemory;
    /**
     * 跳过磁盘缓存
     */
    private boolean isSkipDisk;

    @Override
    public FrescoImageProxy load(String url) {
        uri = Uri.parse(url);
        return this;
    }

    @Override
    public FrescoImageProxy load(File file) {
        uri = Uri.fromFile(file);
        return this;
    }

    @Override
    public FrescoImageProxy load(int res) {
        this.res = res;
        return this;
    }

    @Override
    public FrescoImageProxy placeholder(int res) {
        hierarchy.setPlaceholderImage(res);
        return this;
    }

    @Override
    public FrescoImageProxy error(int res) {
        hierarchy.setFailureImage(res);
        return this;
    }

    @Override
    public FrescoImageProxy roundCorner(int radius) {
        hierarchy.setRoundingParams(new RoundingParams().setCornersRadius(radius));
        return this;
    }

    @Override
    public FrescoImageProxy circle() {
        hierarchy.setRoundingParams(new RoundingParams().setRoundAsCircle(true));
        return this;
    }

    @Override
    public FrescoImageProxy skipCacheMemory() {
        isSkipMemory = true;
        return this;
    }

    @Override
    public FrescoImageProxy skipCacheDisk() {
        isSkipDisk = true;
        return this;
    }


    /**
     * 根据控件生成实体
     * @param view
     */
    public FrescoImageProxy with(@NonNull SimpleDraweeView view) {
        this.view = view;
        hierarchy = view.getHierarchy();
        return this;
    }

    /**
     * 执行真是请求
     */
    public void excute() {
        view.setHierarchy(hierarchy);
        if (res != -1) {
            view.setImageResource(res);
            return;
        }
        if (null == uri){
            SimpleLog.e("请先初始化uri，设置加载的url或者file");
            return;
        }
        SimpleImageBuilder builder = SimpleImage.getInstance().getBuilder();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        if (isSkipMemory){
            imagePipeline.evictFromMemoryCache(uri);
        }else if (!builder.isCacheMemory()){
            imagePipeline.evictFromMemoryCache(uri);
        }
        if (isSkipDisk){
            imagePipeline.evictFromDiskCache(uri);
        }else if (!builder.isCacheDisk()){
            imagePipeline.evictFromDiskCache(uri);
        }
        view.setImageURI(uri);
    }

}
