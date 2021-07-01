package com.chess06.chess06.dao;
import com.chess06.chess06.entity.Chessboard;

import java.util.List;

public interface ChessboardDao {

    /**
     * create a new chessboard.database will give an id.
     * @return 1 success 0 fail
     */
    int insertChessboard(Chessboard chessboard);

    /**
     * after fighting,update chessboard's time,the winner id,the loser id.
     * @param chessboard
     * @return 1 success 0 fail
     */
    int updateChessboardDetails(Chessboard chessboard);
    /*更新细节*/
    int updateChessboardWinner(Chessboard chessboard);
    /*更新胜者*/

    /**
     * select chessboard by id,then review the fighting
     * @param id
     * @return chessboard information include details,winnerId,loserId
     */
    Chessboard queryChessboard(int id);

    List<Integer> queryChessboardIdByUserId(int id);
}
