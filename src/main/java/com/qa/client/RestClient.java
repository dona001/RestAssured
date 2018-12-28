package com.qa.client;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

public class RestClient {
	
	//1.GEt Method
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
	CloseableHttpClient httpClient = HttpClients.createDefault();  
	HttpGet httpget = new HttpGet(url);  //create new url with method
	CloseableHttpResponse closeableHttpResponse =  httpClient.execute(httpget);
	
	return closeableHttpResponse;
	
	}
	
	//Get with header
	public CloseableHttpResponse get(String url, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
	CloseableHttpClient httpClient = HttpClients.createDefault();  
	HttpGet httpget = new HttpGet(url);  //create new url with method
	
	//Defined  header value and key 
	for(Map.Entry<String,String> entry : headerMap.entrySet()) {
		httpget.addHeader(entry.getKey(),entry.getValue());
	}
	CloseableHttpResponse closeableHttpResponse =  httpClient.execute(httpget);
	return closeableHttpResponse;
	}
	
	//post Method 
	public CloseableHttpResponse post(String url, String entityString, HashMap<String, String>headerMap) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();  
		HttpPost httppost = new HttpPost(url);
		httppost.setEntity(new StringEntity(entityString));//create payload entity is payload
		
		//for header
		for(Map.Entry<String,String> entry : headerMap.entrySet()) {
			httppost.addHeader(entry.getKey(),entry.getValue());
		}
		
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httppost);
		return closeableHttpResponse;
		
		
	}

}
