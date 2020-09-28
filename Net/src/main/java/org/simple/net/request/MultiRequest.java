package org.simple.net.request;

import org.simple.net.request.body.MultiBody;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * org.simple.net.request
 *
 * @author Simple
 * @date 2020/9/27
 * @desc
 */
public class MultiRequest extends BodyRequest<MultiBody, MultiRequest> {


    public MultiRequest() {
        body = new MultiBody();
        body.setFileMap(new HashMap<String, File>());
        body.setFileListMap(new HashMap<String, List<File>>());
    }

    /**
     * 添加要上传的文件
     * key  file  一一对应
     *
     * @param name
     * @param file
     */
    public MultiRequest addFile(String name, File file) {
        body.getFileMap().put(name, file);
        return this;
    }


    /**
     * 添加要上传的文件list
     * 一个key  多个file
     *
     * @param key
     * @param files
     * @return
     */
    public MultiRequest addFiles(String key, List<File> files) {
        body.getFileListMap().put(key, files);
        return this;
    }

    /**
     * 是否存在文件
     * @return
     */
    public boolean hasFile() {
        if (!body.getFileMap().isEmpty()) {
            return true;
        }
        if (!body.getFileListMap().isEmpty()) {
            Set<Map.Entry<String, List<File>>> entrySet = body.getFileListMap().entrySet();
            for (Map.Entry<String, List<File>> entry :
                    entrySet) {
                List<File> files = entry.getValue();
                if (null != files && !files.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

}
