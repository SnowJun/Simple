package org.simple.net.proxy;

import org.simple.net.SimpleNet;
import org.simple.net.callback.NetCallBack;
import org.simple.net.constants.Constants;
import org.simple.net.exception.ExceptionCode;
import org.simple.net.exception.NetException;
import org.simple.net.request.BodyRequest;
import org.simple.net.request.MultiRequest;
import org.simple.net.request.Request;
import org.simple.net.request.RequestMethod;
import org.simple.net.request.body.BodyType;
import org.simple.net.request.body.FileBody;
import org.simple.net.request.body.JsonBody;
import org.simple.net.request.body.MultiBody;
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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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
                } else {
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
        if (request.getMethod().isHasBody()) {
            url = new URL(request.getUrl());
        } else {
            Map<String, String> paras = request.getParas();
            StringBuffer sb = new StringBuffer();
            Set<Map.Entry<String, String>> entrySet = paras.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                sb.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), "utf-8"))
                        .append("&");
            }
            String string = sb.toString();
            url = new URL(request.getUrl() + "?" + string.substring(0, string.length() - 2));
        }
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout((int) connectionTimeOut);
        connection.setReadTimeout((int) readTimeOut);
        connection.setRequestMethod(request.getMethod().getMethod());
        connection.setDoInput(true);
        if (RequestMethod.METHOD_GET != request.getMethod()) {
            connection.setDoOutput(true);
            connection.setUseCaches(false);
        }

        //添加自定义的header
        Map<String, String> commonHeaders = SimpleNet.getInstance().getCommonHeaders();
        if (null != commonHeaders &&
                !commonHeaders.isEmpty()) {
            Set<Map.Entry<String, String>> entries = commonHeaders.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }


        //添加自定义的header
        Map<String, String> headers = request.getHeader();
        if (null != headers &&
                !headers.isEmpty()) {
            Set<Map.Entry<String, String>> entries = headers.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        if (request.getMethod().isHasBody()) {
            addBodyParas(connection, request);
        }

        connection.connect();
        int code = connection.getResponseCode();
        if (code != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP RESPONSE CODE ERROR :" + code);
        }
        String message = connection.getResponseMessage();
        InputStream inputStream = connection.getInputStream();


        CloseResponse response = new CloseResponse();

        Map<String, List<String>> connHeaders = connection.getHeaderFields();
        if (!connHeaders.isEmpty()) {
            Set<String> keys = connHeaders.keySet();
            for (String key : keys) {
                response.addHeader(key,connection.getHeaderField(key));
            }
        }

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

    private void addBodyParas(HttpURLConnection connection, Request request) throws IOException {
        BodyRequest bodyRequest = (BodyRequest) request;
        BodyType type = bodyRequest.getBody().type();
        Map<String, String> paras = bodyRequest.getParas();
        switch (type) {
            case TYPE_FORM:
                String parasStr = genParasStr(paras);
                if (null != parasStr) {
                    //不是get请求添加参数
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setRequestProperty("Charset", "utf-8");
                    connection.setRequestProperty("Connection", "Keep-Alive");
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(parasStr.getBytes());
                    outputStream.flush();
                    outputStream.close();
                }
                break;
            case TYPE_JSON:
                JsonBody jsonBody = (JsonBody) bodyRequest.getBody();
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Charset", "utf-8");
                connection.setRequestProperty("Connection", "Keep-Alive");
                OutputStream outputStream = connection.getOutputStream();
                connection.getOutputStream().write((jsonBody.getJsonObject()).toString().getBytes());
                outputStream.flush();
                outputStream.close();
                break;
            case TYPE_FILE:
                genFileBody(connection, request);
                break;
            case TYPE_MULTI:
                genMultiBody(connection, request);
                break;
            default:
                break;
        }
    }

    /**
     * 写入多文件或者混参数
     *
     * @param connection
     * @param request
     * @throws IOException
     */
    private void genMultiBody(HttpURLConnection connection, Request request) throws IOException {
        MultiRequest bodyRequest = (MultiRequest) request;
        MultiBody multiBody = (MultiBody) bodyRequest.getBody();
        Map<String, File> fileMap = multiBody.getFileMap();
        Map<String, List<File>> fileListMap = multiBody.getFileListMap();
        Map<String, String> paras = request.getParas();
        if (!bodyRequest.hasFile()) {
            //没有文件混合参数
            throw new IOException("如果只是参数，请采用postForm传参");
        }
        //上传文件分割线
        String BOUNDARY = UUID.randomUUID() + "";
        String NEW_LINE = "\r\n";
        //固定
        String PREFIX = "--";
        connection.setRequestProperty("Charset", "utf-8");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Accept-Encoding", "gzip");
        connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);

        OutputStream outputStream = connection.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        if (null != paras) {
            for (String key : paras.keySet()) {
                if (null != paras.get(key)) {
                    dataOutputStream.write((PREFIX + BOUNDARY + NEW_LINE).getBytes());
                    //这块的name和服务器的入参名字对应起来 name ="file" 服务器的入参名字为file
                    String string = "Content-Disposition: form-data; " + "name=\""
                            + key +"\""+ NEW_LINE;
                    dataOutputStream.write(string.getBytes());
                    //两个换行 不然会把body算到header里面  出现大小限制异常
                    dataOutputStream.write((NEW_LINE + NEW_LINE).getBytes());
                    dataOutputStream.write(paras.get(key).getBytes());
                    dataOutputStream.write((NEW_LINE).getBytes());
                }
            }
        }

        Set<Map.Entry<String, File>> entrySet = fileMap.entrySet();
        if (null != fileMap && !fileMap.isEmpty()){
            for (Map.Entry<String, File> entry : entrySet) {
                String key = entry.getKey();
                File file = entry.getValue();
                dataOutputStream.write((PREFIX + BOUNDARY + NEW_LINE).getBytes());
                //这块的name和服务器的入参名字对应起来 name ="files" 服务器的入参名字为files
                String string = "Content-Disposition: form-data; " + "name=\""
                        + key + "\"" + "; filename=\"" + file.getName()
                        + "\"" + NEW_LINE;
                dataOutputStream.write(string.getBytes());
                dataOutputStream.write(("Content-Type: */*" + NEW_LINE).getBytes());
                dataOutputStream.write(("Content-Length: " + file.length()).getBytes());
                //两个换行 不然会把body算到header里面  出现大小限制异常
                dataOutputStream.write((NEW_LINE + NEW_LINE).getBytes());

                InputStream inputStream = new FileInputStream(file);
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                int bytes = 0;
                byte[] buffer = new byte[1024];
                while ((bytes = dataInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytes);
                }
                dataOutputStream.write((NEW_LINE).getBytes());
                dataInputStream.close();
            }
        }


        Set<Map.Entry<String, List<File>>> entrySet1 = fileListMap.entrySet();
        if (null != fileListMap && ! fileListMap.isEmpty()){
            for (Map.Entry<String, List<File>> entry : entrySet1) {
                String key = entry.getKey();
                List<File> files = entry.getValue();
                if (null != files && !files.isEmpty()){
                    for (File file : files) {
                        dataOutputStream.write((PREFIX + BOUNDARY + NEW_LINE).getBytes());
                        //这块的name和服务器的入参名字对应起来 name ="files" 服务器的入参名字为files
                        String string = "Content-Disposition: form-data; " + "name=\""
                                + key + "\"" + "; filename=\"" + file.getName()
                                + "\"" + NEW_LINE;
                        dataOutputStream.write(string.getBytes());
                        dataOutputStream.write(("Content-Type: */*" + NEW_LINE).getBytes());
                        dataOutputStream.write(("Content-Length: " + file.length()).getBytes());
                        //两个换行 不然会把body算到header里面  出现大小限制异常
                        dataOutputStream.write((NEW_LINE + NEW_LINE).getBytes());

                        InputStream inputStream = new FileInputStream(file);
                        DataInputStream dataInputStream = new DataInputStream(inputStream);
                        int bytes = 0;
                        byte[] buffer = new byte[1024];
                        while ((bytes = dataInputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytes);
                        }
                        dataOutputStream.write((NEW_LINE).getBytes());
                        dataInputStream.close();

                    }

                }

            }
        }

        //后缀
        dataOutputStream.write((PREFIX + BOUNDARY + PREFIX).getBytes());
        dataOutputStream.write((NEW_LINE).getBytes());
        dataOutputStream.flush();
        dataOutputStream.close();
    }

    /**
     * 写入文件参数
     *
     * @param connection
     * @param request
     * @throws IOException
     */
    private void genFileBody(HttpURLConnection connection, Request request) throws IOException {
        BodyRequest bodyRequest = (BodyRequest) request;
        FileBody fileBody = (FileBody) bodyRequest.getBody();
        //上传文件分割线
        String BOUNDARY = UUID.randomUUID() + "";
        String NEW_LINE = "\r\n";
        //固定
        String PREFIX = "--";
        File file = (File) fileBody.getFile();
        connection.setRequestProperty("Charset", "utf-8");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Accept-Encoding", "gzip");
        connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);

        OutputStream outputStream = connection.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.write((PREFIX + BOUNDARY + NEW_LINE).getBytes());
        //这块的name和服务器的入参名字对应起来 name ="file" 服务器的入参名字为file
        String string = "Content-Disposition: form-data; " + "name=\""
                + "file" + "\"" + "; filename=\"" + file.getName()
                + "\"" + NEW_LINE;
        dataOutputStream.write(string.getBytes());
        dataOutputStream.write(("Content-Type: */*" + NEW_LINE).getBytes());
        dataOutputStream.write(("Content-Length: " + file.length()).getBytes());
        //两个换行 不然会把body算到header里面  出现大小限制异常
        dataOutputStream.write((NEW_LINE + NEW_LINE).getBytes());

        InputStream inputStream = new FileInputStream(file);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int bytes = 0;
        byte[] buffer = new byte[1024];
        while ((bytes = dataInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytes);
        }
        dataOutputStream.write((NEW_LINE).getBytes());
        //后缀
        dataOutputStream.write((PREFIX + BOUNDARY + PREFIX).getBytes());
        dataOutputStream.write((NEW_LINE).getBytes());

        dataInputStream.close();
        dataOutputStream.flush();
        dataOutputStream.close();
    }


    /**
     * 生成表单参数
     *
     * @return
     */
    private String genParasStr(Map<String, String> paras) {
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
        return string.substring(0, string.length() - 1);
    }


}
