package org.zk;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangkang on 2018/3/14.
 */
public class TranFlowTest {

    // 补偿历史流水
    @Test
    public void history() throws Exception {
        LocalDateTime now = LocalDateTime.of(2020, 5, 23, 0, 0, 0);
        do {
            String str = now.format(DateTimeFormatter.ofPattern("20200101-20200101"));
            System.out.println(str);
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost("http://super.lvmama.com/sas-service/v1/tran-flow/processor/job/bank-save-history");
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                nvps.add(new BasicNameValuePair("parameter", str));
                httpPost.setEntity(new UrlEncodedFormEntity(nvps));
                CloseableHttpResponse response1 = httpclient.execute(httpPost);
                HttpEntity entity1 = response1.getEntity();
                System.out.println(response1.getStatusLine() + EntityUtils.toString(entity1));
                now = now.minusDays(1);
            }
        } while (now.isAfter(LocalDateTime.of(2020, 1, 1, 0, 0, 0)));

    }
}
