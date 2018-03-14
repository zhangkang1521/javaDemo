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
public class GcbTest {

    @Test
    public void testQueryBalance() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://10.113.1.58:9528/CGBClient/BankAction");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("cgb_data", "<?xml version=\"1.0\" encoding = \"GBK\"?><BEDC><Message><commHead><tranCode>0001</tranCode><cifMaster>1000126240</cifMaster><entSeqNo>201605280001131005</entSeqNo><tranDate>20160528</tranDate><tranTime>131005</tranTime><retCode>000</retCode><entUserId>100001</entUserId><password><![CDATA[1q2w3e4r]]></password></commHead><Body><account>9550880043236602603</account></Body></Message></BEDC>"));
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
