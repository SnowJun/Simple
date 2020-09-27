package org.simple;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Simple
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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

    private Button btnHttpUrlConnection;
    private Button btnOkHttp;

    private static final String URL = "http://172.16.30.57:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        test();
    }


    private void initView() {
        tvContent = findViewById(R.id.tv_content);
        tvAgency = findViewById(R.id.tv_agency);
        btnGet = findViewById(R.id.btn_get);
        btnPostParas = findViewById(R.id.btn_post_paras);
        btnPostJson = findViewById(R.id.btn_post_json);
        btnPostFile = findViewById(R.id.btn_post_file);
        btnHttpUrlConnection = findViewById(R.id.btn_httpurlconnection);
        btnOkHttp = findViewById(R.id.btn_okhttp);
        btnGet.setOnClickListener(this);
        btnPostParas.setOnClickListener(this);
        btnPostJson.setOnClickListener(this);
        btnPostFile.setOnClickListener(this);
        btnHttpUrlConnection.setOnClickListener(this);
        btnOkHttp.setOnClickListener(this);
    }

    private void test() {


    }

    @Override
    public void onClick(View v) {
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
                SimpleNet.getInstance().init(new SimpleNetBuilder().setNetAgency(NetAgencyEnum.AGENCY_OKHTTP));
                tvAgency.setText("代理：OkHttp");
                break;
            case R.id.btn_httpurlconnection:
                tvAgency.setText("代理：HttpUrlConnection");
                SimpleNet.getInstance().init(new SimpleNetBuilder().setNetAgency(NetAgencyEnum.AGENCY_HTTPURLCONNECTION));
                break;
            case R.id.btn_post_file:
                selectFile();
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
                SimpleLog.d("回调数据数量："+images.size());
                for (ImageItem item : images) {
                    SimpleLog.d("ImageItem-path："+item.path);
                }
                if (null != images && images.size()>0){
                    ImageItem imageItem = images.get(0);
                    File file = new File(imageItem.path);
                    uploadFile(file);
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * 选择文件
     */
    private void selectFile() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(false);
        imagePicker.setShowCamera(false);
        imagePicker.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
                SimpleLog.d("displayImage-path:"+path);
                imageView.setImageBitmap(BitmapFactory.decodeFile(path));
            }

            @Override
            public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
                SimpleLog.d("displayImagePreview-path:"+path);
                imageView.setImageBitmap(BitmapFactory.decodeFile(path));
            }

            @Override
            public void clearMemoryCache() {
                SimpleLog.d("clearMemoryCache");
            }
        });
        imagePicker.addOnImageSelectedListener(new ImagePicker.OnImageSelectedListener() {
            @Override
            public void onImageSelected(int position, ImageItem item, boolean isAdd) {
                SimpleLog.d("选中position："+position);
                SimpleLog.d("ImageItem-path："+item.path);

            }
        });
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, 100);

    }


    private void uploadFile(File file) {
        SimpleNet.postFile(URL+"/uploadFile").file(file).excute(new StringCallBack() {
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
        SimpleNet.postForm(URL + "/login").paras(paras).excute(new StringCallBack() {
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
        SimpleNet.get(URL + "/info").paras(paras).excute(new StringCallBack() {
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