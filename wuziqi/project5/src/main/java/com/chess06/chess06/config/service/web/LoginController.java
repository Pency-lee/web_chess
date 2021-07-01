package com.chess06.chess06.config.service.web;


import com.chess06.chess06.dao.AdministratorDao;
import com.chess06.chess06.dao.ChessboardDao;
import com.chess06.chess06.dao.CollectionDao;
import com.chess06.chess06.dao.UserDao;
import com.chess06.chess06.entity.Administrator;
import com.chess06.chess06.entity.Chessboard;
import com.chess06.chess06.entity.CollectionOfChess;
import com.chess06.chess06.entity.User;
import net.minidev.json.JSONArray;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;
import java.util.List;


@Controller
public class LoginController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AdministratorDao administratorDao;
    @Autowired
    private ChessboardDao chessboardDao;
    @Autowired
    private CollectionDao collectionDao;
    @PostMapping(value = "/login")
    public ModelAndView login(@RequestParam("userId") String userId, @RequestParam("password") String password)
    {

        User user=userDao.queryUserById(Integer.valueOf(userId));
        if (user==null)
        {
            return new ModelAndView("index");
        }
        else if (password.equals(user.getPassword()))
        {
            ModelAndView mav=new ModelAndView();
            int id=user.getId();
            String name=user.getName();
            int point=user.getPoint();
//            List<Integer> chessboardId=chessboardDao.queryChessboardIdByUserId(id);
//            for(Iterator<Integer> it = chessboardId.iterator(); it.hasNext();){
//                if(it.next()%2==0){
//                    it.remove();
//                }
//            }
            mav.addObject("userId",userId);
            mav.addObject("name",name);
            mav.addObject("password",password);
            mav.addObject("point",point);
//            mav.addObject("chessboardId",JSONArray.toJSONString(chessboardId));
            mav.setViewName("lobby");
            return mav;
        }
        else
        {
            return new ModelAndView("index");
        }
    }

    @PostMapping(value = "collection")
    @ResponseBody
    public void collection(@RequestParam("userId") int userId,@RequestParam("id") int id){
        CollectionOfChess collectionOfChess=new CollectionOfChess();
        collectionOfChess.setUserId(userId);
        collectionOfChess.setChessId(id);
        collectionDao.createCollect(collectionOfChess);
    }

    @RequestMapping(value = "/review")
    @ResponseBody
    public String review(@RequestParam("id") String id)
    {
        Chessboard chessboard1=new Chessboard();
        chessboard1=chessboardDao.queryChessboard(Integer.valueOf(id));
        String detailOfWhite=chessboard1.getDetails();
        Chessboard chessboard2=new Chessboard();
        chessboard2=chessboardDao.queryChessboard(Integer.valueOf(id)+1);
        String detailOfBlack=chessboard2.getDetails();
        String detail=new String();
        int count=1;
        while (detailOfBlack.length()!=0 || detailOfWhite.length()!=0){

            if(count==1) {
                detail += detailOfBlack.substring(0,4);
                detailOfBlack=detailOfBlack.substring(4);
                count++;
            }
            else if(count==2){
                detail +=detailOfWhite.substring(0,4);
                detailOfWhite=detailOfWhite.substring(4);
                count--;
            }
        }
//        while (detail.length()!=0)
//        {
//            head=detail.substring(0,2);
//            int x=Integer.valueOf(head);
//            detail=detail.substring(2);
//            head=detail.substring(0,2);
//            detail=detail.substring(2);
//            int y=Integer.valueOf(head);
//            arr[x][y]=bw;
//            if (bw==2) bw=1;
//            else bw=2;
//        }
        System.out.println(detail);
        return detail;
    }
    @PostMapping("/chessboardPos")
    @ResponseBody
    public void sendArr(String[] info,Integer winnerId,Integer loserId) {
        System.out.println(info[0]+","+info[1]+","+info[2]);
        Chessboard chessboard=new Chessboard();
        chessboardDao.insertChessboard(chessboard);
        String details;
        if (Integer.parseInt(info[0])>9)
        {
            details=info[0];
        }
        else
        {
            details="0"+info[0];
        }
        for(int i=1;i< info.length;i++){
            if (Integer.parseInt(info[i])>9)
            {
                details+=info[i];

            }
            else
            {
                details+="0"+info[i];
            }
        }
        chessboard.setDetails(details);
        chessboard.setWinnerId(winnerId);
        chessboard.setLoserId(loserId);
        userDao.addPoint(winnerId);
        chessboardDao.updateChessboardDetails(chessboard);
    }


    @PostMapping(value="/returnChessboard")
    @ResponseBody
    public List<Integer> list(@RequestParam("userId") int userId){
       List<Integer> chessboardId=chessboardDao.queryChessboardIdByUserId(userId);
        for(Iterator<Integer> it = chessboardId.iterator(); it.hasNext();){
            if(it.next()%2==0){
                it.remove();
            }
        }
        System.out.println(chessboardId);
//        return JSONArray.toJSONString(chessboardId);
        return chessboardId;
    }
    @PostMapping(value = "/admin_user")
    public ModelAndView accessUser(){
        ModelAndView mav= new ModelAndView();
        mav.addObject("user", JSONArray.toJSONString(userDao.descSortByPoint()));
        mav.setViewName("member-list");
        return mav;
    }

    @PostMapping(value = "/adminLogin")
    public ModelAndView adminlogin(@RequestParam("id") String id, @RequestParam("password") String password)
    {

        Administrator administrator=administratorDao.queryAdministratorById(Integer.valueOf(id));
        if (administrator==null)
        {
            return new ModelAndView("index");
        }
        else if (password.equals(administrator.getPassword()))
        {
            ModelAndView mav=new ModelAndView();
            List<Administrator> administratorsList =administratorDao.selectAllAdministrator();
            mav.addObject("administrator",JSONArray.toJSONString(administratorsList));
            mav.setViewName("admin-list");
            return mav;
        }
        else
        {
            return new ModelAndView("index");
        }
    }

    @PostMapping (value = "/battle")
    @ResponseBody
    public ModelAndView battle(@RequestParam("userId1") String userId1,@RequestParam("userId2") String userId2){
        User user1= userDao.queryUserById(Integer.valueOf(userId1));
        User user2= userDao.queryUserById(Integer.valueOf(userId2));
        ModelAndView mav=new ModelAndView();
        String name1=user1.getName();
        String name2=user2.getName();
        int point1=user1.getPoint();
        int point2=user2.getPoint();
        mav.addObject("id1",userId1);
        mav.addObject("id2",userId2);
        mav.addObject("name1",name1);
        mav.addObject("name2",name2);
        mav.addObject("point1",point1);
        mav.addObject("point2",point2);
        return mav;
    }
////    public String battle(){
////        return "battle";
////    }


    @PostMapping(value = "/Regist")
    @ResponseBody
    private User registinmain(@RequestParam("username") String username,@RequestParam("password") String password, @RequestParam("rpassword")String rpassword) {
        User user=new User();
        // 两次密码一致
        if (password.equals(rpassword))
        {
            user.setName(username);
            user.setPassword(password);
            userDao.insertUser(user);
            return user;
        }
        else {
            return user;
        }
    }

    @PostMapping(value = "/adminRegist")
    @ResponseBody
    private Administrator registadmin(@RequestParam("adminname") String adminname,@RequestParam("password") String password, @RequestParam("rpassword")String rpassword) {
        Administrator administrator = new Administrator();
        // 两次密码一致
        if (password.equals(rpassword))
        {
            administrator.setName(adminname);
            administrator.setPassword(password);
            administratorDao.insertAdministrator(administrator);
            return administrator;
        }
        else {
            return administrator;
        }
    }

    @RequestMapping("addAdminData")
    @ResponseBody
    public ModelAndView addAdmin(@RequestParam("admin_name") String admin_name,@RequestParam("admin_password") String admin_password) {
        Administrator administrator = new Administrator();
        administrator.setName(admin_name);
        administrator.setPassword(admin_password);
        administratorDao.insertAdministrator(administrator);
        return new ModelAndView("redirect:/adminLogin");//返回到分页查询的requestMapping;
    }

    @RequestMapping("addUserData")
    @ResponseBody
    public ModelAndView addUser(@RequestParam("user_name") String user_name,@RequestParam("user_password") String user_password) {
        User user = new User();
        user.setName(user_name);
        user.setPassword(user_password);
        userDao.insertUser(user);
        return new ModelAndView("redirect:/admin_user");//返回到分页查询的requestMapping;
    }

    @RequestMapping("changepassword")
    @ResponseBody
    public ModelAndView changepassword(@RequestParam("new_password") String user_password,@RequestParam("id") int id) {
        User user = new User();
        user.setId(id);
        user.setName(userDao.queryUserById(id).getName());
        user.setPassword(user_password);
        userDao.updateUser(user);
        return new ModelAndView("redirect:/admin_user");//返回到分页查询的requestMapping;
    }

    @RequestMapping("updateUserData")
    @ResponseBody
    public ModelAndView updateUser(@RequestParam("user_name") String user_name,@RequestParam("user_password") String user_password,@RequestParam("id") int id) {
        User user = new User();
        user.setName(user_name);

        user.setId(id);
        user.setPassword(user_password);
        userDao.updateUser(user);
        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println(user.getPassword());
        return new ModelAndView("redirect:/admin_user");//返回到分页查询的requestMapping;
    }
/*
    @ResponseBody
    @PostMapping("/chesspos")
    public void sendArr(String[] info) {
        System.out.println(info[0]+","+info[1]+","+info[2]);
        Fight fight=new Fight();
        fight.setFightId(Integer.valueOf(info[2]));
        fight=fightDao.queryFightDetails(fight);
        if (Integer.parseInt(info[0])>9)
        {
            fight.setDetails(fight.getDetails()+info[0]);
        }
        else
        {
            fight.setDetails(fight.getDetails()+"0"+info[0]);
        }
        if (Integer.parseInt(info[1])>9)
        {
            fight.setDetails(fight.getDetails()+info[1]);
        }
        else
        {
            fight.setDetails(fight.getDetails()+"0"+info[1]);
        }
        fightDao.updateFightDetails(fight);
    }
    @RequestMapping(value = "/review")
    public ModelAndView review(@RequestParam("fightid") String fightid)
    {
        Fight fight=new Fight();
        fight.setFightId(Integer.valueOf(fightid));
        fight=fightDao.queryFightDetails(fight);
        String detail= fight.getDetails();
        String head=new String();
//        int[][] arr=new int[15][15];
//        show(arr);
//        int bw=2;
        while (!isInteger(head=detail.substring(0,1)))
        {
            detail=detail.substring(1);
        }
//        while (detail.length()!=0)
//        {
//            head=detail.substring(0,2);
//            int x=Integer.valueOf(head);
//            detail=detail.substring(2);
//            head=detail.substring(0,2);
//            detail=detail.substring(2);
//            int y=Integer.valueOf(head);
//            arr[x][y]=bw;
//            if (bw==2) bw=1;
//            else bw=2;
//        }
        ModelAndView mav=new ModelAndView();
        mav.addObject("detail",detail);
        mav.setViewName("rchess");
        return mav;

    }


    public static boolean isInteger(String str){
        Pattern pattern= Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
    private static void show(int[][] board)
    {

        System.out.print("   ");
        for (int i = 0; i <15 ; i++) {
            System.out.printf("%2d ",i);
        }
        System.out.println();
        for (int i = 0; i <15 ; i++) {
            System.out.printf("%2d ",i);
            for (int j = 0; j < 15; j++) {
                System.out.printf("%2d ",board[i][j]);
            }
            System.out.println();
        }
    }

     */
}
