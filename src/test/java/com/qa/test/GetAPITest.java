package com.qa.test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class GetAPITest extends TestBase {
    TestBase testBase;
    String serviceURL;
    String apiURL;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;

    @BeforeMethod
    public void setUP(){
        testBase = new TestBase();
        serviceURL = prop.getProperty("serviceURL");
        apiURL = prop.getProperty("URL");

        url = apiURL+serviceURL;

    }

    @Test
    public void getAPITestWithoutHeaders() throws ClientProtocolException, IOException {
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);

        // a. Status Code
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status Code: "+statusCode);
        Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_200, "Status Code is not "+RESPONSE_STATUS_CODE_200);

        // b. Json String
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");   //Content-Type =UTF-8 (R=Without the junk characters
        System.out.println("Json string :");
        System.out.println(responseString);

        // this will convert the String Response to JSON object by removing all the unnecessary characters, and save as Key-Value pairs
        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("JSON Object: ");
        System.out.println(responseJson);


        // Check the Per Page value
        // perPageValue is a String variable. Therefore, the types of Assertion value should match
        // Either cast the Actual String to Integer or Define the Expected as String
        String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
        System.out.println("Value of Per Page : "+perPageValue);
        Assert.assertEquals(perPageValue, "3");


        // Check the Value of Total
        // perPageValue is a String variable. Therefore, the types of Assertion value should match
        // Either cast the Actual String to Integer or Define the Expected as String
        String  totalValue = TestUtil.getValueByJPath(responseJson, "/total");
        System.out.println("Value of Total: "+totalValue);
        Assert.assertEquals(Integer.parseInt(totalValue), 12);  // The attribute Total is changing based on the available data


        //Check the values from JSON array
       String id = TestUtil.getValueByJPath(responseJson,"/data[0]/id");
       String firstName = TestUtil.getValueByJPath(responseJson,"/data[0]/first_name");
       String lastName = TestUtil.getValueByJPath(responseJson,"/data[0]/last_name");
       String avatar = TestUtil.getValueByJPath(responseJson,"/data[0]/avatar");


        System.out.println("Data Array index 0 values: ");
        System.out.println("ID: "+id);
        System.out.println("First name: "+firstName);
        System.out.println("Last name: "+lastName);
        System.out.println("Avatar: "+avatar);

//        c. Get all the Headers into an array
        Header[] headerArray = closeableHttpResponse.getAllHeaders();

//        Assign the Header array to a HashMap - Easy to refer as it is in Key-value pairs in string format
        HashMap<String,String> allHeaders = new HashMap<String, String>();

//        Assigning the values to the HashMap
        for (Header header: headerArray){
            allHeaders.put(header.getName(), header.getValue());
        }

//        Print all the Headers
        System.out.println("Headers: "+allHeaders);
    }


    @Test
    public void getAPITestWithHeaders() throws ClientProtocolException, IOException {
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);

        HashMap<String,String> headerMap = new HashMap<String, String> ();
        // get the values from Postman
        // If there are multiple Header attributed, add all those to the HashMap
        headerMap.put("Content-Type", "application/json");
        closeableHttpResponse =restClient.get(url, headerMap);


        // a. Status Code
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status Code: "+statusCode);
        Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_200, "Status Code is not "+RESPONSE_STATUS_CODE_200);

        // b. Json String
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");   //Content-Type =UTF-8 (R=Without the junk characters
        System.out.println("Json string :");
        System.out.println(responseString);

        // this will convert the String Response to JSON object by removing all the unnecessary characters, and save as Key-Value pairs
        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("JSON Object: ");
        System.out.println(responseJson);


        // Check the Per Page value
        // perPageValue is a String variable. Therefore, the types of Assertion value should match
        // Either cast the Actual String to Integer or Define the Expected as String
        String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
        System.out.println("Value of Per Page : "+perPageValue);
        Assert.assertEquals(perPageValue, "3");


        // Check the Value of Total
        // perPageValue is a String variable. Therefore, the types of Assertion value should match
        // Either cast the Actual String to Integer or Define the Expected as String
        String  totalValue = TestUtil.getValueByJPath(responseJson, "/total");
        System.out.println("Value of Total: "+totalValue);
        Assert.assertEquals(Integer.parseInt(totalValue), 12);  // The attribute Total is changing based on the available data


        //Check the values from JSON array
        String id = TestUtil.getValueByJPath(responseJson,"/data[0]/id");
        String firstName = TestUtil.getValueByJPath(responseJson,"/data[0]/first_name");
        String lastName = TestUtil.getValueByJPath(responseJson,"/data[0]/last_name");
        String avatar = TestUtil.getValueByJPath(responseJson,"/data[0]/avatar");


        System.out.println("Data Array index 0 values: ");
        System.out.println("ID: "+id);
        System.out.println("First name: "+firstName);
        System.out.println("Last name: "+lastName);
        System.out.println("Avatar: "+avatar);

//        c. Get all the Headers into an array
        Header[] headerArray = closeableHttpResponse.getAllHeaders();

//        Assign the Header array to a HashMap - Easy to refer as it is in Key-value pairs in string format
        HashMap<String,String> allHeaders = new HashMap<String, String>();

//        Assigning the values to the HashMap
        for (Header header: headerArray){
            allHeaders.put(header.getName(), header.getValue());
        }

//        Print all the Headers
        System.out.println("Headers: "+allHeaders);

    }
}
