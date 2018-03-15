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
import java.util.Random;

/**
 * Created by zhangkang on 2018/3/14.
 */
public class GcbTest {

    @Test
    public void testQueryBalance() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://10.113.1.58:9528/CGBClient/BankAction");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
         String req = "<?xml version=\"1.0\" encoding = \"GBK\"?><BEDC><Message><commHead><tranCode>0001</tranCode><cifMaster>1000194691</cifMaster><entSeqNo>201605280001131005</entSeqNo><tranDate>20160528</tranDate><tranTime>131005</tranTime><retCode>000</retCode><entUserId>100001</entUserId><password><![CDATA[j4r6x7p3y7]]></password></commHead><Body><account>9550880200382500120</account></Body></Message></BEDC>";
//        String req = "<?xml version=\"1.0\" encoding = \"GBK\"?><BEDC><Message><commHead><tranCode>0012</tranCode><cifMaster>1000194691</cifMaster><entSeqNo>2018111506546485524</entSeqNo><tranDate>20131115</tranDate><tranTime>154643</tranTime><retCode>000</retCode><entUserId>100001</entUserId><password><![CDATA[j4r6x7p3y7]]></password></commHead><Body><traceNo></traceNo><outAccName>12</outAccName><outAcc>9550880200382500120</outAcc><outAccBank></outAccBank><inAccName>设计公司</inAccName><inAcc>12345678</inAcc><inAccBank>中国银行</inAccBank><inAccAdd>广州广东大厦支行</inAccAdd><amount>1000</amount><remark></remark><date></date><comment>捐款</comment><creNo></creNo><frBalance></frBalance><toBalance></toBalance><handleFee></handleFee></Body></Message></BEDC>";
//        String seq = "20180315" + new Random().nextInt(100000);
//        String req = "<?xml version=\\\"1.0\\\" encoding = \\\"GBK\\\"?><BEDC><Message><commHead><tranCode>0011</tranCode><cifMaster>1000194691</cifMaster><entSeqNo>"+seq+"</entSeqNo><tranDate>20170205</tranDate><tranTime>152306</tranTime><retCode>000</retCode><entUserId>100001</entUserId><password><![CDATA[j4r6x7p3y7]]></password></commHead><Body><traceNo></traceNo><outAccName>测试</outAccName><outAcc>9550880200382500210</outAcc><outAccBank></outAccBank><inAccName>KHG怜桑敛倍纬惯793</inAccName><inAcc>9550880200382500120</inAcc><inAccBank></inAccBank><inAccAdd></inAccAdd><amount>11.88</amount><remark>0011zy</remark><date></date><comment>0011fy</comment><creNo></creNo><frBalance></frBalance><toBalance></toBalance><handleFee></handleFee></Body></Message></BEDC>";
        nvps.add(new BasicNameValuePair("cgb_data", req));
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
