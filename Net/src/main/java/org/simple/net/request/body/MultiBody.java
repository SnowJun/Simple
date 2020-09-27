package org.simple.net.request.body;

import java.io.File;
import java.util.Map;

/**
 * org.simple.net.request.body
 *
 * @author Simple
 * @date 2020/9/27
 * @desc
 *
 */
public class MultiBody extends RequestBody {
    /**
     * 文件参数
     */
    private Map<String, File> fileMap;

    @Override
    public BodyType type() {
        return BodyType.TYPE_MULTI;
    }

    public Map<String, File> getFileMap() {
        return fileMap;
    }

    public void setFileMap(Map<String, File> fileMap) {
        this.fileMap = fileMap;
    }


}
