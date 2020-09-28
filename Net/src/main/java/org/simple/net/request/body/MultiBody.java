package org.simple.net.request.body;

import java.io.File;
import java.util.List;
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
     * 一个key  一个文件
     */
    private Map<String, File> fileMap;

    /**
     * 添加文件列表参数
     * 一个key  多个文件
     */
    private Map<String, List<File>> fileListMap;

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

    public Map<String, List<File>> getFileListMap() {
        return fileListMap;
    }

    public void setFileListMap(Map<String, List<File>> fileListMap) {
        this.fileListMap = fileListMap;
    }

}
