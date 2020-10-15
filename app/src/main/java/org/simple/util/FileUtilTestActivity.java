package org.simple.util;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.simple.R;

import java.io.File;

/**
 * org.simple.util
 *
 * @author Simple
 * @date 2020/10/14
 * @desc
 */
public class FileUtilTestActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView tvPath;
    private TextView tvResult;
    private TextView tvResultExtral;

    private Button btnCreateFile;
    private Button btnCreateExtralFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util_file);
        initView();
        setInfo();
    }

    private void setInfo() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("路径信息\n");
        buffer.append("app缓存：" + SimpleUtil.getFileUtil(this).getFilePathUtil().getAppCacheDir(this).getAbsolutePath() + "\n");
        buffer.append("app特定：" + SimpleUtil.getFileUtil(this).getFilePathUtil().getAppDir(this, "testDir").getAbsolutePath() + "\n");
        buffer.append("app外部文件：" + SimpleUtil.getFileUtil(this).getFilePathUtil().getAppExtralFileDir(this).getAbsolutePath() + "\n");
        buffer.append("app外部缓存：" + SimpleUtil.getFileUtil(this).getFilePathUtil().getAppExtralCacheDir(this).getAbsolutePath() + "\n");
        buffer.append("app特定外部文件：" + SimpleUtil.getFileUtil(this).getFilePathUtil().getAppExtralFileDir(this, Environment.DIRECTORY_MUSIC).getAbsolutePath() + "\n");
        buffer.append("app文件：" + SimpleUtil.getFileUtil(this).getFilePathUtil().getAppFileDir(this).getAbsolutePath() + "\n");

        buffer.append("data目录：" + SimpleUtil.getFileUtil(this).getFilePathUtil().getDataDir().getAbsolutePath() + "\n");
        buffer.append("外部目录：" + SimpleUtil.getFileUtil(this).getFilePathUtil().getExtralDir().getAbsolutePath() + "\n");
        tvPath.setText(buffer.toString());
    }

    private void initView() {
        tvPath = findViewById(R.id.tv_path);
        tvResult = findViewById(R.id.tv_result);
        tvResultExtral = findViewById(R.id.tv_result_extral);
        btnCreateFile = findViewById(R.id.btn_create_file);
        btnCreateExtralFile = findViewById(R.id.btn_create_extral_file);
        btnCreateFile.setOnClickListener(this);
        btnCreateExtralFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create_file:
                createFile();
                break;
            case R.id.btn_create_extral_file:
                createExtralFile();
                break;
            default:
                break;
        }
    }

    /**
     * 创建外部文件   需要读写权限 不然会报错
     */
    private void createExtralFile() {

        File CacheDir = SimpleUtil.getFileUtil(this).getFilePathUtil().getDataDir();
        File TestDir = SimpleUtil.getFileUtil(this).getFilePathUtil().getExtralDir();

        File CacheDirFile = SimpleUtil.getFileUtil(this).createFile(CacheDir.getAbsolutePath(), "test.jpg");
        File TestDirFile = SimpleUtil.getFileUtil(this).createFile(TestDir.getAbsolutePath(), "test.jpg");


        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("外部创建结果\n");
        if (null != CacheDirFile) {
            stringBuffer.append("CacheDirFile:" + CacheDirFile.getAbsolutePath()+"\n");
        }
        if (null != TestDirFile) {
            stringBuffer.append("TestDirFile:" + TestDirFile.getAbsolutePath()+"\n");
        }
        tvResultExtral.setText(stringBuffer.toString());
    }

    private void createFile() {
        File appCacheDir = SimpleUtil.getFileUtil(this).getFilePathUtil().getAppCacheDir(this);
        File appTestDir = SimpleUtil.getFileUtil(this).getFilePathUtil().getAppDir(this, "testDir");
        File appFileDir = SimpleUtil.getFileUtil(this).getFilePathUtil().getAppFileDir(this);
        File appCacheExtralDir = SimpleUtil.getFileUtil(this).getFilePathUtil().getAppExtralCacheDir(this);
        File appExtralDir = SimpleUtil.getFileUtil(this).getFilePathUtil().getAppExtralFileDir(this);
        File appExtralPicDir = SimpleUtil.getFileUtil(this).getFilePathUtil().getAppExtralFileDir(this, Environment.DIRECTORY_PICTURES);

        File appCacheDirFile = SimpleUtil.getFileUtil(this).createFile(appCacheDir.getAbsolutePath(), "test.jpg");
        File appTestDirFile = SimpleUtil.getFileUtil(this).createFile(appTestDir.getAbsolutePath(), "test.jpg");
        File appFileDirFile = SimpleUtil.getFileUtil(this).createFile(appFileDir.getAbsolutePath(), "test.jpg");
        File appCacheExtralDirFile = SimpleUtil.getFileUtil(this).createFile(appCacheExtralDir.getAbsolutePath(), "test.jpg");
        File appExtralDirFile = SimpleUtil.getFileUtil(this).createFile(appExtralDir.getAbsolutePath(), "test.jpg");
        File appExtralPicDirFile = SimpleUtil.getFileUtil(this).createFile(appExtralPicDir.getAbsolutePath(), "test.jpg");


        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("内部创建结果\n").
                append("appCacheDirFile:" + appCacheDirFile.getAbsolutePath()+"\n").
                append("appTestDirFile:" + appTestDirFile.getAbsolutePath()+"\n").
                append("appFileDirFile:" + appFileDirFile.getAbsolutePath()+"\n").
                append("appCacheExtralDirFile:" + appCacheExtralDirFile.getAbsolutePath()+"\n").
                append("appExtralDirFile:" + appExtralDirFile.getAbsolutePath()+"\n").
                append("appExtralPicDirFile:" + appExtralPicDirFile.getAbsolutePath()+"\n");
        tvResult.setText(stringBuffer.toString());

    }

}
