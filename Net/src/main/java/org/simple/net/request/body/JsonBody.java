package org.simple.net.request.body;

import org.json.JSONObject;

/**
 * org.simple.net.request.body
 *
 * @author Simple
 * @date 2020/9/27
 * @desc
 */
public class JsonBody extends RequestBody{

    private JSONObject jsonObject;

    @Override
    public BodyType type() {
        return BodyType.TYPE_JSON;
    }


    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

}
