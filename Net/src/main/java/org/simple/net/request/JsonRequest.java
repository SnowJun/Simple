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
public class JsonRequest extends BodyRequest<JsonBody,JsonRequest> {

    public JsonRequest() {
        body = new JsonBody();
    }

    public JsonRequest json(JSONObject jsonObject) {
        body.setJsonObject(jsonObject);
        return this;
    }
}
