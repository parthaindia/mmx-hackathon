package com.mmx.hackathon.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mmx.hackathon.dto.Installation;
import com.mmx.hackathon.util.Constants;

/**
 *
 * @author Manindar
 */
public class InstallationManager {

    public String add(Installation installJson) throws Exception {
        String json = new Gson().toJson(installJson, new TypeToken<Installation>() {
        }.getType());
        return DBManager.getDB().add(Constants.INSTALLATION_TABLE, json);
    }
}
