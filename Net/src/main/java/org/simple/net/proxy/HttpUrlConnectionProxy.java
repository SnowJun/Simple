package org.simple.net.proxy;

import org.json.JSONObject;
import org.simple.net.callback.NetCallBack;
import org.simple.net.constants.Constants;
import org.simple.net.exception.ExceptionCode;
import org.simple.net.exception.NetException;
import org.simple.net.header.Header;
import org.simple.net.request.Request;
import org.simple.net.request.RequestMethod;
import org.simple.net.response.Body;
import org.simple.net.response.CloseResponse;
import org.simple.net.response.Code;
import org.simple.net.response.Response;
import org.simple.net.util.SimpleLog;
import org.simple.net.util.TaskUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * org.simple.net.proxy
 *
 * @author Simple
 * @date 2020/9/9
 * @desc
 */
public class HttpUrlConnectionProxy implements NetProxy {


    /**
     * 连接超时时间
     */
    private long connectionTimeOut = Constants.CONNECT_TIME_OUT;
    /**
     * 读取超时时间
     */
    private long readTimeOut = Constants.READ_TIME_OUT;
    /**
     * 写入超时时间
     */
    private long writeTimeOut = Constants.WRITE_TIME_OUT;
    /**
     * 重试次数
     */
    private int retryCount = Constants.RETRY_COUNT;

    private List<Request> requests = new ArrayList<>();

    @Override
    public void retryCount(int count) {
        retryCount = count;
    }

    @Override
    public void init() {

    }


    @Override
    public void setConnectionTimeOut(long connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    @Override
    public void setWriteTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
    }

    @Override
    public void setReadTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
    }


    @Override
    public void excute(final Request request, final NetCallBack callBack) {
        if (request.isCanceled()) {
            SimpleLog.e("网络请求已取消");
            return;
        }
        requests.add(request);
        TaskUtil.getInstance().runTask(new Runnable() {
            @Override
            public void run() {
                try {
                    CloseResponse response = excuteRequest(request);
                    requests.remove(request);
                    if (request.isCanceled()) {
                        SimpleLog.e("网络请求已取消");
                        return;
                    }
                    callBack.parse(response);
                    response.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    requests.remove(request);
                    if (request.isCanceled()) {
                        SimpleLog.e("网络请求已取消");
                        return;
                    }
                    callBack.onException(NetException.exception(ExceptionCode.CODE_PARSE_EXCEPTION, "Url异常：" + e.getMessage()));
                } catch (IOException e) {
                    if (e instanceof SocketTimeoutException) {
                        //超时重试
                        if (request.getRetryCount() > 0) {
                            request.setRetryCount(request.getRetryCount() - 1);
                        }
                        requests.remove(request);
                        if (request.isCanceled()) {
                            SimpleLog.e("网络请求已取消");
                            return;
                        }
                        excute(request, callBack);
                    } else {
                        requests.remove(request);
                        if (request.isCanceled()) {
                            SimpleLog.e("网络请求已取消");
                            return;
                        }
                        callBack.onException(NetException.exception(ExceptionCode.CODE_PARSE_EXCEPTION, "网络超时：" + e.getMessage()));
                    }
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Response excute(Request request) {
        if (request.isCanceled()) {
            SimpleLog.e("网络请求已取消");
            return null;
        }
        requests.add(request);
        try {
            CloseResponse response = excuteRequest(request);
            requests.remove(request);
            if (request.isCanceled()) {
                SimpleLog.e("网络请求已取消");
                return null;
            }
            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Response response = new Response();
            response.setCode(Code.RESP0NSE_EXCEPTION);
            response.setMessage(NetException.exception(ExceptionCode.CODE_PARSE_EXCEPTION, "Url异常：" + e.getMessage()).toString());
            requests.remove(request);
            if (request.isCanceled()) {
                SimpleLog.e("网络请求已取消");
                return null;
            }
            return response;
        } catch (IOException e) {
            if (e instanceof SocketTimeoutException) {
                //超时重试
                if (request.getRetryCount() > 0) {
                    SimpleLog.e("网络超时，进行重试");
                    request.setRetryCount(request.getRetryCount() - 1);
                    requests.remove(request);
                    if (request.isCanceled()) {
                        SimpleLog.e("网络已取消");
                        return null;
                    }
                    excute(request);
                }else {
                    Response response = new Response();
                    response.setCode(Code.RESP0NSE_EXCEPTION);
                    response.setMessage(NetException.exception(ExceptionCode.CODE_PARSE_EXCEPTION, "网络超时：" + e.getMessage()).toString());
                    requests.remove(request);
                    if (request.isCanceled()) {
                        SimpleLog.e("网络请求已取消");
                        return null;
                    }
                    return response;
                }
            } else {
                Response response = new Response();
                response.setCode(Code.RESP0NSE_EXCEPTION);
                response.setMessage(NetException.exception(ExceptionCode.CODE_PARSE_EXCEPTION, "IO异常：" + e.getMessage()).toString());
                requests.remove(request);
                if (request.isCanceled()) {
                    SimpleLog.e("网络请求已取消");
                    return null;
                }
                return response;
            }
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cancel(Object tag) {
        for (Request request : requests) {
            if (request.getTag() == tag) {
                request.cancel();
            }
        }
    }

    @Override
    public void cancelAll() {
        for (Request request : requests) {
            request.cancel();
        }
    }

    /**
     * 执行请求
     *
     * @param request
     * @return
     * @throws IOException
     */
    private CloseResponse excuteRequest(Request request) throws IOException {
        URL url = null;
        if (request.getMethod() == RequestMethod.METHOD_GET && null != request.getParas().genParasStrGet()) {
            url = new URL(request.getUrl() + "?" + request.getParas().genParasStrGet());
        } else {
            url = new URL(request.getUrl());
        }
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout((int) connectionTimeOut);
        connection.setReadTimeout((int) readTimeOut);
        connection.setRequestMethod(request.getMethod().getMethod());
        connection.setDoInput(true);

        //添加自定义的header
        Header requestHeader = request.getHeader();
        if (null != requestHeader &&
                null != requestHeader.getHeaders() &&
                !requestHeader.getHeaders().isEmpty()) {
            Map<String, String> map = requestHeader.getHeaders();
            Set<Map.Entry<String, String>> entries = map.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        String parasStr = request.getParas().genParasStr();
        SimpleLog.d("参数："+parasStr);
        if (null != parasStr && request.getMethod() != RequestMethod.METHOD_GET) {
            connection.setDoOutput(true);
            //不是get请求添加参数
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Charset", "utf-8");
            connection.setRequestProperty("Connection", "Keep-Alive");
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(parasStr.getBytes());
            outputStream.flush();
            outputStream.close();
        } else if (null != request.getBody()) {
            connection.setDoOutput(true);
            Object o = request.getBody();
            if (o instanceof JSONObject) {
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Charset", "utf-8");
                connection.setRequestProperty("Connection", "Keep-Alive");
                OutputStream outputStream = connection.getOutputStream();
                connection.getOutputStream().write(((JSONObject) o).toString().getBytes());
                outputStream.flush();
                outputStream.close();
            } else if (o instanceof File) {
                //上传文件分割线
                String BOUNDARY = "#--------------------------------#";
                String NEW_LINE = "\r\n";
                String PREFIX = "--";
                File file = (File) o;
                connection.setRequestProperty("Charset", "utf-8");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("multipart/form-data;boundary=", BOUNDARY);

                OutputStream outputStream = connection.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.write((PREFIX + BOUNDARY + NEW_LINE).getBytes());
                dataOutputStream.writeBytes("Content-Disposition: form-data; " + "name=\""
                        + "uploadFile" + "\"" + "; filename=\"" + "file-" + System.currentTimeMillis()
                        + "\"" + NEW_LINE);

                InputStream inputStream = new FileInputStream(file);
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                int bytes = 0;
                byte[] buffer = new byte[1024];
                while ((bytes = dataInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytes);
                }
                dataInputStream.close();

                dataOutputStream.write((NEW_LINE + PREFIX + BOUNDARY + PREFIX + NEW_LINE).getBytes());

                dataOutputStream.flush();
                dataOutputStream.close();
            }
        }

        connection.connect();
        int code = connection.getResponseCode();
        if (code != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP RESPONSE CODE ERROR :" + code);
        }
        String message = connection.getResponseMessage();
        InputStream inputStream = connection.getInputStream();


        CloseResponse response = new CloseResponse();
        Header header = new Header();

        Map<String, List<String>> connHeaders = connection.getHeaderFields();
        if (!connHeaders.isEmpty()) {
            Set<String> keys = connHeaders.keySet();
            for (String key : keys) {
                header.getHeaders().put(key, connection.getHeaderField(key));
            }
        }
        response.setHeader(header);

        Body body = new Body();
        body.setInputStreamData(inputStream);
        body.setLength(connection.getContentLength());
        response.setBody(body);

        response.setRequest(request);
        response.setCode(code);
        response.setMessage(message);
        response.setProtocal("");
        response.setConnection(connection);
        return response;
    }


}
