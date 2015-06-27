/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmx.hackathon.util;

/**
 *
 * @author Partha
 */
public interface Constants {

    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";
    public static final String ERROR = "error";
    public static final String FATAL_ERROR = "fatal error";
    public static final String WARNING = "warning";
    public static final String AUTHORIZED = "authorized";
    public static final String UNAUTHORIZED_ACCESS = "unauthorized access";
    public static final String INVALID_INPUT = "invalid input";
    public static final String INVALID_SESSION = "Invalid Session";
    public static final String INSUFFICIENT_PRIVILEGE = "Insufficient Privilege";
    public static final String USER_EMAIL_EXIST_MESSAGE = "Email is already registered";
    public static final String USER_NAME_EXIST_MESSAGE = "User name already exist";
    public static final String STATUS = "status";
    public static final String ACTIVE = "active";
    public static final String INACTIVE = "inactive";
    public static final String COMPLETE = "complete";
    public static final String USER_TABLE = "user";
    public static final String ORG_TABLE = "org";
    //HTTP status codes
    public static final String HTTP_STATUS_SUCCESS = "200";
    public static final String HTTP_STATUS_FAIL = "501";
    public static final String HTTP_STATUS_UNAUTHORIZED = "401";
    public static final String HTTP_STATUS_INVALID_SESSION = "403";
    public static final String HTTP_STATUS_EXCEPTION = "500";
    public static final String HTTP_USER_EXIST = "409";

    public static final String DBSCHEMA = "masx";
    public static final String DBPORT = "27017";
    public static final String DBURL = "192.168.60.61";
    public static String GOOGLE_KEY = "";
    public static String REGISTRATION_TABLE = "user";
    public static final String INSTALLATION_TABLE = "installation";
    public static final String PERMISSION_TABLE = "permission";
    public static final String NOTIFICATION_TABLE = "notification";
}
