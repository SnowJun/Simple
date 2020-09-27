package org.simple.net.request;

import org.json.JSONObject;
import org.simple.net.request.body.JsonBody;

/**
 * org.simple.net.request
 *
 * @author Simple
 * @date 2020/9/27
 * @desc
 */
public class JsonRequest extends BodyRequest<JsonBody> {

    public JsonRequest() {
        body = new JsonBody();
    }

    @Override
    public BodyRequest<JsonBody> json(JSONObject jsonObject) {
        body.setJsonObject(jsonObject);
        return this;
    }
}
