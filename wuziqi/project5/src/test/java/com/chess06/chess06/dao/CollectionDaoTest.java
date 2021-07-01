package com.chess06.chess06.dao;

import com.chess06.chess06.entity.CollectionOfChess;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectionDaoTest {

 @Autowired
 private CollectionDao collectionDao;

 private CollectionOfChess collection=new CollectionOfChess();

    @Test
    public void createCollect() {
        collection.setUserId(1);
        collection.setChessId(1);
        collectionDao.createCollect(collection);
        List<CollectionOfChess> collection1=collectionDao.queryUserCollection(1);
        System.out.println(collection1.get(0).getUserId());
        System.out.println(collection1.get(0).getChessId());
    }

    @Test
    public void queryUserCollection() {
    }
}