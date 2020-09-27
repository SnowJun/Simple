package org.simple.net.request.body;

import java.io.File;

/**
 * org.simple.net.request.body
 *
 * @author Simple
 * @date 2020/9/27
 * @desc
 */
public class FileBody extends RequestBody {

    private File file;

    @Override
    public BodyType type() {
        return BodyType.TYPE_FILE;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

}
