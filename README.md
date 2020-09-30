# Simple

[![](https://jitpack.io/v/SnowJun/Simple.svg)](https://jitpack.io/#SnowJun/Simple)

#### 网络库介绍
##### 库名及功能：
 	Simple的子库Net库，支持httpurlconnection和okhttp两种代理实现
 	功能主要包括，get请求，post表单，postJson，
 	post上传文件，post上传多文件，post参数文件混传等。
 
##### 库地址
######  Github库：[Simple](https://github.com/SnowJun/Simple)
###### Gitee库：[Simple](https://gitee.com/SnowJun/Simple)
###### Jitpack主页：[Simple](https://jitpack.io/#SnowJun/Simple)
##### 使用
```java
maven { url 'https://jitpack.io' }

implementation 'com.github.SnowJun.Simple:Net:1.0.3'
```

##### 正式开始撸功能
1. Builder配置：
```java
SimpleNetBuilder builder = new SimpleNetBuilder();
		//设置网络代理	默认为httpurlconnection 目前支持okhttp和httpurlconnection两种
        builder.setNetAgency(NetAgencyEnum.AGENCY_HTTPURLCONNECTION)
        //添加公共header请求
                .addHeader("header", "xxxxx")
                //连接超时时间 单位毫秒
                .connectTimeOut(10000)
                //读超时时间
                .readTimeOut(10000)
                //写超时时间
                .writeTimeOut(10000)
                //一键https
                .https()
                //请求重试次数
                .retryCount(3);
                //使用builder初始化
        SimpleNet.getInstance().init(builder);
```
2. Get请求
```java
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
```
3. Post表单参数,addHeader添加请求头
```java
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
```
4. Post Json请求
```java
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
```
5. Post上传文件
```java
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
```
6. Post上传多文件
```java
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
```
7. Post参数文件混传
```java
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
```
8. Bitmap返回
```java
 SimpleNet.get("").excute(new BitmapCallback() {
            @Override
            public void onSuccess(Bitmap result) {
                
            }

            @Override
            public void onFail(String reason) {

            }

            @Override
            public void onException(Exception e) {

            }
        });
```
9. File返回
```java
 SimpleNet.get("").excute(new FileCallBack("","") {
            @Override
            public void onProgress(int progress, long current, long size) {
                
            }

            @Override
            public void onSuccess(File result) {

            }

            @Override
            public void onFail(String reason) {

            }

            @Override
            public void onException(Exception e) {

            }
        });
```

