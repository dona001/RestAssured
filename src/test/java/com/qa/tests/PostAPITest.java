package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;

public class PostAPITest extends TestBase{
	TestBase testbase;
	String apiUrl;
	String serviceUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closebleHttpResponse;
	
	@BeforeMethod
	public void setUP() throws ClientProtocolException, IOException {
		testbase = new TestBase();
		apiUrl =  prop.getProperty("URL");
		serviceUrl = prop.getProperty("serviceURL");
		
		url = apiUrl + serviceUrl;
	
	}
	
	@Test
	public void postApiTest() throws JsonGenerationException, JsonMappingException, IOException{
		restClient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String,String>();
		headerMap.put("Content-Type", "application/json");
		
		//jackson API
		ObjectMapper mapper = new ObjectMapper();
		Users users = new Users("morpheus", "leader");
		
		//object to json file covert 
		mapper.writeValue(new File("C:\\Users\\Suve\\eclipse\\restapi\\src\\main\\java\\com\\qa\\data\\Users.json"), users);
		
		//object to json to String in consloe 
		String usersjsonString = mapper.writeValueAsString(users);
		System.out.println(usersjsonString);
		
		//call the API
		closebleHttpResponse = restClient.post(url, usersjsonString, headerMap);
		
		//Response validate
		//status code
		int statusCode = closebleHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, testbase.RESPONSE_STATUS_CODE_201);
		
		//json String 
		String responseString = EntityUtils.toString(closebleHttpResponse.getEntity(), "UTF-8"); 
		
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Responce API Output"+ responseJson);
		
		
		//here i create same json but need to match with up one
		Users objecjRes = mapper.readValue(responseString, Users.class);
		System.out.println(objecjRes);
		
		Assert.assertTrue(users.getName().equals(objecjRes.getName()));
		Assert.assertTrue(users.getJob().equals(objecjRes.getJob()));
		
		//System.out.println(objecjRes.getId());
		//System.out.println(objecjRes.getCreatedAt());
		
		
		
	}
	

}
