package com.ustc.box.core.utils;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class  JsonParser {
    /** LOGGER */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonParser.class);

    /**
     * 根据json生成map
     * <p>
     * 
     * @author shun.lv, 2014-7-20
     * @param json
     *            原始json字符串
     * @return key to value
     */
    public static Map<String, Object> generateMapByJson(String json) {
        if (StringUtils.isEmpty(json)) {
            LOGGER.warn("The json {} is null.", json);

            return null;
        }

        Map<String, Object> jsonMap = new HashMap<String, Object>();

        JSONObject jsonObject = JSONObject.fromObject(json);
        if (jsonObject == null || CollectionUtils.isEmpty(jsonObject.keySet())) {
            LOGGER.warn("Cannot parse {} to map.", json);

            return null;
        }
        for (Object key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);

            jsonMap.put(key.toString(), value);
        }

        return jsonMap;
    }

    /**
     * 根据json生成map list
     * <p>
     * 
     * @author shun.lv, 2014-7-21
     * @param json
     *            原始json字符串
     * @return List<Map<String, Object>>对象
     */
    public static List<Map<String, Object>> generateListMapByJson(String json) {
        if (StringUtils.isEmpty(json)) {
            LOGGER.warn("The json {} is null.", json);

            return null;
        }

        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        JSONArray jsonArray = JSONArray.fromObject(json);
        if (jsonArray == null || jsonArray.isEmpty()) {
            LOGGER.warn("Cannot parse {} to array.", json);

            return null;
        }

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject == null || CollectionUtils.isEmpty(jsonObject.keySet())) {
                LOGGER.warn("Cannot parse {} to map.", jsonObject);

                continue;
            }

            Map<String, Object> map = new HashMap<String, Object>();
            for (Object key : jsonObject.keySet()) {
                Object value = jsonObject.get(key);

                map.put(key.toString(), value);
            }

            mapList.add(map);
        }

        return mapList;
    }
}
