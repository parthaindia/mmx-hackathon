/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmx.hackathon.manager;


import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.mmx.hackathon.util.Constants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Partha
 */
public class PushNotificationManager {

    public List<String> postPushNotification(Map<String, List<String>> data) throws IOException {
        if (data == null || data.isEmpty()) {
            return null;
        }

        List preference_valueList = data.get("preference_valueList");
        List messageList = data.get("messageList");
        List<String> gcmIdList = data.get("gcmIdList");

        String preference_value = "";
        String notificationmessage = "";

        if (preference_valueList != null) {
            preference_value = (String) preference_valueList.get(0);
        }
        if (messageList != null) {
            notificationmessage = (String) messageList.get(0);
        }
        List<String> ar = new ArrayList();
        Sender sender = new Sender(Constants.GOOGLE_KEY);
        Message message = new Message.Builder()
                .timeToLive(30)
                .delayWhileIdle(true)
                .addData("gcm", notificationmessage)
                .addData("preference_value", preference_value)
                .build();

        MulticastResult result = sender.send(message, gcmIdList, 1);
        ar.add(result.toString());
        System.out.println("regId: " + result.toString());

        return ar;
    }

    

}

