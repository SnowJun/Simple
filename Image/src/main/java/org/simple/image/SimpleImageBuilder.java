package org.simple.image;

import android.content.Context;

import org.simple.image.agency.ImageProxyEnum;
import org.simple.image.constants.Constants;

/**
 * org.simple.image
 *
 * @author Simple
 * @date 2020/10/9
 * @desc
 */
public class SimpleImageBuilder {


    /**
     * 是否内存缓存
     */
    private boolean isCacheMemory = true;
    /**
     * 是否磁盘缓存
     */
    private boolean isCacheDisk = true;
    /**
     * 是否初始化fresco
     */
    private boolean initFresco;
    private Context context;
    private ImageProxyEnum proxy = Constants.PROXY;

    public SimpleImageBuilder setProxy(ImageProxyEnum proxy) {
        this.proxy = proxy;
        return this;
    }

    public SimpleImageBuilder cacheMemory() {
        isCacheMemory = true;
        return this;
    }

    public SimpleImageBuilder disableCacheMemory() {
        isCacheMemory = false;
        return this;
    }

    public SimpleImageBuilder cacheDisk() {
        isCacheDisk = true;
        return this;
    }

    public SimpleImageBuilder disableCacheDisk() {
        isCacheDisk = false;
        return this;
    }

    public SimpleImageBuilder initFresco(Context context) {
        initFresco = true;
        this.context = context;
        return this;
    }

    public boolean isCacheMemory() {
        return isCacheMemory;
    }

    public boolean isCacheDisk() {
        return isCacheDisk;
    }

    public Context context() {
        return context;
    }

    public boolean isInitFresco() {
        return initFresco;
    }

    public ImageProxyEnum getProxy() {
        return proxy;
    }


}
