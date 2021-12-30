package bip.test.orientdb;

import com.orientechnologies.orient.jdbc.OrientJdbcConnection;

import java.sql.*;

/**
 * Created by ramezani on 6/17/2018.
 */
public class OrientdbJDBC {
    public static void main(String[] args) throws Exception {
        //testInsert();
        etlPersonUpsert();
        testSelect();

    }
    public static void testSelect()throws Exception{
        System.out.println("OrientdbJDBC starting ...");
        Connection conn = (OrientJdbcConnection) DriverManager.getConnection("jdbc:orient:remote:localhost/sepehr", "root","orientdb@bip43");
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM Person");

        while(rs.next()) {

            System.out.println(
                    "getColumnCount=" + rs.getMetaData().getColumnCount()
                    +",@version=" + rs.getInt("@version")
                            + ",@class=" + rs.getString("@class")
                            + ",@rid=" + rs.getString("@rid")

                            + ",stringKey=" + rs.getString("stringKey")
                            + ",intKey=" + rs.getInt("intKey")

                            + ",text=" + rs.getString("text")
                            + ",personid=" + rs.getString("personid")
                            + ",firstName=" + rs.getString("firstname")
                            + ",lastName=" + rs.getString("lastname")

            );
/*
        +",firstName="+rs.getString("firstName")
                +",lastName="+rs.getString("lastName")
*/
            //rs.getInt("intKey");
        }

        rs.close();
        stmt.close();
        conn.close();
        System.out.println("OrientdbJDBC finished.");
    }


    public static void testInsert()throws Exception{
        System.out.println("OrientdbJDBC starting ...");
        Connection conn = (OrientJdbcConnection) DriverManager.getConnection("jdbc:orient:remote:localhost/sepehr", "root","orientdb@bip43");
        PreparedStatement ps = conn.prepareStatement(" INSERT INTO person (personid,nationalcode,firstname,lastname,gender,passport_code) " +
                        "VALUES (:personid,:nationalcode,:firstname,:lastname,:gender,:passport_code)");
        Statement stmt = conn.createStatement();

        for (int i = 21; i <= 30; i++) {
            ps.setInt(1,i);
            ps.setString(2,""+i);
            ps.setString(3,""+i);
            ps.setString(4,""+i);
            ps.setInt(5,1);
            ps.setString(6,""+i);
            ps.execute();
        }

        stmt.close();
        conn.close();
        System.out.println("OrientdbJDBC finished.");
    }

    public static void etlPerson()throws Exception{
        System.out.println("OrientdbJDBC starting ...");
        Connection cnDst = (OrientJdbcConnection) DriverManager.getConnection("jdbc:orient:remote:localhost/sepehr", "root","orientdb@bip43");
        PreparedStatement ps = cnDst.prepareStatement(" INSERT INTO person (personid,nationalcode,firstname,lastname,gender,passport_code) " +
                "VALUES (:personid,:nationalcode,:firstname,:lastname,:gender,:passport_code)");
        Statement stmt = cnDst.createStatement();

        for (int i = 21; i <= 30; i++) {
            ps.setInt(1,i);
            ps.setString(2,""+i);
            ps.setString(3,""+i);
            ps.setString(4,""+i);
            ps.setInt(5,1);
            ps.setString(6,""+i);
            ps.execute();
        }

        stmt.close();
        cnDst.close();
        System.out.println("OrientdbJDBC finished.");
    }

    public static void etlPersonUpsert()throws Exception{
        System.out.println("OrientdbJDBC starting ...");
        Connection cnDst = (OrientJdbcConnection) DriverManager.getConnection("jdbc:orient:remote:localhost/sepehr", "root","orientdb@bip43");
        PreparedStatement ps = cnDst.prepareStatement(" update person set " +
                "personid=:personid," +
                "nationalcode=:nationalcode," +
                "firstname=:firstname," +
                "lastname=:lastname," +
                "gender=:gender," +
                "passport_code=:passport_code " +
                " UPSERT WHERE personid=:personid");
        Statement stmt = cnDst.createStatement();

        for (int i = 25; i <= 35; i++) {
            ps.setInt(1,i);
            ps.setString(2,"up-"+i);
            ps.setString(3,"up-"+i);
            ps.setString(4,"up-"+i);
            ps.setInt(5,1);
            ps.setString(6,"up-"+i);
            ps.setInt(7,i);
            ps.execute();
        }

        stmt.close();
        cnDst.close();
        System.out.println("OrientdbJDBC finished.");
    }
}
