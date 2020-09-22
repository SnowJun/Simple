package org.simple.net.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.simple.net.exception.ExceptionCode;
import org.simple.net.exception.NetException;
import org.simple.net.response.Code;
import org.simple.net.response.Response;
import org.simple.net.util.UIThreadUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * org.simple.net.callbac
 *
 * @author Simple
 * @date 2020/9/10
 * @desc Bitmap返回类
 */
public abstract class BitmapCallback implements NetCallBack<Bitmap> {
    @Override
    public void parse(final Response response) {
        int code = response.getCode();
        if (Code.RESP0NSE_OK != code) {
            UIThreadUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    onException(NetException.exception(ExceptionCode.CODE_HTTP_EXCEPTION,"http异常："+response.getCode()));
                }
            });
            return;
        }
        InputStream datas = null;
        try {
            datas = response.getBody().getInputStreamData();
            final Bitmap bitmap = BitmapFactory.decodeStream(datas);
            if (null != bitmap) {
                UIThreadUtil.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        onSuccess(bitmap);
                    }
                });
            } else {
                UIThreadUtil.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        onFail("返回图片为空");
                    }
                });
            }
        } catch (final Exception e) {
            UIThreadUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    onException(NetException.exception(ExceptionCode.CODE_PARSE_EXCEPTION, e.getMessage()));
                }
            });
        } finally {
            if (null != datas){
                try {
                    datas.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
