package org.simple.net.callback;

import org.simple.net.exception.NetException;
import org.simple.net.response.Code;
import org.simple.net.response.Response;
import org.simple.net.util.UIThreadUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * org.simple.net.callback
 *
 * @author Simple
 * @date 2020/9/10
 * @desc 文件回调
 */
public abstract class FileCallBack implements NetCallBack<File> {

    /**
     * 父文件
     */
    private String parent;
    /**
     * 文件名
     */
    private String name;

    public FileCallBack(String parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    @Override
    public void parse(final Response response) {
        final int code = response.getCode();
        if (Code.RESP0NSE_OK != code) {
            UIThreadUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    onException(NetException
                            .exception(code,response.getMessage(),"http异常"));
                }
            });
            return;
        }
        try {
            saveFileToLocal(response);
        } catch (final Exception e) {
            UIThreadUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    onException(NetException.exception("文件IO异常："+e.getMessage()));
                }
            });
        }
    }


    /**
     * 进度
     *
     * @param progress 下载进度  0-100
     * @param current  当前下载大小
     * @param size     总共大小
     */
    public abstract void onProgress(int progress, long current, long size);

    private void saveFileToLocal(Response response) throws Exception {
        if (null == response || null == response.getBody() || null == response.getBody().getInputStreamData()) {
            onFail("返回的文件为空");
            return;
        }
        long current = 0;
        long size = response.getBody().getLength();
        //每次下载的单位
        int unit = 0;
        File parentFile = new File(parent);
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        final File file = new File(parent, name);

        byte[] buffer = new byte[1024];

        OutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            outputStream = new FileOutputStream(file);
            inputStream = response.getBody().getInputStreamData();
            while ((unit = inputStream.read(buffer)) != -1) {
                current += unit;
                outputStream.write(buffer, 0, unit);
                onProgress((int) (current * 100.0 / size), current, size);
            }
            outputStream.flush();
            UIThreadUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    onSuccess(file);
                }
            });
        } catch (final Exception e) {
            UIThreadUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    NetException
                            .exception("文件流异常"+ e.getMessage());
                }
            });
        } finally {
            if (null != outputStream) {
                outputStream.close();
            }
            if (null != inputStream) {
                inputStream.close();
            }
        }

    }

}
