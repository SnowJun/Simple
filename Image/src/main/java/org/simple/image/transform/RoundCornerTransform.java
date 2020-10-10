package org.simple.image.transform;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.squareup.picasso.Transformation;

/**
 * org.simple.image.transform
 *
 * @author Simple
 * @date 2020/10/10
 * @desc
 * 圆角转换器
 */
public class RoundCornerTransform implements Transformation {

    /**
     * 角弧度
     * 单位px
     */
    private int corner;

    public RoundCornerTransform(int corner) {
        this.corner = corner;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();
        //根据原配置创建空白图
        Bitmap bitmap = Bitmap.createBitmap(width, height, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader =
                new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);
        RectF rectF = new RectF(new Rect(0, 0, width, height));
        canvas.drawRoundRect(rectF, corner, corner, paint);
        source.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "RoundCornerTransform()";
    }
}
