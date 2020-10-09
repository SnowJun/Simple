package org.simple.image.proxy;

import android.app.Activity;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

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
    public PicassoImageProxy roundCorner(float radius) {
        return this;
    }

    @Override
    public PicassoImageProxy circle() {
        return this;
    }
}
