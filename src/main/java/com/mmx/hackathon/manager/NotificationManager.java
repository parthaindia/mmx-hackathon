package com.mmx.hackathon.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mmx.hackathon.dto.Notification;
import com.mmx.hackathon.util.Constants;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Manindar
 */
public class NotificationManager {

    public String add(Notification n) throws Exception {
        String json = new Gson().toJson(n, new TypeToken<Notification>() {
        }.getType());
        return DBManager.getDB().add(Constants.NOTIFICATION_TABLE, json);
    }

    public String fetch(String toid, String status) throws Exception {
        Map<String, String> conditionMap = new HashMap<>();
        conditionMap.put("toid", toid);
        conditionMap.put("status", status);
        return DBManager.getDB().getByCondition(Constants.NOTIFICATION_TABLE, conditionMap);
    }
}
