package com.chess06.chess06.dao;
import com.chess06.chess06.entity.CollectionOfChess;

import java.util.List;

public interface CollectionDao {

    /**
     * everyone can choose a chessboard id to collections.
     * @param  collectionOfChess
     * @return 1 success 0 fail
     */
    int createCollect(CollectionOfChess collectionOfChess);

    /**
     * search user's collection by Id
     * @param userId
     * @return collection include chessboardId
     */
    List<CollectionOfChess> queryUserCollection(int userId);
}
