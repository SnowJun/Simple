package org.simple.image.transform;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

/**
 * org.simple.image.transform
 *
 * @author Simple
 * @date 2020/10/10
 * @desc
 * 圆形转换器
 */
public class CircleTransform implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        //取宽高最小值为转换为方形图片的size
        int size = Math.min(source.getWidth(), source.getHeight());
        //计算两个方向的起点坐标
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        //创建方形图片
        Bitmap squareBitmap = Bitmap.createBitmap(source,x,y,size,size);
        if (source != squareBitmap){
            //如果方图和原图不是一个  则回收原图
            source.recycle();
        }
        //创建新图
        Bitmap resultBitmap = Bitmap.createBitmap(size,size,source.getConfig());
        Canvas canvas = new Canvas(resultBitmap);
        Paint paint = new Paint();
        //格局方图创建Shader
        BitmapShader shader =
                new BitmapShader(squareBitmap,BitmapShader.TileMode.CLAMP,BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);
        float roundR = size/2.0f;
        float centerX = roundR;
        float centerY = roundR;
        canvas.drawCircle(centerX,centerY,roundR,paint);
        squareBitmap.recycle();
        return resultBitmap;
    }

    @Override
    public String key() {
        return "CircleTransform()";
    }
}
