package org.zk.tictactoe.mongo;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

public class TicTacToeCollection {

    MongoCollection mongoCollection;

    public TicTacToeCollection() throws Exception{
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("tic");
        Jongo jongo = new Jongo(db);
        mongoCollection = jongo.getCollection("game");
    }

    public MongoCollection getMongoCollection() {
        return mongoCollection;
    }

    public boolean saveMove(TicTacToeBean ticTacToeBean) {
        try {
            getMongoCollection().save(ticTacToeBean);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void drop() {
        getMongoCollection().drop();
    }
}
