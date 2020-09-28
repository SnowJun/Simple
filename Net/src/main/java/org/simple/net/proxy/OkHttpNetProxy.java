package org.simple.net.proxy;

import org.simple.net.SimpleNet;
import org.simple.net.callback.NetCallBack;
import org.simple.net.exception.ExceptionCode;
import org.simple.net.exception.NetException;
import org.simple.net.https.Https;
import org.simple.net.request.BodyRequest;
import org.simple.net.request.Request;
import org.simple.net.request.body.BodyType;
import org.simple.net.request.body.FileBody;
import org.simple.net.request.body.JsonBody;
import org.simple.net.request.body.MultiBody;
import org.simple.net.response.Body;
import org.simple.net.response.Code;
import org.simple.net.response.Response;
import org.simple.net.util.SimpleLog;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * org.simple.net
 *
 * @author Simple
 * @date 2020/9/9
 * @desc OkHttp请求口
 */
public class OkHttpNetProxy implements NetProxy {

    private int retryCount;
    private OkHttpClient client;
    private OkHttpClient.Builder builder = new OkHttpClient.Builder();

    @Override
    public void init() {
        builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                SimpleLog.d(message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY));
        client = builder.build();
    }


    @Override
    public void setConnectionTimeOut(long connectionTimeOut) {
        builder.connectTimeout(connectionTimeOut, TimeUnit.MILLISECONDS);
    }

    @Override
    public void setWriteTimeOut(long writeTimeOut) {
        builder.writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS);
    }

    @Override
    public void setReadTimeOut(long readTimeOut) {
        builder.readTimeout(readTimeOut, TimeUnit.MILLISECONDS);
    }


    @Override
    public void excute(final Request request, final NetCallBack callBack) {
        request.setRetryCount(retryCount);
        Call call = genCall(request);
        final Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e instanceof SocketTimeoutException) {
                    if (request.getRetryCount() == 0) {
                        callBack.onException(NetException.exception(ExceptionCode.CODE_TIME_OUT_EXCEPTION, "网络超时:" + e.getMessage()));
                    } else {
                        request.setRetryCount(request.getRetryCount() - 1);
                        genCall(request).enqueue(this);
                    }
                }
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                Response result = transformResponse(response);
                result.setRequest(request);
                callBack.parse(result);
            }
        };
        call.enqueue(callback);
    }


    @Override
    public void retryCount(int count) {
        this.retryCount = count;
    }


    @Override
    public Response excute(Request request) {
        request.setRetryCount(retryCount);
        Call call = genCall(request);
        try {
            okhttp3.Response response = call.execute();
            Response result = transformResponse(response);
            result.setRequest(request);
            return result;
        } catch (IOException e) {
            Response response = new Response();
            response.setCode(Code.RESP0NSE_EXCEPTION);
            response.setMessage(NetException.exception(ExceptionCode.CODE_PARSE_EXCEPTION, "同步执行异常：" + e.getMessage()).toString());
            return response;
        }
    }


    @Override
    public void cancel(Object tag) {
        List<Call> callsRunning = client.dispatcher().runningCalls();
        List<Call> callsQuene = client.dispatcher().queuedCalls();
        for (Call call : callsRunning) {
            Object tagRequest = call.request().tag();
            if (tagRequest == tag) {
                call.cancel();
            }
        }
        for (Call call : callsQuene) {
            Object tagRequest = call.request().tag();
            if (tagRequest == tag) {
                call.cancel();
            }
        }
    }

    @Override
    public void cancelAll() {
        List<Call> callsRunning = client.dispatcher().runningCalls();
        List<Call> callsQuene = client.dispatcher().queuedCalls();
        for (Call call : callsRunning) {
            call.cancel();
        }
        for (Call call : callsQuene) {
            call.cancel();
        }
    }

    @Override
    public void https() {
        Https https = new Https();
        Https.HttpsParas paras = https.getHttpsParas();
        builder.sslSocketFactory(paras.getSslSocketFactory(),paras.getX509TrustManager());
        builder.hostnameVerifier(paras.getTrustAllHost());
    }


    /**
     * 转换返回
     *
     * @param response
     * @return
     * @throws IOException
     */
    private Response transformResponse(okhttp3.Response response) throws IOException {
        Response result = new Response();
        result.setCode(response.code());
        result.setMessage(response.message());
        result.setProtocal(response.protocol().toString());

        Body body = new Body();
        if (null != response.body()) {
            body.setLength(response.body().contentLength());
            body.setInputStreamData(response.body().byteStream());
        }
        result.setBody(body);

        Headers headers = response.headers();
        Set<String> names = headers.names();
        for (String name : names) {
            result.addHeader(name, headers.get(name));
        }
        return result;
    }


    private Call genCall(Request request) {
        okhttp3.Request.Builder okHttpRequestBuilder = new okhttp3.Request.Builder();
        Map<String, String> commonHeaders = SimpleNet.getInstance().getCommonHeaders();
        if (null != commonHeaders) {
            for (String str : commonHeaders.keySet()) {
                okHttpRequestBuilder.addHeader(str, commonHeaders.get(str));
            }
        }
        Map<String, String> headers = request.getHeader();
        if (null != headers) {
            for (String str : headers.keySet()) {
                okHttpRequestBuilder.addHeader(str, headers.get(str));
            }
        }
        if (request.getMethod().isHasBody()) {
            //需要body体的请求参数添加
            addBodyParas(okHttpRequestBuilder, request);
            okHttpRequestBuilder.url(request.getUrl());
        } else {
            //不需要body体的请求参数添加
            addNoBodyParas(okHttpRequestBuilder, request);
        }
        okHttpRequestBuilder.tag(request.getTag());
        okhttp3.Request okHttpRequest = okHttpRequestBuilder.build();
        return client.newCall(okHttpRequest);
    }

    /**
     * 添加body参数
     *
     * @param okHttpRequestBuilder
     * @param request
     */
    private void addBodyParas(okhttp3.Request.Builder okHttpRequestBuilder, Request request) {
        BodyRequest bodyRequest = (BodyRequest) request;
        Map<String, String> paras = bodyRequest.getParas();
        BodyType type = bodyRequest.getBody().type();
        switch (type) {
            case TYPE_FORM:
                if (null != paras) {
                    Set<String> keys = paras.keySet();
                    FormBody.Builder builder = new FormBody.Builder();
                    for (String key : keys) {
                        builder.add(key, paras.get(key));
                    }
                    FormBody formBody = builder.build();
                    okHttpRequestBuilder = okHttpRequestBuilder.method(request.getMethod().getMethod(), formBody);
                }
                break;
            case TYPE_JSON:
                JsonBody jsonBody = (JsonBody) bodyRequest.getBody();
                MediaType jsonType = MediaType.parse("application/json; charset=utf-8");
                RequestBody requestBody = RequestBody.create(jsonType, (jsonBody.getJsonObject()).toString());
                okHttpRequestBuilder = okHttpRequestBuilder.method(request.getMethod().getMethod(), requestBody);
                break;
            case TYPE_FILE:
                File file = ((FileBody) bodyRequest.getBody()).getFile();
                RequestBody requestBody1 = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("*/*"), file))
                        .build();
                okHttpRequestBuilder = okHttpRequestBuilder.method(request.getMethod().getMethod(), requestBody1);
                break;
            case TYPE_MULTI:
                MultiBody multiBody = (MultiBody) bodyRequest.getBody();
                Map<String, File> fileMap = multiBody.getFileMap();
                Map<String, List<File>> fileListMap = multiBody.getFileListMap();
                MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM);
                if (null != paras) {
                    for (String key : paras.keySet()) {
                        requestBodyBuilder.addFormDataPart(key, paras.get(key));
                    }
                }
                if (null != fileMap) {
                    Set<Map.Entry<String, File>> entries = fileMap.entrySet();
                    for (Map.Entry<String, File> entry : entries) {
                        requestBodyBuilder.addFormDataPart(entry.getKey(), entry.getValue().getName(), RequestBody.create(MediaType.parse("*/*"), entry.getValue()));
                    }
                }
                if (null != fileListMap) {
                    Set<Map.Entry<String, List<File>>> entries = fileListMap.entrySet();
                    for (Map.Entry<String, List<File>> entry : entries) {
                        List<File> files = entry.getValue();
                        if (null != files && !files.isEmpty()) {
                            for (File file1 : files) {
                                requestBodyBuilder.addFormDataPart(entry.getKey(),file1.getName(), RequestBody.create(MediaType.parse("*/*"), file1));
                            }
                        }
                    }
                }
                RequestBody requestBody2 = requestBodyBuilder.build();
                okHttpRequestBuilder = okHttpRequestBuilder.method(request.getMethod().getMethod(), requestBody2);
                break;
            default:
                break;
        }
    }

    /**
     * 添加无body体参数
     *
     * @param okHttpRequestBuilder
     * @param request
     */
    private void addNoBodyParas(okhttp3.Request.Builder okHttpRequestBuilder, Request request) {
        Map<String, String> paras = request.getParas();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(request.getUrl()).newBuilder();
        for (String key : paras.keySet()) {
            urlBuilder.addQueryParameter(key, paras.get(key));
        }
        okHttpRequestBuilder.url(urlBuilder.build());
    }

}
