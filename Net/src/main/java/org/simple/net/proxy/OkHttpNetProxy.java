package org.simple.net.proxy;

import org.json.JSONObject;
import org.simple.net.callback.NetCallBack;
import org.simple.net.exception.ExceptionCode;
import org.simple.net.exception.NetException;
import org.simple.net.header.Header;
import org.simple.net.paras.Paras;
import org.simple.net.request.Request;
import org.simple.net.request.RequestMethod;
import org.simple.net.response.Body;
import org.simple.net.response.Code;
import org.simple.net.response.Response;
import org.simple.net.util.SimpleLog;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
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

        Header header = new Header();
        Headers headers = response.headers();
        Set<String> names = headers.names();
        for (String name : names) {
            header.addHeader(name, headers.get(name));
        }
        result.setHeader(header);
        return result;
    }


    private Call genCall(Request request) {
        okhttp3.Request.Builder okHttpRequestBuilder = new okhttp3.Request.Builder();
        Header header = request.getHeader();
        if (null != header && null != header.getHeaders()) {
            for (String str : header.getHeaders().keySet()) {
                okHttpRequestBuilder.addHeader(str, header.getHeaders().get(str));
            }
        }
        //先判断参数  有参数以参数为准
        Paras paras = request.getParas();
        if (request.getMethod() == RequestMethod.METHOD_GET) {
            if (null != paras && null != paras.getParas() && !paras.getParas().isEmpty()) {
                HttpUrl.Builder urlBuilder = HttpUrl.parse(request.getUrl()).newBuilder();
                for (String key : paras.getParas().keySet()) {
                    urlBuilder.addQueryParameter(key, paras.getParas().get(key));
                }
                okHttpRequestBuilder.url(urlBuilder.build());
            }else {
                okHttpRequestBuilder.url(request.getUrl());
            }
        } else {
            okHttpRequestBuilder.url(request.getUrl());
        }
        if (request.getMethod() != RequestMethod.METHOD_GET){
            //先判断参数  有参数以参数为准
            if (null != paras && null != paras.getParas() && !paras.getParas().isEmpty()) {
                Set<String> keys = paras.getParas().keySet();
                FormBody.Builder builder = new FormBody.Builder();
                for (String key : keys) {
                    builder.add(key, paras.getParas().get(key));
                }
                FormBody formBody = builder.build();
                okHttpRequestBuilder = okHttpRequestBuilder.method(request.getMethod().getMethod(), formBody);
            } else if (null != request.getBody()) {
                //json 参数
                Object o = request.getBody();
                if (o instanceof JSONObject) {
                    MediaType jsonType = MediaType.parse("application/json; charset=utf-8");
                    RequestBody requestBody = RequestBody.create(jsonType, ((JSONObject) o).toString());
                    okHttpRequestBuilder = okHttpRequestBuilder.method(request.getMethod().getMethod(), requestBody);
                } else if (o instanceof File) {
                    File file = (File) o;
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/png"), file))
                            .build();
                    okHttpRequestBuilder = okHttpRequestBuilder.method(request.getMethod().getMethod(), requestBody);
                }
            }
        }
        okHttpRequestBuilder.tag(request.getTag());
        okhttp3.Request okHttpRequest = okHttpRequestBuilder.build();
        return client.newCall(okHttpRequest);
    }

}
