package org.simple.net.request;

import org.simple.net.request.body.MultiBody;

import java.io.File;

/**
 * org.simple.net.request
 *
 * @author Simple
 * @date 2020/9/27
 * @desc
 */
public class MultiRequest extends BodyRequest<MultiBody> {


    public MultiRequest() {
        body = new MultiBody();
    }

    /**
     * 添加要上传的文件
     * @param name
     * @param file
     */
    @Override
    public MultiRequest addFile(String name, File file) {
        body.getFileMap().put(name, file);
        return this;
    }

}
