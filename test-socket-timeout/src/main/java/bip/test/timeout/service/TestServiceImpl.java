package bip.test.timeout.service;

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

    JdbcTemplate jdbcTemplateBase;
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public TestServiceImpl(DataSource dataSource){

        //Connection c = dataSource.getConnection();


        jdbcTemplateBase = new JdbcTemplate(dataSource);
        jdbcTemplateBase.setQueryTimeout(10);// seconds
        jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplateBase);
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
    public List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap){
        return jdbcTemplate.queryForList(sql,paramMap);
    }

}
