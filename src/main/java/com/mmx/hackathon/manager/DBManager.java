/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmx.hackathon.manager;

import com.mmx.hackathon.util.Constants;
import partha.mongodb.manager.DBpublicConnector;
import partha.mongodb.manager.MongoInterface;

/**
 *
 * @author Partha
 */
public class DBManager {

    public static MongoInterface getDB() throws Exception {
        String schema = Constants.DBSCHEMA;
        String dbUrl = Constants.DBURL;
        Integer dbPort = Integer.parseInt(Constants.DBPORT);
        MongoInterface db = new DBpublicConnector().getConnection(schema,dbUrl, dbPort);
        return db;
    }
}
