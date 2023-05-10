package com.upboard.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBConnectionTest2Test {

    @Autowired
    DataSource ds;


    @Test // 테스트 메서드는 public 이어야 한다.
    public void insertUserTest() throws Exception {
        User user = new User("programmer", "programmer", "프로그래머", "programmer@gmail.com", new Date(), "instagram", new Date());
        deleteProgrammar();
        int rowCnt = insertUser(user);

        assertTrue(rowCnt == 1);
    }

    @Test
    public void selectUserTest() throws Exception {
        deleteProgrammar();
        User user = new User("programmer", "programmer", "프로그래머", "programmer@gmail.com", new Date(), "instagram", new Date());
        insertUser(user);
        User user2 = selectUser("programmer");

        assertTrue(user.getId().equals("programmer"));
    }

    @Test
    public void deleteUserTest() throws Exception {
        deleteProgrammar();
        deleteUser("1234");
        int rowCnt = deleteUser("1234"); // 어떤 것을 줘도 결과는 0. 다 지운 후 삭제 하는 것이므로.

        assertTrue(rowCnt == 0);

        User user = new User("programmer", "programmer", "프로그래머", "programmer@gmail.com", new Date(), "instagram", new Date());
        rowCnt = insertUser(user);
        assertTrue(rowCnt == 1);

        rowCnt = deleteUser(user.getId());
        assertTrue(rowCnt == 1);

        assertTrue(selectUser(user.getId()) == null);
    }

    @Test
    public void updateUserTest() throws Exception {
        deleteProgrammar();
        User user = new User("programmer", "programmer", "프로그래머", "programmer@gmail.com", new Date(), "instagram", new Date());
        int rowCnt = insertUser(user);
        assertTrue(rowCnt == 1);

        user.setName("성장하는 프로그래머");
        rowCnt = updateUser(user);
        assertTrue(rowCnt == 1);

        User foundUser = selectUser("programmer");
        assertTrue(foundUser.getName().equals("성장하는 프로그래머"));
    }

    public User selectUser(String id) throws Exception {
        Connection conn = ds.getConnection();

        String sql = "select * from user_info where id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();// select

        if(rs.next()) {
            User user = new User();
            user.setId(rs.getString(1));
            user.setPwd(rs.getString(2));
            user.setName(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setBirth(new Date(rs.getDate(5).getTime()));
            user.setSns(rs.getString(6));
            user.setReg_date(new Date(rs.getTimestamp(7).getTime()));

            return user;
        }

        return null;
    }

    private void deleteAll() throws Exception{
        Connection conn = ds.getConnection();

        // delete from user_info;

        String sql = "delete from user_info";

        PreparedStatement pstmt = conn.prepareStatement(sql);   // SQL Injection 공격, 성능향상
        pstmt.executeUpdate(); // insert, delete, update

    }

    public int deleteUser(String id) throws Exception {
        Connection conn = ds.getConnection();

        String sql = "delete from user_info where id=?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);

        return pstmt.executeUpdate();
    }

    private void deleteProgrammar() throws Exception{
        Connection conn = ds.getConnection();

        // delete from user_info;

        String sql = "delete from user_info where id='programmer'";

        PreparedStatement pstmt = conn.prepareStatement(sql);   // SQL Injection 공격, 성능향상
        pstmt.executeUpdate(); // insert, delete, update

    }


    // 사용자 정보를 user_info 테이블에 저장하는 메서드
    public int insertUser(User user) throws Exception {
        Connection conn = ds.getConnection();

        // insert into user_info (id, pwd, name, email, birth, sns, reg_date)
        //values ('glad', 'glad', '기쁨이', 'glad@gmail.com', '2015-7-7', 'instagram', now());

        String sql = "insert into user_info values (?, ?, ?, ?, ?, ?, now())";

        // PreparedStatement: SQL Injection 공격, 성능향상
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getPwd());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
        pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime()));
        pstmt.setString(6, user.getSns());

        int rowCnt = pstmt.executeUpdate(); // insert, delete, update

        return rowCnt;
    }

    // 매개변수로 받은 사용자 정보로 user_info 테이블을 update하는 메서드
    public int updateUser(User user) throws Exception {
        int rowCnt = 0;


        String sql = "update user_info " +
                "set pwd = ?, name=?, email=?, birth =?, sns=?, reg_date=? " +
                "where id = ? ";

        // try-with-resources - since jdk7
        try (
                Connection conn = ds.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql); // SQL Injection공격, 성능향상
        ){
            pstmt.setString(1, user.getPwd());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setDate(4, new java.sql.Date(user.getBirth().getTime()));
            pstmt.setString(5, user.getSns());
            pstmt.setTimestamp(6, new java.sql.Timestamp(user.getReg_date().getTime()));
            pstmt.setString(7, user.getId());

            rowCnt = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

        return rowCnt;

    }

    @Test
    public void springJdbcConnectionTest() throws Exception {
//        ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
//        DataSource ds = ac.getBean(DataSource.class);

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn);
        assertTrue(conn != null); // 괄호 안의 조건식이 true면, 테스트 성공, 아니면 실패

    }

}