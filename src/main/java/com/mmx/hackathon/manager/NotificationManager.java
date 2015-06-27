package com.mmx.hackathon.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mmx.hackathon.dto.Notification;
import com.mmx.hackathon.dto.Permission;
import com.mmx.hackathon.util.Constants;
import java.util.HashMap;
import java.util.List;
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

    public boolean update(String notificationid, String status) throws Exception {
        if(notificationid == null || notificationid.isEmpty() || status == null || status.isEmpty()){
            return false;
        }
        String exist_notification = DBManager.getDB().getByKey(Constants.NOTIFICATION_TABLE, notificationid);
        if (exist_notification == null || exist_notification.isEmpty()) {
            return false;
        } else {
            List<Notification> notifications = new Gson().fromJson(exist_notification, new TypeToken<List<Notification>>() {
            }.getType());
            Notification n = notifications.get(0);
            n.setStatus(status);
            String json = new Gson().toJson(n, new TypeToken<Notification>() {
            }.getType());
            return DBManager.getDB().modify(Constants.NOTIFICATION_TABLE, json, notificationid);
        }
    }
}
