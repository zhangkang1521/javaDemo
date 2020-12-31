package org.zk;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class TransportClient5Test {

	private Client client;

	public static final String index = "zk"; // 相当于db

	public static final String type = "article";  // 相当于表

	@Before
	public void createClient() throws Exception {
		// cat-client, es都是用netty导致错误 availableProcessors is already set to [8], rejecting [8]
		// -Des.set.netty.runtime.available.processors=false
//		System.setProperty("es.set.netty.runtime.available.processors", "false");
		client = new PreBuiltTransportClient(Settings.EMPTY)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
	}

	@After
	public void close() throws Exception {
		client.close();
		System.in.read();
	}



	@Test
	public void insert() throws Exception {
		XContentBuilder article = jsonBuilder().startObject()
				.field("id", 200)
				.field("title", "hello")
				.field("abstract", "hello world")
				.field("content", "hello World")
				.field("postTime", new Date())
				.field("clickCount", 1)
				.endObject();

		// 方式1
//		IndexRequest indexRequest = new IndexRequest(index, type, "3");
//		indexRequest.source(article);
//		IndexResponse indexResponse = client.index(indexRequest).get();
//		System.out.println(indexResponse.isCreated());

		// 方式2
		IndexResponse indexResponse2 = client.prepareIndex(index, type, "200")
				.setSource(article).get();
		System.out.println(indexResponse2);
	}

	@Test
	public void update() throws Exception {
		XContentBuilder updateArticle = jsonBuilder().startObject().field("title", "Hello5").endObject();
		// 方式1
//		UpdateRequest updateRequest = new UpdateRequest(index, type, "1");
//		updateRequest.doc(updateArticle);
//		client.update(updateRequest).get();
		// 方式2
		// UpdateRequest toString不可读
		UpdateResponse updateResponse = client.prepareUpdate(index, type, "200")
				.setDoc(updateArticle).get();
		System.out.println(updateResponse);
	}

	@Test
	public void findById() throws Exception {
		// GetRequest ok
		GetResponse getResponse = client.prepareGet(index, type, "1").get();
		System.out.println(getResponse.getSource());
		System.in.read();
	}

	@Test
	public void delete() {
		DeleteResponse deleteResponse = client.prepareDelete(index, type, "200").get();
		System.out.println(deleteResponse);
	}


}
