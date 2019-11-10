package org.zk;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JongoTest {

    MongoClient mongoClient;
    MongoCollection collection;

    @Before
    public void before() throws Exception{
        mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("zk");
        Jongo jongo = new Jongo(db);
        collection = jongo.getCollection("user");
    }
    @After
    public void after() {
        mongoClient.close();
    }

    @Test
    public void testSave() {
        User user =   new User();
        user.setUsername("cc");
        WriteResult writeResult = collection.save(user);
        System.out.println(writeResult.getN());
    }



    @Test
    public void testRead() {
        User user1 =   collection.findOne("{'username': 'aa'}").as(User.class);
        assertEquals("aa", user1.getUsername());
    }

    @Test
    public void testReadB() {
        User user1 =   collection.findOne("{'username': 'bb'}").as(User.class);
        assertEquals("bb", user1.getUsername());
    }
}
