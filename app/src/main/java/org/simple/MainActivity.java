package org.simple;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.net.SimpleNet;
import org.simple.net.angency.NetAgencyEnum;
import org.simple.net.callback.StringCallBack;

/**
 * @author Simple
 */
public class MainActivity extends AppCompatActivity {

    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvInfo = findViewById(R.id.tv_info);
        test();
    }

    private void test() {
        SimpleNet.init().setNetAgency(NetAgencyEnum.AGENCY_HTTPURLCONNECTION).build();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id","6d6a190d3b9a27fe958ed0ce01e161e9");
            jsonObject.put("helpUserId","10000040");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SimpleNet
                .<JSONObject>post("https://app-dev.qms888.com/qmsnode/card/updateCardVoucherHelpShare")
                .body(jsonObject)
                .excute(new StringCallBack() {
            @Override
            public void onSuccess(String result) {
                tvInfo.setText("请求响应"+result);
            }

            @Override
            public void onFail(String reason) {
                SimpleLog.e("onFail：reason->" + reason);
            }

            @Override
            public void onException(Exception e) {
                SimpleLog.e("onException：e->" + e.getMessage());
                SimpleLog.e("onException：e->" + e.getCause());
                SimpleLog.e("onException：e->" + e.getClass());
            }
        });

    }
}