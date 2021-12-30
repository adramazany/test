package bip.test.timeout.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by ramezani on 8/14/2018.
 */
public class TestJdbcTimeout {

    public static void main(String[] args) {
        Properties connectionProperties = new Properties();
        connectionProperties.setProperty("oracle.jdbc.ReadTimeout","10000");

        Properties dataSourceProperties = new Properties();
        dataSourceProperties.setProperty("url","jdbc:oracle:thin:@192.168.88.2:1521:orcl");
        dataSourceProperties.setProperty("user","saba");
        dataSourceProperties.setProperty("password","saba");
        dataSourceProperties.put("connectionProperties",connectionProperties);


        Properties props=new Properties();
        props.setProperty("dataSourceClassName","oracle.jdbc.pool.OracleDataSource");
        props.put("dataSourceProperties",dataSourceProperties);

        HikariConfig hikariConfig=new HikariConfig(props);
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        Connection cn=null;
        Statement stmt=null;
        ResultSet rs=null;
        try{
            cn= dataSource.getConnection();
            stmt = cn.createStatement();
            stmt.execute("delete from topics where topics_id=1620");
//
//
//
//            1621
//            1622
//            1623
//            1624
//            1625

        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(rs!=null)try{rs.close();}catch(Exception ex){}
            if(stmt!=null)try{stmt.close();}catch(Exception ex){}
            if(cn!=null)try{cn.close();}catch(Exception ex){}
        }


    }

}
