package org.zk;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequestBuilder;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class EsApiTest {

	private Client client;

	public static final String index = "zk"; // 相当于db

	public static final String type = "article";  // 相当于表

	@Before
	public void createClient() throws Exception {
		client = TransportClient.builder().build()
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
				.field("id", 3)
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
		IndexResponse indexResponse2 = client.prepareIndex(index, type, "7")
				.setSource(article).get();
		System.out.println(indexResponse2.isCreated());
	}

	@Test
	public void update() throws Exception {
		XContentBuilder updateArticle = jsonBuilder().startObject().field("title", "Hello4").endObject();
		// 方式1
//		UpdateRequest updateRequest = new UpdateRequest(index, type, "1");
//		updateRequest.doc(updateArticle);
//		client.update(updateRequest).get();
		// 方式2
		// UpdateRequest toString不可读
		UpdateResponse updateResponse = client.prepareUpdate(index, type, "7")
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
		DeleteResponse deleteResponse = client.prepareDelete(index, type, "1").get();
		System.out.println(deleteResponse.isFound());
	}

	@Test
	public void search() {
		QueryBuilder qb = QueryBuilders.termQuery("id", 10);
		SearchResponse response = client.prepareSearch(index).setTypes(type)
				.setQuery(qb)
				.get();
		System.out.println(response);
	}

	@Test
	public void bulkSave() throws Exception {
		XContentBuilder article1 = jsonBuilder().startObject()
				.field("id", 10)
				.field("title", "hello10")
				.field("abstract", "hello world")
				.field("content", "hello World")
				.field("postTime", new Date())
				.field("clickCount", 1)
				.endObject();
		XContentBuilder article2 = jsonBuilder().startObject()
				.field("id", 11)
				.field("title", "hello11")
				.field("abstract", "hello world")
				.field("content", "hello World")
				.field("postTime", new Date())
				.field("clickCount", 1)
				.endObject();
		client.prepareBulk()
				.add(client.prepareIndex(index, type, "10").setSource(article1))
				.add(client.prepareIndex(index, type, "11").setSource(article2))
				.get();
	}

	@Test
	public void scan() {
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.size(1);
		SearchRequest request = Requests.searchRequest("zk");
		request.scroll("2m");
		request.source(sourceBuilder);
		SearchResponse response = client.search(request).actionGet();
		SearchHits hits = response.getHits();
		for(int i = 0; i < hits.getHits().length; i++) {
			System.out.println(hits.getHits()[i].getSourceAsString());
		}
		String scrollId = response.getScrollId();
		while(true){
			SearchScrollRequestBuilder searchScrollRequestBuilder = client
					.prepareSearchScroll(scrollId);
			searchScrollRequestBuilder.setScroll("2m");
			SearchResponse response1 = searchScrollRequestBuilder.get();

			SearchHits hits2 = response1.getHits();
			if(hits2.getHits().length == 0){
				break;
			}
			for(int i = 0; i < hits2.getHits().length; i++) {
				System.out.println(hits2.getHits()[i].getSourceAsString());
			}
			scrollId = response1.getScrollId();
		}
	}


}
