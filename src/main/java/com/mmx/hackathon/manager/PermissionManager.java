package com.mmx.hackathon.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mmx.hackathon.dto.Permission;
import com.mmx.hackathon.util.Constants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Manindar
 */
public class PermissionManager {

    public String add(Permission p) throws Exception {
        String json = new Gson().toJson(p, new TypeToken<Permission>() {
        }.getType());
        return DBManager.getDB().add(Constants.PERMISSION_TABLE, json);
    }

    public Permission fetch(String loginid, String fileid) throws Exception {
        if (loginid == null || loginid.isEmpty() || fileid == null || fileid.isEmpty()) {
            return null;
        }
        Map<String, String> conditionMap = new HashMap<>();
        conditionMap.put("recieverid", loginid);
        conditionMap.put("fileid", fileid);
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

}
