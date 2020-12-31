package org.zk;

import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class RestHighLevelClientTest {

	private RestHighLevelClient client;

	public static final String index = "zk"; // 相当于db

	public static final String type = "article";  // 相当于表

	@Before
	public void before() {
		RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200));
		client = new RestHighLevelClient(builder);
	}


	@Test
	public void testGet() throws Exception {
		GetRequest getRequest = new GetRequest("zk", "article", "200");
		GetResponse getResponse = client.get(getRequest);
		System.out.println(getResponse);
	}

	@Test
	public void testGetAsync() {
		GetRequest getRequest = new GetRequest("zk", "article", "100");
		ActionListener actionListener = null;
		client.getAsync(getRequest, null);
	}

	@Test
	public void testInsert() throws Exception {
		XContentBuilder article = jsonBuilder().startObject()
				.field("id", 200)
				.field("title", "hello")
				.field("abstract", "hello world")
				.field("content", "hello World")
				.field("postTime", new Date())
				.field("clickCount", 1)
				.endObject();
		IndexRequest indexRequest = new IndexRequest(index, type, "200");
		indexRequest.source(article);
		IndexResponse indexResponse = client.index(indexRequest);
		System.out.println(indexResponse);
	}

	@Test
	public void testUpdate() throws Exception {
		XContentBuilder updateArticle = jsonBuilder().startObject().field("title", "Hello4").endObject();
		UpdateRequest updateRequest = new UpdateRequest(index, type, "200");
		updateRequest.doc(updateArticle);
		UpdateResponse updateResponse = client.update(updateRequest);
	}

	@Test
	public void testDelete() throws Exception {
		DeleteRequest deleteRequest = new DeleteRequest(index, type, "200");
		client.delete(deleteRequest);
	}


}
