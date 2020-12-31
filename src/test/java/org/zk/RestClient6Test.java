package org.zk;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class RestClient6Test {

	RestClient restClient;

	@Before
	public void before() {
		restClient = RestClient.builder(new HttpHost("localhost", 9200, "http")).build();
	}

	@After
	public void after() throws Exception {
		restClient.close();
		System.in.read();
	}

	@Test
	public void testGet() throws Exception {
		Response response = restClient.performRequest("GET", "/zk/article/200");
		System.out.println(EntityUtils.toString(response.getEntity()));
	}

	@Test
	public void testDelete() throws Exception {
		Response response = restClient.performRequest("DELETE", "/zk/article/200");
		System.out.println(EntityUtils.toString(response.getEntity()));
	}

	@Test
	public void testSave() throws Exception {
		Map map = new HashMap<>();
		HttpEntity httpEntity = new NStringEntity("{" +
				"\"title\": \"hello4\"" +
				"}", ContentType.APPLICATION_JSON);
		Response response = restClient.performRequest("POST", "/zk/article/201", map, httpEntity);
//		System.out.println(EntityUtils.toString(response.getEntity()));
	}

}
