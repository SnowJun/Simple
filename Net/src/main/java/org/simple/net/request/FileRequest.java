package org.simple.net.request;

import org.simple.net.request.body.FileBody;

import java.io.File;

/**
 * org.simple.net.request
 *
 * @author Simple
 * @date 2020/9/27
 * @desc
 */
public class FileRequest extends BodyRequest<FileBody,FileRequest> {


    public FileRequest() {
        body = new FileBody();
    }

    /**
     * 要上传的文件
     *
     * @param file
     */
    public FileRequest file(File file) {
        body.setFile(file);
        return this;
    }


}
