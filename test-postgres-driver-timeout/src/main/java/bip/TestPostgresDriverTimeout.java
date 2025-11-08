package bip;

import bip.service.HelloWorld;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Properties;

public class TestPostgresDriverTimeout {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        System.out.println("bip.TestPostgresDriverTimeout...");
        //testTimeout1();
        //testTimeout1();
        testSpring();
    }

    public static void testTimeout1()throws ClassNotFoundException, SQLException {
        System.out.println("testTimeout1....");
        Class.forName("org.postgresql.Driver");
        Connection cn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","test","test");

        Statement stmt = cn.createStatement();
        stmt.setQueryTimeout(300);
        ResultSet rs = stmt.executeQuery("select * from config");
        while(rs.next()) {
            System.out.println(String.format("key:%s, value:%s", rs.getString("key"), rs.getString("json_value")));
        }
        cn.close();
    }

    public static void testTimeout2() throws Exception {
        System.out.println("testTimeout1....");
        Properties cfg=new Properties();
        cfg.setProperty("driverClassName", "org.postgresql.Driver");
        cfg.setProperty("url", "jdbc:postgresql://localhost:5432/postgres");
        cfg.setProperty("username", "test");
        cfg.setProperty("password", "test");
        cfg.setProperty("maxActive", "20");
        DataSource ds = BasicDataSourceFactory.createDataSource(cfg);
        Connection cn = ds.getConnection();

        Statement stmt = cn.createStatement();
        stmt.setQueryTimeout(300);
        ResultSet rs = stmt.executeQuery("select * from config");
        while(rs.next()) {
            System.out.println(String.format("key:%s, value:%s", rs.getString("key"), rs.getString("json_value")));
        }
        cn.close();
    }

    public static void testSpring(){
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "springBeans.xml");

        HelloWorld obj = (HelloWorld) context.getBean("helloBean");
        obj.printHello();
        System.out.println("--------------------------------------------");
        obj.printConfig_jdbcTemplate();
        System.out.println("--------------------------------------------");
        obj.printConfig_hibernateTemplate();
        System.out.println("--------------------------------------------");
        obj.raise_postgres_setTimeoutException();
        System.out.println("--------------------------------------------");
    }
}
