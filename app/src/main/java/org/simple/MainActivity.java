package org.simple;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.net.SimpleNet;
import org.simple.net.angency.NetAgencyEnum;
import org.simple.net.callback.StringCallBack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Simple
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvContent;
    private Button btnGet;
    private Button btnPostParas;
    private Button btnPostJson;

    private static final String URL = "http://172.16.30.22:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initView();
        test();
    }

    private void init() {
        SimpleNet.init().setNetAgency(NetAgencyEnum.AGENCY_OKHTTP).build();
    }

    private void initView() {
        tvContent = findViewById(R.id.tv_content);
        btnGet = findViewById(R.id.btn_get);
        btnPostParas = findViewById(R.id.btn_post_paras);
        btnPostJson = findViewById(R.id.btn_post_json);
        btnGet.setOnClickListener(this);
        btnPostParas.setOnClickListener(this);
        btnPostJson.setOnClickListener(this);
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
            default:
                break;
        }
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
                .<JSONObject>post(URL+"login1")
                .body(jsonObject)
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
        Map<String,String> paras = new HashMap<>();
        paras.put("userName","snowjun");
        paras.put("password","123456");
        SimpleNet.post(URL+"/login").paras(paras).excute(new StringCallBack() {
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
        Map<String,String> paras = new HashMap<>();
        paras.put("id","id111");
        paras.put("code","code222");
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