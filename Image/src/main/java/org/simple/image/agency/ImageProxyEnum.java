package org.simple.image.agency;

import org.simple.image.proxy.FrescoImageProxy;
import org.simple.image.proxy.GlideImageProxy;
import org.simple.image.proxy.ImageProxy;
import org.simple.image.proxy.PicassoImageProxy;

/**
 * org.simple.image.agency
 *
 * @author Simple
 * @date 2020/10/9
 * @desc
 */
public enum ImageProxyEnum {

    /**
     * fresco代理
     */
    AGENCY_FRESCO(FrescoImageProxy.class),
    AGENCY_GLIDE(GlideImageProxy.class),
    AGENCY_PICASSO(PicassoImageProxy.class);

    private Class<? extends ImageProxy> imageAgencyClass;

    ImageProxyEnum(Class<? extends ImageProxy<?>> imageAgencyClass) {
        this.imageAgencyClass = imageAgencyClass;
    }

    public Class<? extends ImageProxy> getImageAgencyClass() {
        return imageAgencyClass;
    }
}
