package com.chess06.chess06.dao;

import com.chess06.chess06.entity.Chessboard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ChessboardDaoTest {

    private Chessboard chessboard=new Chessboard();
    @Autowired
    private ChessboardDao chessboardDao;
    @Test
    public void insertChessboard() {
        chessboardDao.insertChessboard(chessboard);
        System.out.println(chessboard.getId());
    }

    @Test
    public void updateChessboardDetails() {
        chessboard.setDetails("111111");
        chessboard.setId(1);
        chessboardDao.updateChessboardDetails(chessboard);
        System.out.println(chessboardDao.queryChessboard(1).getDetails());
    }

    @Test
    public void updateChessboardWinner() {
        chessboard.setWinnerId(1);
        chessboard.setLoserId(2);
        chessboard.setId(1);
        chessboardDao.updateChessboardWinner(chessboard);
        System.out.println(chessboardDao.queryChessboard(chessboard.getId()).getWinnerId());
        System.out.println(chessboardDao.queryChessboard(chessboard.getId()).getLoserId());
    }

    @Test
    public void queryChessboard() {

        System.out.println(chessboardDao.queryChessboard(1).getDetails());
    }
}