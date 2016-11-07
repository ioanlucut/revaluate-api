package com.revaluate.oauth.service;

interface SlackUtils {

    //-----------------------------------------------------------------
    // SLACK endpoints and methods
    //-----------------------------------------------------------------
    String SLACK_URI = "https://slack.com/";
    String SLACK_ACCESS_PATH = "api/oauth.access";

    //-----------------------------------------------------------------
    // QUERY params
    //-----------------------------------------------------------------
    String CLIENT_ID = "client_id";
    String CLIENT_SECRET = "client_secret";
    String CODE = "code";
    String REDIRECT_URI = "redirect_uri";
}