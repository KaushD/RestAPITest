package com.qa.test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import com.qa.data.Users;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class PostAPITest extends TestBase {

    TestBase testBase;
    String serviceURL;
    String apiURL;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;


    @BeforeMethod
    public void setUP() {
        testBase = new TestBase();
        serviceURL = prop.getProperty("serviceURL");
        apiURL = prop.getProperty("URL");

        url = apiURL + serviceURL;
    }


    @Test
    public void postAPITest() throws IOException {
//JsonGenerationException, JsonMappingException,
        restClient = new RestClient();
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");

        //jackson API: Converting the java object to JSON (POJO)
        // this is to create the JSON Request
        ObjectMapper mapper = new ObjectMapper();
        Users users = new Users("morpheus", "leader"); //expected users object

        //object to json file:writes the Request  payload to users.json file created inside data package
        mapper.writeValue(new File("C:/Users/Rishani/IdeaProjects/RestAPITest/src/main/java/com/qa/data/users.json"), users);

        //java object to json in String: this is the value which is passing to RestClient Post method - Entity String
        String usersJsonString = mapper.writeValueAsString(users);
        System.out.println(usersJsonString); //prints the response JSON string

        closeableHttpResponse = restClient.post(url, usersJsonString, headerMap); //call the API

        //validate response from API:
        //1. status code:
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, testBase.RESPONSE_STATUS_CODE_201);

        //2. JsonString: Validate the JSON response content
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8"); // UTF-8 is the compatible JSON string type

        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("The response from API is:"+ responseJson); // prints the response JSON object

        //json to java object: validate the JSON Response by reading the value - Un-marshalling
        // Comparing the name & job values in the Request & Response
        Users usersResObj = mapper.readValue(responseString, Users.class); //actual users object
        System.out.println(usersResObj);

        Assert.assertTrue(users.getName().equals(usersResObj.getName()));

        Assert.assertTrue(users.getJob().equals(usersResObj.getJob()));

        System.out.println(usersResObj.getId());
        System.out.println(usersResObj.getCreatedAt());
    }
    }
