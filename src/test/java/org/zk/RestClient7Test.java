package org.zk;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RestClient7Test {

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
		Request request = new Request("GET", "zk/article/200");
		Response response = restClient.performRequest(request);
		System.out.println(EntityUtils.toString(response.getEntity()));
	}

	@Test
	public void testDelete() throws Exception {
		Request request = new Request("DELETE", "zk/article/200");
		Response response = restClient.performRequest(request);
		System.out.println(EntityUtils.toString(response.getEntity()));
	}

	@Test
	public void testSave() throws Exception {
		Request request = new Request("POST", "zk/article/200");
		request.setJsonEntity("{" +
				"\"title\": \"hello4\"" +
				"}");
		Response response = restClient.performRequest(request);
//		System.out.println(EntityUtils.toString(response.getEntity()));
	}

}
