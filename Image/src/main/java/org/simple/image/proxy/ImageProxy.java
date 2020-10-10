package org.simple.image.proxy;

import java.io.File;

/**
 * org.simple.image.proxy
 *
 * @author Simple
 * @date 2020/10/9
 * @desc
 */
public interface ImageProxy<R extends ImageProxy<R>> {

    /**
     * 加载url
     * @param url
     * @return
     */
    R load(String url);

    /**
     * 加载文件
     * @param file
     * @return
     */
    R load(File file);

    /**
     * 加载资源
     * @param res
     * @return
     */
    R load(int  res);

    /**
     * 占位图
     * @param res
     * @return
     */
    R placeholder(int res);

    /**
     * 错误图
     * @param res
     * @return
     */
    R error(int res);
    /**
     * 圆角裁剪
     * @param radius
     * @return
     */
    R roundCorner(int radius);

    /**
     * 圆形裁剪
     * @return
     */
    R circle();

    /**
     * 跳过内存缓存
     * @return
     */
    R skipCacheMemory();

    /**
     * 跳过磁盘缓存
     * @return
     */
    R skipCacheDisk();

}
