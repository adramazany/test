package bip.test.jdbctemplate.service;

import bip.test.jdbctemplate.model.Test;
import bip.test.jdbctemplate.rowmapper.TestRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * Created by ramezani on 8/9/2018.
 */
@Service
public class TestServiceImpl implements TestService{

    JdbcTemplate jdbcTemplate2;
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public TestServiceImpl(DataSource dataSource){

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcTemplate2 = new JdbcTemplate(dataSource);
    }

    public Integer insert(Test test) {
        String sql ="insert into Test (id,code,name)values(hibernate_sequence.nextval,:code,:name)";
        KeyHolder keyHolder=new GeneratedKeyHolder();
        jdbcTemplate.update(sql,new BeanPropertySqlParameterSource(test),keyHolder,new String[]{"id"});
        test.setId(keyHolder.getKey().intValue());
        return keyHolder.getKey().intValue();
    }

    public Integer update(String sql, Map<String, ?> paramMap, String pkField) {
        if(pkField!=null && !"".equals(pkField)){
            KeyHolder keyHolder=new GeneratedKeyHolder();
            jdbcTemplate.update(sql,new MapSqlParameterSource(paramMap),keyHolder,new String[]{pkField});
            return keyHolder.getKey().intValue();
        }else{
            jdbcTemplate.update(sql,new MapSqlParameterSource(paramMap));
            return null;
        }
    }

    public Integer update(String sql, Object obj, String pkField) {
        if(pkField!=null && !"".equals(pkField)){
            KeyHolder keyHolder=new GeneratedKeyHolder();
            jdbcTemplate.update(sql,new BeanPropertySqlParameterSource(obj),keyHolder,new String[]{pkField});
            return keyHolder.getKey().intValue();
        }else{
            jdbcTemplate.update(sql,new BeanPropertySqlParameterSource(obj));
            return null;
        }
    }

    public List<Test> findAll() {
        return jdbcTemplate.query("select * from test",new TestRowMapper());

    }
}
