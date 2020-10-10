package org.simple;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.simple.image.SimpleImage;
import org.simple.image.SimpleImageBuilder;

/**
 * org.simple
 *
 * @author Simple
 * @date 2020/10/9
 * @desc
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        initSimpleImage();
    }

    private void initSimpleImage() {
        SimpleImageBuilder simpleImageBuilder = new SimpleImageBuilder();
        SimpleImage.getInstance().init(simpleImageBuilder);
    }

}
