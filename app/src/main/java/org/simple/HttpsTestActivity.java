package org.simple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.net.SimpleNet;
import org.simple.net.SimpleNetBuilder;
import org.simple.net.angency.NetAgencyEnum;
import org.simple.net.callback.StringCallBack;
import org.simple.net.request.MultiRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Simple
 */
public class HttpsTestActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 返回内容
     */
    private TextView tvContent;
    /**
     * 代理
     */
    private TextView tvAgency;

    private Button btnGet;
    private Button btnPostParas;
    private Button btnPostJson;
    private Button btnPostFile;
    private Button btnPostMoreFile;
    private Button btnPostParasFile;

    private Button btnHttpUrlConnection;
    private Button btnOkHttp;

    private boolean isParasAndFile;
    private static final String URL = "https://172.16.30.57:8085/simple/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_https_test);
        initView();
        SimpleNet.getInstance().init(new SimpleNetBuilder().setNetAgency(NetAgencyEnum.AGENCY_HTTPURLCONNECTION).addHeader("deviceId","aaaaaaaaaaaaa-ddddddddddd").https());
    }


    private void initView() {
        tvContent = findViewById(R.id.tv_content);
        tvAgency = findViewById(R.id.tv_agency);
        btnGet = findViewById(R.id.btn_get);
        btnPostParas = findViewById(R.id.btn_post_paras);
        btnPostJson = findViewById(R.id.btn_post_json);
        btnPostFile = findViewById(R.id.btn_post_file);
        btnPostMoreFile = findViewById(R.id.btn_post_more_file);
        btnPostParasFile = findViewById(R.id.btn_post_paras_file);
        btnHttpUrlConnection = findViewById(R.id.btn_httpurlconnection);
        btnOkHttp = findViewById(R.id.btn_okhttp);
        btnGet.setOnClickListener(this);
        btnPostParas.setOnClickListener(this);
        btnPostJson.setOnClickListener(this);
        btnPostFile.setOnClickListener(this);
        btnPostMoreFile.setOnClickListener(this);
        btnPostParasFile.setOnClickListener(this);
        btnHttpUrlConnection.setOnClickListener(this);
        btnOkHttp.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        isParasAndFile = false;
        switch (v.getId()) {
            case R.id.btn_get:
                doGet();
                break;
            case R.id.btn_post_paras:
                doPostParas();
                break;
            case R.id.btn_post_json:
                doPostJson();
                break;
            case R.id.btn_okhttp:
                SimpleNet.getInstance().init(new SimpleNetBuilder().setNetAgency(NetAgencyEnum.AGENCY_OKHTTP).addHeader("deviceId","ccccccccccc-ddddddddddd").https());
                tvAgency.setText("代理：OkHttp");
                break;
            case R.id.btn_httpurlconnection:
                tvAgency.setText("代理：HttpUrlConnection");
                SimpleNet.getInstance().init(new SimpleNetBuilder().setNetAgency(NetAgencyEnum.AGENCY_HTTPURLCONNECTION).addHeader("deviceId","aaaaaaaaaaaaa-ddddddddddd").https());
                break;
            case R.id.btn_post_file:
                selectFile(1);
                break;
            case R.id.btn_post_more_file:
                selectFile(3);
                break;
            case R.id.btn_post_paras_file:
                isParasAndFile = true;
                selectFile(1);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                SimpleLog.d("回调数据数量：" + images.size());
                for (ImageItem item : images) {
                    SimpleLog.d("ImageItem-path：" + item.path);
                }
                if (null != images && images.size() == 1) {
                    ImageItem imageItem = images.get(0);
                    File file = new File(imageItem.path);
                    if (isParasAndFile) {
                        uploadFileAndParas(file);
                    } else {
                        uploadFile(file);
                    }
                } else if (null != images && images.size() > 1) {
                    List<File> files = new ArrayList<>(images.size());
                    for (ImageItem item : images) {
                        File file = new File(item.path);
                        files.add(file);
                    }
                    uploadFiles(files);
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * 选择文件
     *
     * @param count 选择的数量
     */
    private void selectFile(int count) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        if (count == 1) {
            imagePicker.setMultiMode(false);
        } else if (count > 1) {
            imagePicker.setMultiMode(true);
            imagePicker.setSelectLimit(count);
        } else {
            return;
        }
        imagePicker.setCrop(false);
        imagePicker.setShowCamera(false);
        imagePicker.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
                SimpleLog.d("displayImage-path:" + path);
                Glide.with(getApplicationContext()).load(path).into(imageView);
            }

            @Override
            public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
                SimpleLog.d("displayImagePreview-path:" + path);
                Glide.with(getApplicationContext()).load(path).into(imageView);
            }

            @Override
            public void clearMemoryCache() {
                SimpleLog.d("clearMemoryCache");
            }
        });
        imagePicker.addOnImageSelectedListener(new ImagePicker.OnImageSelectedListener() {
            @Override
            public void onImageSelected(int position, ImageItem item, boolean isAdd) {
                SimpleLog.d("选中position：" + position);
                SimpleLog.d("ImageItem-path：" + item.path);

            }
        });
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, 100);

    }


    private void uploadFile(File file) {
        SimpleNet.postFile(URL + "/uploadFile").file(file).excute(new StringCallBack() {
            @Override
            public void onSuccess(String result) {
                tvContent.setText(result);
            }

            @Override
            public void onFail(String reason) {
                SimpleLog.e("onFail：reason->" + reason);
            }

            @Override
            public void onException(Exception e) {
                SimpleLog.e("onException：e->" + e.getMessage());
            }
        });
        tvContent.setText("文件上传中..");
    }


    private void uploadFileAndParas(File file) {
        SimpleNet.postMulti(URL + "/uploadAndLogin")
                .addParas("userName","snowjun")
                .addParas("password","123456").addFile("file",file).excute(new StringCallBack() {
            @Override
            public void onSuccess(String result) {
                tvContent.setText(result);
            }

            @Override
            public void onFail(String reason) {
                SimpleLog.e("onFail：reason->" + reason);
            }

            @Override
            public void onException(Exception e) {
                SimpleLog.e("onException：e->" + e.getMessage());
            }
        });
        tvContent.setText("文件上传中..");
    }

    /**
     * 上传多文件
     *
     * @param files
     */
    private void uploadFiles(List<File> files) {
        MultiRequest request = SimpleNet.postMulti(URL + "/uploadFiles");
        request.addFiles("files", files);
        request.excute(new StringCallBack() {
            @Override
            public void onSuccess(String result) {
                tvContent.setText(result);
            }

            @Override
            public void onFail(String reason) {
                SimpleLog.e("onFail：reason->" + reason);
            }

            @Override
            public void onException(Exception e) {
                SimpleLog.e("onException：e->" + e.getMessage());
            }
        });
        tvContent.setText("多文件上传中..");
    }

    private void doPostJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName", "SnowJun");
            jsonObject.put("password", "123456");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SimpleNet
                .<JSONObject>postJson(URL + "login1")
                .json(jsonObject)
                .excute(new StringCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        tvContent.setText(result);
                    }

                    @Override
                    public void onFail(String reason) {
                        SimpleLog.e("onFail：reason->" + reason);
                    }

                    @Override
                    public void onException(Exception e) {
                        SimpleLog.e("onException：e->" + e.getMessage());
                    }
                });
        tvContent.setText("请求中，请稍后...");
    }

    private void doPostParas() {
        Map<String, String> paras = new HashMap<>();
        paras.put("userName", "snowjun");
        paras.put("password", "123456");
        SimpleNet.postForm(URL + "/login").addHeader("session","aaaaa-bbbbb").paras(paras).excute(new StringCallBack() {
            @Override
            public void onSuccess(String result) {
                tvContent.setText(result);
            }

            @Override
            public void onFail(String reason) {
                SimpleLog.e("onFail：reason->" + reason);
            }

            @Override
            public void onException(Exception e) {
                SimpleLog.e("onException：e->" + e.getMessage());
            }
        });
        tvContent.setText("请求中，请稍后...");
    }

    private void doGet() {
        Map<String, String> paras = new HashMap<>();
        paras.put("id", "id111");
        paras.put("code", "code222");
        SimpleNet.get(URL+"/info").paras(paras).excute(new StringCallBack() {
            @Override
            public void onSuccess(String result) {
                tvContent.setText(result);
            }

            @Override
            public void onFail(String reason) {
                SimpleLog.e("onFail：reason->" + reason);
            }

            @Override
            public void onException(Exception e) {
                SimpleLog.e("onException：e->" + e.getMessage());
            }
        });
        tvContent.setText("请求中，请稍后...");
    }

}