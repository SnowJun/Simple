package org.simple.net.request;

import org.json.JSONObject;
import org.simple.net.request.body.RequestBody;

import java.io.File;

/**
 * org.simple.net.request
 *
 * @author Simple
 * @date 2020/9/27
 * @desc
 */
public class BodyRequest<B extends RequestBody> extends Request<BodyRequest<B>> {

    /**
     * body体
     */
    protected B body;

    public B getBody() {
        return body;
    }

    public  BodyRequest<B> setBody(B body) {
        this.body = body;
        return this;
    }


    public BodyRequest<B> file(File file) {
        return  this;
    }

    public BodyRequest<B> addFile(String name, File file) {
        return  this;
    }

    public BodyRequest<B> json(JSONObject jsonObject) {
        return  this;
    }


}
