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
        if (n == null) {
            return null;
        }
        String json = new Gson().toJson(n, new TypeToken<Notification>() {
        }.getType());
        return DBManager.getDB().add(Constants.NOTIFICATION_TABLE, json);
    }

    public String fetch(String toid, String status) throws Exception {
        if (toid == null || toid.isEmpty() || status == null || status.isEmpty()) {
            return null;
        }
        Map<String, String> conditionMap = new HashMap<>();
        conditionMap.put("toid", toid);
        conditionMap.put("status", status);
        return DBManager.getDB().getByCondition(Constants.NOTIFICATION_TABLE, conditionMap);
    }

    public boolean update(String notificationid, String status) throws Exception {
        if (notificationid == null || notificationid.isEmpty() || status == null || status.isEmpty()) {
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

    public void addNotification(String json) throws Exception {
        List<Permission> permissions = new Gson().fromJson(json, new TypeToken<List<Permission>>() {
        }.getType());
        Permission p = permissions.get(0);
        Notification n = new Notification();
        n.setCreatedate(System.currentTimeMillis() + "");
        n.setFileid(p.getFileid());
        n.setFromid(p.getLoginid());
        n.setMsg("");
        n.setStatus("fresh");
        n.setToid(p.getRecieverid());
        json = new Gson().toJson(n, new TypeToken<Notification>() {
        }.getType());
        String id = DBManager.getDB().add(Constants.NOTIFICATION_TABLE, json);
        System.out.println(id);
    }
}
