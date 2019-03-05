package com.qa.client;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RestClient {

    //1. Get method without Headers
    public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url); // Create the HTTP Connection with the URL
        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);

        return closeableHttpResponse;
    }


    //2. Get method with Headers
    public CloseableHttpResponse get(String url, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url); // Create the HTTP Connection with the URL

        for (Map.Entry<String, String> entry : headerMap.entrySet()){
            httpGet.addHeader(entry.getKey(), entry.getValue());
        }

        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet); // Hit the GET URL

        return closeableHttpResponse;
    }

//    3. Post method
    public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url); // Create the HTTP Connection with the URL
        httpPost.setEntity(new StringEntity(entityString)); //for payload

        //for headers:
        for(Map.Entry<String,String> entry : headerMap.entrySet()){
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }

        CloseableHttpResponse closebaleHttpResponse = httpClient.execute(httpPost);
        return closebaleHttpResponse;

    }
}
