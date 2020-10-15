package org.simple.util.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import org.simple.util.SimpleLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * org.simple.util.util
 *
 * @author Simple
 * @date 2020/10/14
 * @desc
 */
public class FileUtil {

    private Context context;

    /**
     * 文件路径工具
     */
    private FilePathUtil filePathUtil;

    public FileUtil(Context context) {
        this.context = context;
        filePathUtil = new FilePathUtil();
    }

    public FilePathUtil getFilePathUtil() {
        return filePathUtil;
    }

    /**
     * 文件是否存在
     *
     * @param file
     * @return
     */
    public boolean isExist(File file) {
        if (null == file) {
            return false;
        }
        if (file.exists()) {
            return true;
        }
        return isExistApiQ(file);
    }

    private boolean isExistApiQ(File file) {
        if (null == file) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Uri uri = Uri.parse(file.getAbsolutePath());
            ContentResolver resolver = context.getContentResolver();
            try {
                //只读模式打开
                //"r"只读"w"只写"wa"只写追加"rw"读写"rwt"
                AssetFileDescriptor assetFileDescriptor = resolver.openAssetFileDescriptor(uri, "r");
                if (null == assetFileDescriptor) {
                    return false;
                }
                try {
                    assetFileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }


    /**
     * 创建文件
     * 如果出现异常  如权限拒绝等会返回空的file
     *
     * @param parent
     * @param name
     * @return
     */
    public File createFile(String parent, String name) {
        File parentDir = new File(parent);
        if (!parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                return null;
            }
        }
        File file = new File(parent, name);
        if (isExist(file)) {
            return file;
        }
        try {
            boolean createResult = file.createNewFile();
            if (createResult) {
                return file;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            SimpleLog.e("创建异常：" + e.getMessage());
        }
        return null;
    }

    /**
     * 在App缓存目录 创建文件
     * 如果出现异常  如权限拒绝等会返回空的file
     *
     * @param name
     * @return
     */
    public File createFileInAppCache(String name) {
        return createFile(filePathUtil.getAppCacheDir(context).getAbsolutePath(), name);
    }

    /**
     * 在App文件目录 创建文件
     * 如果出现异常  如权限拒绝等会返回空的file
     *
     * @param name
     * @return
     */
    public File createFileInAppFile(String name) {
        return createFile(filePathUtil.getAppFileDir(context).getAbsolutePath(), name);
    }

    /**
     * 在App外部文件目录 创建文件
     * 如果出现异常  如权限拒绝等会返回空的file
     *
     * @param name
     * @return
     */
    public File createFileInAppExtralFile(String name) {
        return createFile(filePathUtil.getAppExtralFileDir(context).getAbsolutePath(), name);
    }

    /**
     * 在App外部缓存目录 创建文件
     * 如果出现异常  如权限拒绝等会返回空的file
     *
     * @param name
     * @return
     */
    public File createFileInAppExtralCache(String name) {
        return createFile(filePathUtil.getAppExtralCacheDir(context).getAbsolutePath(), name);
    }



    public static class FilePathUtil {

        /**
         * 获取app缓存目录
         * 类似于 /data/user/0/packname/cache
         *
         * @param context
         * @return
         */
        public File getAppCacheDir(Context context) {
            return context.getCacheDir();
        }

        /**
         * 获取app目录下的自定义路径
         * 类似于/data/user/0/packname/dir
         *
         * @param context
         * @param dir
         * @return
         */
        public File getAppDir(Context context, String dir) {
            return context.getDir(dir, Context.MODE_PRIVATE);
        }

        /**
         * 获取app目录下的file目录
         * 类似于data/user/0/packname/files
         *
         * @param context
         * @return
         */
        public File getAppFileDir(Context context) {
            return context.getFilesDir();
        }

        /**
         * 获取根目录下
         * 类似于/storage/emulated/0/Android/data/packname/files
         *
         * @param context
         * @return
         */
        public File getAppExtralFileDir(Context context) {
            return context.getExternalFilesDir("");
        }

        /**
         * 获取根目录下
         * 类似于/storage/emulated/0/Android/data/packname/files/Music等
         *
         * @param context
         * @param type    {@link Environment } 如Environment.DIRECTORY_MUSIC 等等
         * @return
         */
        public File getAppExtralFileDir(Context context, String type) {
            return context.getExternalFilesDir(type);
        }


        /**
         * 获取根目录下 缓存路径
         * 类似于/storage/emulated/0/Android/data/packname/cache
         *
         * @param context
         * @return
         */
        public File getAppExtralCacheDir(Context context) {
            return context.getExternalCacheDir();
        }

        /**
         * 获取外部data根目录
         * 类似于/data
         * 读写需要权限
         *
         * @return
         */
        public File getDataDir() {
            return Environment.getDataDirectory();
        }

        /**
         * 获取外部出处根目录
         * 类似于/storage/emulated/0
         * 读写需要权限
         *
         * @return
         */
        public File getExtralDir() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                return Environment.getStorageDirectory();
            } else {
                return Environment.getExternalStorageDirectory();
            }
        }


    }


}
