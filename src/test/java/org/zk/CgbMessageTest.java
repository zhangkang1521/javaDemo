package org.zk;

import com.thoughtworks.xstream.XStream;
import org.junit.Test;
import org.zk.cgb.BEDC;
import org.zk.cgb.CommHead;
import org.zk.cgb.Message;
import org.zk.cgb.QueryBalance;

/**
 * Created by zhangkang on 2018/3/14.
 */
public class CgbMessageTest {

    @Test
    public void testQueryBalance() {
        BEDC bedc = new BEDC();
        Message<QueryBalance> message = new Message<QueryBalance>();
        CommHead commHead = new CommHead();
        commHead.setCifMaster("asdf");
        commHead.setTranCode("111");
        message.setCommHead(commHead);
        QueryBalance queryBalance = new QueryBalance();
        queryBalance.setAccount("00001");
        message.setBody(queryBalance);
        bedc.setMessage(message);
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        xStream.aliasSystemAttribute(null, "class");
        System.out.println(xStream.toXML(bedc));
    }


}
