package org.simple.net.paras;

import androidx.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * org.simple.net.paras
 *
 * @author Simple
 * @date 2020/9/9
 * @desc 请求参数
 */
public class Paras {

    /**
     * 请求参数的键值对
     */
    private Map<String, String> paras = new HashMap<>();

    public Map<String, String> getParas() {
        return paras;
    }

    public void setParas(Map<String, String> paras) {
        this.paras = paras;
    }

    public void addParas(@NonNull String key, String value) {
        paras.put(key, value);
    }

    /**
     * 生成表单参数
     *
     * @return
     */
    public String genParasStr() {
        if (paras.isEmpty()) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        Set<Map.Entry<String, String>> entrySet = paras.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        String string = sb.toString();
        return string.substring(0,string.length()-1);
    }

    /**
     * 生成表单参数
     *
     * @return
     */
    public String genParasStrGet() throws UnsupportedEncodingException {
        if (paras.isEmpty()) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        Set<Map.Entry<String, String>> entrySet = paras.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            sb.append(entry.getKey())
                    .append("=")
                    .append(URLEncoder.encode(entry.getValue(), "utf-8"))
                    .append("&");
        }
        String string = sb.toString();
        return string.substring(0, string.length() - 2);
    }


}
