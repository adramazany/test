package bip.service;

import bip.entity.Config;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelloWorld {
    private String name;
    private DataSource dataSource;
    private NamedParameterJdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate jdbcTemplateLocal;
    private HibernateTemplate hibernateTemplate;

    public void setName(String name) {
        this.name = name;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public NamedParameterJdbcTemplate getJdbcTemplateLocal() {
        try {
            if (jdbcTemplateLocal == null) {
                jdbcTemplateLocal = new NamedParameterJdbcTemplate(dataSource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jdbcTemplateLocal;
    }

    public void printHello() {
        System.out.println("Spring 3 : Hello ! " + name);
    }

    public void printConfig_jdbcTemplate(){
        Map<String, ?>  paramMap = new HashMap<>();
        List<Map<String,Object>> res = jdbcTemplate.queryForList("select * from config",paramMap);
        for (int i = 0; i < res.size(); i++) {
            Map<String,Object> row = res.get(i);
            // row.keySet().forEach( k -> k  );
            for(String k: row.keySet()){
                System.out.print(String.format("%s=%s, \t",k,row.get(k)));
            }
            System.out.println();
        }
    }

    public void printConfig_hibernateTemplate(){
        Map<String, ?>  paramMap = new HashMap<>();
        List<Config> res = hibernateTemplate.loadAll(Config.class);
        for (int i = 0; i < res.size(); i++) {
            System.out.println(String.format("config=%s, \t", res.get(i) ));
        }
    }

    public void raise_postgres_setTimeoutException(){
        String sql ="select * from Config where key like 'Captcha_security'";
        Map<String, String> params=new HashMap<String, String>();
        List<Map<String,Object>> list= getJdbcTemplateLocal().queryForList(sql,params);
        Integer result = 1;
        for (Map<String, Object> row : list) {
            //result = (Integer.parseInt(row.get("JSON_VALUE").toString()) == 0)?false:true;
            result = Integer.parseInt(row.get("JSON_VALUE").toString());
        }
        System.out.println(result);

    }
}

