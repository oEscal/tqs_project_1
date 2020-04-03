package com.tqs1.api.serializers;

import org.json.simple.JSONObject;


public class JSONObjectStrategy {

    private JSONObject jsonObject;

    public JSONObjectStrategy(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObjectStrategy get(String o) {
        return new JSONObjectStrategy((JSONObject) this.jsonObject.get(o));
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
