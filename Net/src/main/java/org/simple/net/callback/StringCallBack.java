package org.simple.net.callback;

import android.text.TextUtils;

import org.simple.net.exception.ExceptionCode;
import org.simple.net.exception.NetException;
import org.simple.net.response.Code;
import org.simple.net.response.Response;
import org.simple.net.util.UIThreadUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * org.simple.net.callback
 *
 * @author Simple
 * @date 2020/9/9
 * @desc String返回类
 */
public abstract class StringCallBack implements NetCallBack<String> {

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
            final StringBuffer sb = new StringBuffer();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(datas));
            String unit = null;
            while ((unit = bufferedReader.readLine()) != null) {
                sb.append(unit);
            }
            if (!TextUtils.isEmpty(sb.toString())) {
                UIThreadUtil.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        onSuccess(sb.toString());
                    }
                });
            } else {
                UIThreadUtil.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        onFail("返回内容为空");
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
            if (null != datas) {
                try {
                    datas.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
