package org.simple.net.request.body;

/**
 * org.simple.net.request
 *
 * @author Simple
 * @date 2020/9/27
 * @desc
 */
public enum  BodyType {

    /**
     * 表单信息
     */
    TYPE_FORM("application/x-www-form-urlencoded"),
    /**
     * Json信息
     */
    TYPE_JSON("application/json"),
    /**
     * File信息
     */
    TYPE_FILE("multipart/form-data;boundary="),
    /**
     * 多类型
     * 多文件 或者参数文件混传
     */
    TYPE_MULTI("multipart/form-data;boundary=")
    ;
    /**
     * body的类型
     */
    private String bodyContentType;

    BodyType(String bodyContentType) {
        this.bodyContentType = bodyContentType;
    }

    /**
     * 获取body提体的内容
     * @return
     */
    public String getBodyContentType() {
        return bodyContentType;
    }

}
