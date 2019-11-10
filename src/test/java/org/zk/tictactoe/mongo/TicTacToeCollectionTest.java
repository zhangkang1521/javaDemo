package org.zk.tictactoe.mongo;

import org.jongo.MongoCollection;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TicTacToeCollectionTest {

    TicTacToeCollection collection;
    TicTacToeBean bean;
    MongoCollection mongoCollection;

    @Before
    public void before() throws Exception{
        collection = spy(new TicTacToeCollection());
        bean = new TicTacToeBean(1, 2, 3, 'Y');
        mongoCollection = mock(MongoCollection.class);
    }

    @Test
    public void whenInstantiatedThenHasDbNameTicTacToe() {
        assertEquals("tic", collection.getMongoCollection().getDBCollection().getDB().getName());
    }

    @Test
    public void collectionName() {
        assertEquals("game", collection.getMongoCollection().getName());
    }

    @Test
    public void whenSaveMoveThenInvokeMongoSave() {
        doReturn(mongoCollection).when(collection).getMongoCollection();
        collection.saveMove(bean);
        verify(mongoCollection, times(1)).save(bean);
    }

    @Test
    public void whenSaveMoveThenReturnTrue() {
        doReturn(mongoCollection).when(collection).getMongoCollection();
        assertTrue(collection.saveMove(bean));
    }

    @Test
    public void whenSaveMoveThenReturnFalse() {
        doReturn(mongoCollection).when(collection).getMongoCollection();
        doThrow(new RuntimeException()).when(mongoCollection).save(any(TicTacToeBean.class));
        assertFalse(collection.saveMove(bean));
    }

    @Test
    public void whenDropThenInvoiceMongoDrop() {
        doReturn(mongoCollection).when(collection).getMongoCollection();
        collection.drop();
        verify(mongoCollection).drop();
    }
}
