/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmx.hackathon.manager;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mmx.hackathon.dto.User;
import com.mmx.hackathon.util.Constants;
import java.lang.reflect.Type;
import java.util.List;

/**
 *
 * @author Partha
 */
public class RegistrationManager {

    public boolean registration(String gcmId, String countryCode, String imei) throws Exception {
        if (gcmId == null
                || gcmId.equals("")
                || imei == null
                || imei.equals("")) {
            return false;
        }
        Type type = new TypeToken<User>() {
        }.getType();
        Map<String, String> idMap = null;
        Map<String, String> param = new HashMap<String, String>();
        param.put("gcmId", gcmId);
        param.put("countryCode", countryCode);
        param.put("imei", imei);
        String result = DBManager.getDB().getByCondition(Constants.USER_TABLE, param);
        if (result != null) {
            return false;//exact duplicate present
        }
        param.clear();
        param.put("imei", imei);
        String result2 =  DBManager.getDB().getByCondition(Constants.USER_TABLE, param);
        if (result2 != null) {
            // update of already existing registration
            Type type1 = new TypeToken<List<User>>() {
            }.getType();
            List<User> usrList = new Gson().fromJson(result2, type1);
            User usr = usrList.get(0);
            idMap = (Map<String, String>) usr.getId();
            String id = idMap.get("$oid");
            String oldGcmId = usr.getGcmId();

            usr.setCountryCode(countryCode);
            usr.setGcmId(gcmId);
            usr.setUpdatedate(System.currentTimeMillis() + "");

            return DBManager.getDB().modify(Constants.USER_TABLE, id, new Gson().toJson(usr, type));
        } else {
            //new registration

            User newUser = new User();
            newUser.setGcmId(gcmId);
            newUser.setCountryCode(countryCode);
            newUser.setImei(imei);
            newUser.setCreatedate(System.currentTimeMillis() + "");
            newUser.setUpdatedate(System.currentTimeMillis() + "");
            newUser.setStatus(Constants.ACTIVE);


            String id = DBManager.getDB().addDefault(Constants.USER_TABLE, (new Gson().toJson(newUser, type)));
            if (id == null) {
                return false;
            } else {
                return true;
            }
        }
    }
}
