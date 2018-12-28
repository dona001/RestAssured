package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.Util.TestUtil;
import com.qa.base.TestBase;
import com.qa.client.RestClient;

public class GetAPITest extends TestBase{
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
	
	@Test(priority=1)
	public void getTest() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		closebleHttpResponse = restClient.get(url);
		
		//Statuscode
		int statusCode =  closebleHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code--->"+statusCode);
		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200 , "Status code is not 200");
		
		//pure string
		String responseString = EntityUtils.toString(closebleHttpResponse.getEntity(), "UTF-8");  
		
		//json convert to String 
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Responce Json---->"+responseJson);
		
		//perpage json output 
		String perpage = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("Per Page ----->"+perpage);
		
		Assert.assertEquals(Integer.parseInt(perpage), 3);  // i used interger for change the string and interger value 
		
		//Total Output
		String total = TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("Total ----->"+total);
		
		//data Array call 
		String Lastname = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String ID = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
		String Avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String Firstname = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");
		
		System.out.println("Lastname---->"+Lastname);
		System.out.println("ID---->"+ID);
		System.out.println("Avatar---->"+Avatar);
		System.out.println("Firstname---->"+Firstname);
		
		
		//Header Response 
		Header[] headersArray = closebleHttpResponse.getAllHeaders();
		
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for(Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		
		System.out.println("Header Array---->"+allHeaders);

	}
	
	//with Header
	@Test
	public void getTestwithHeader() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		
		//Passing Header Key And value 
		HashMap<String, String> headerMap = new HashMap<String,String>();
		headerMap.put("Content-Type", "application/json");
		//headerMap.put("Content-Type", "application/json");
		//headerMap.put("Content-Type", "application/json");
		
		//with url map will pass
		closebleHttpResponse = restClient.get(url,headerMap);
		
		//Statuscode
		int statusCode =  closebleHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code--->"+statusCode);
		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200 , "Status code is not 200");
		
		//pure string
		String responseString = EntityUtils.toString(closebleHttpResponse.getEntity(), "UTF-8");  
		
		//json convert to String 
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Responce Json-->"+responseJson);
		
		//perpage json output 
		String perpage = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("Per Page ----->"+perpage);
		
		Assert.assertEquals(Integer.parseInt(perpage), 3);  // i used interger for change the string and interger value 
		
		//Total Output
		String total = TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("Total ----->"+total);
		
		//data Array call 
		String Lastname = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String ID = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
		String Avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String Firstname = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");
		
		System.out.println("Lastname---->"+Lastname);
		System.out.println("ID---->"+ID);
		System.out.println("Avatar---->"+Avatar);
		System.out.println("Firstname---->"+Firstname);
		
		
		//Header Response 
		Header[] headersArray = closebleHttpResponse.getAllHeaders();
		
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for(Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		
		System.out.println("Header Array---"+allHeaders);

	}

}
