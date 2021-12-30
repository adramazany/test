package bip.test.timeout.rowmapper;

import bip.test.timeout.model.Test;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ramezani on 8/9/2018.
 */
public class TestRowMapper implements RowMapper<Test> {
    public Test mapRow(ResultSet rs, int i) throws SQLException {
        return new Test(
                rs.getInt("id")
                ,rs.getString("code")
                ,rs.getString("name")
        );
    }
}
