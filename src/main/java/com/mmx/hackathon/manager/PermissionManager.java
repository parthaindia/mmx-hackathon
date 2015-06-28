package com.mmx.hackathon.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mmx.hackathon.dto.FileHolder;
import com.mmx.hackathon.dto.Permission;
import com.mmx.hackathon.util.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Manindar
 */
public class PermissionManager {

    public String add(Permission p) throws Exception {
        if (p == null || p.toString().isEmpty()) {
            return null;
        }
        String json = new Gson().toJson(p, new TypeToken<Permission>() {
        }.getType());
        new NotificationManager().addNotification(json);
        return DBManager.getDB().add(Constants.PERMISSION_TABLE, json);
    }

    public Permission fetch(String loginid, String fileid) throws Exception {
        if (loginid == null || loginid.isEmpty() || fileid == null || fileid.isEmpty()) {
            return null;
        }
        Map<String, String> conditionMap = new HashMap<>();
        conditionMap.put("recieverid", loginid);
        conditionMap.put("fileid", fileid);
        conditionMap.put("status", "active");
        String json = DBManager.getDB().getByCondition(Constants.PERMISSION_TABLE, conditionMap);
        List<Permission> permissions = new Gson().fromJson(json, new TypeToken<List<Permission>>() {
        }.getType());
        if (permissions == null || permissions.isEmpty()) {
            return null;
        }

        Permission permission = permissions.get(0);
        long time = Long.parseLong(permission.getTime()) * 60 * 1000;
        long createdtime = Long.parseLong(permission.getCreatedate());
        if ((createdtime + time) > System.currentTimeMillis()) {
            return permission;
        } else {
            permission.setStatus("false");
            boolean flag = update(permission);
            if (flag) {
                return permission;
            } else {
                return null;
            }
        }
    }

    public boolean update(Permission p) throws Exception {
        String json = new Gson().toJson(p, new TypeToken<Permission>() {
        }.getType());
        return DBManager.getDB().modify(Constants.PERMISSION_TABLE, json, ((Map<String, String>) p.getId()).get("$oid"));
    }

    public boolean update(String permissionid, String status) throws Exception {
        if (permissionid == null || permissionid.isEmpty() || status == null || status.isEmpty()) {
            return false;
        }
        String exist_permission = DBManager.getDB().getByKey(Constants.NOTIFICATION_TABLE, permissionid);
        if (exist_permission == null || exist_permission.isEmpty()) {
            return false;
        } else {
            List<Permission> notifications = new Gson().fromJson(exist_permission, new TypeToken<List<Permission>>() {
            }.getType());
            Permission n = notifications.get(0);
            n.setStatus(status);
            String json = new Gson().toJson(n, new TypeToken<Permission>() {
            }.getType());
            return DBManager.getDB().modify(Constants.NOTIFICATION_TABLE, json, permissionid);
        }
    }

    public String fetchAll(String loginid) throws Exception {
        if (loginid == null || loginid.isEmpty()) {
            return null;
        }
        Map<String, String> conditionMap = new HashMap<>();
        conditionMap.put("loginid", loginid);
        conditionMap.put("status", "active");
        String json = DBManager.getDB().getByCondition(Constants.PERMISSION_TABLE, conditionMap);
        List<Permission> pers = new Gson().fromJson(json, new TypeToken<List<Permission>>() {
        }.getType());
        List<Permission> resultpers = new ArrayList<>();
        if (pers != null && !pers.isEmpty()) {
            for (Permission p : pers) {
                if (p.getId() != null) {
                    long now = System.currentTimeMillis();
                    long time = 0 + Long.parseLong(p.getCreatedate()) + (Long.parseLong(p.getTime()) * 60 * 1000);
                    if (now > time) {
                        continue;
                    }
                    p.setId(((Map<String, String>) p.getId()).get("$oid"));
                }
                resultpers.add(p);
            }
        }
        return new Gson().toJson(resultpers, new TypeToken<List<Permission>>() {
        }.getType());
//        return DBManager.getDB().getByCondition(Constants.PERMISSION_TABLE, conditionMap);
    }

    public boolean revokePermission(String permissionId) throws Exception {
        if (permissionId == null || permissionId.isEmpty()) {
            return false;
        }
        String json = DBManager.getDB().getByKey(Constants.PERMISSION_TABLE, permissionId);
        if (json == null || json.isEmpty()) {
            return false;
        }
        List<Permission> permissions = new Gson().fromJson(json, new TypeToken<List<Permission>>() {
        }.getType());
        if (permissions != null && !permissions.isEmpty()) {
            Permission permission = permissions.get(0);
            permission.setStatus("inactive");
            permission.setUpdatedate(System.currentTimeMillis() + "");
            json = new Gson().toJson(permission, new TypeToken<Permission>() {
            }.getType());
            return DBManager.getDB().modify(Constants.PERMISSION_TABLE, json, permissionId);
        } else {
            return false;
        }
    }

    public boolean checkOwner(String loginid, String fileId) throws Exception {
        String json = DBManager.getDB().getByKey(Constants.FILE_TABLE, fileId);
        List<FileHolder> fhs = new Gson().fromJson(json, new TypeToken<List<FileHolder>>() {
        }.getType());
        if (fhs != null && !fhs.isEmpty()) {
            FileHolder fh = fhs.get(0);
            return fh.getOwnerID().equals(loginid);
        } else {
            return false;
        }
    }

}
