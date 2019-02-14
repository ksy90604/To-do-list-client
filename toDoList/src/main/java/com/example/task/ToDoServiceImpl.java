package com.example.task;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


@Service
public class ToDoServiceImpl implements ToDoService{
	
//  이것도 가능 - 참고용
//	@RequestMapping("/doGET")
//	@ResponseBody
//	public String doGET(@RequestParam("reqUrl") String reqUrl) throws IOException {
//		System.out.println("reqUrl-------" + reqUrl);
//		String data = "";
//		try {
//			URL url = new URL(reqUrl);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setRequestMethod("GET");
//			conn.setRequestProperty("Accept", "application/json");
//
//			if (conn.getResponseCode() != 200) {
//				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
//			}
//
//			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//			StringBuffer sb = new StringBuffer();
//			String jsonData ="";
//			while ((jsonData = br.readLine()) != null) {
//				sb.append(jsonData);
//			}
//			
//			data = sb.toString();
//
//			conn.disconnect();
//
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
//		return data;
//	}
	@RequestMapping("/doGET")
	@ResponseBody
	public String doGET(@RequestParam("reqUrl") String reqUrl) throws IOException {
		String data = "";
		try {
			
			// RestTemplate 에 MessageConverter 세팅
			List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
			converters.add(new FormHttpMessageConverter());
			converters.add(new StringHttpMessageConverter());
			
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.setMessageConverters(converters);
			
			// REST API 호출
			data = restTemplate.getForObject(reqUrl, String.class);
//			System.out.println(data);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return data;
	}
	@RequestMapping("/doPOST")
	@ResponseBody
	public String doPOST(@RequestParam("reqUrl") String reqUrl, @RequestParam(value="title", required=true)String title
			,  @RequestParam(value="parentId", required=false)String parentId) throws IOException {
		String data = "";
		try {
			
			// RestTemplate 에 MessageConverter 세팅
			List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
			converters.add(new FormHttpMessageConverter());
			converters.add(new StringHttpMessageConverter());
			
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.setMessageConverters(converters);
			
			// parameter 세팅
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("title", title);
			map.add("parentId", parentId);
			
			// REST API 호출
			data = restTemplate.postForObject(reqUrl, map, String.class);
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return data;
	}
	
	@RequestMapping("/doPUT")
	@ResponseBody
	public String doPUT(@RequestParam("reqUrl") String reqUrl, @RequestBody Task task) throws IOException {
		String data = "";
		try {
			URI uri = URI.create(reqUrl);
		    
			HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);

		    HttpEntity<Task> entity = new HttpEntity(task, headers);

		    RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);

		}catch (Exception e) {
			// TODO: handle exception
		}
		return data;
	}
}
