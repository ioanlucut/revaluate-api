package com.revaluate.oauth.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.revaluate.oauth.exception.AppIntegrationException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.util.Map;

public interface SlackUtils {

    //-----------------------------------------------------------------
    // SLACK endpoints and methods
    //-----------------------------------------------------------------
    String SLACK_URI = "https://slack.com/";
    String SLACK_ACCESS_PATH = "api/oauth.access";
    String SLACK_AUTH_TEST_PATH = "api/auth.test";

    //-----------------------------------------------------------------
    // QUERY params
    //-----------------------------------------------------------------
    String CLIENT_ID = "client_id";
    String CLIENT_SECRET = "client_secret";
    String CODE = "code";
    String REDIRECT_URI = "redirect_uri";
    String TOKEN = "token";



}