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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangkang on 2018/3/14.
 */
public class CcbTest {

    @Test
    public void testQueryBalance() throws Exception {
        for (int i = 0; i< 5; i++) {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://10.6.2.114:12345");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            String req = "<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\" ?><TX><REQUEST_SN>20180428001</REQUEST_SN><CUST_ID>SH31000009160774601</CUST_ID><USER_ID>WLPT02</USER_ID><PASSWORD>196611</PASSWORD><TX_CODE>6W0100</TX_CODE><LANGUAGE>CN</LANGUAGE><TX_INFO><ACC_NO>31050184400000000496</ACC_NO></TX_INFO></TX>";
            nvps.add(new BasicNameValuePair("requestXml", req));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response1 = httpclient.execute(httpPost);
            try {
                System.out.println(response1.getStatusLine());
                HttpEntity entity1 = response1.getEntity();
                System.out.println(EntityUtils.toString(entity1));
            } finally {
                response1.close();
            }
        }
    }
}
