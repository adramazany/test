package bip.test.jdbctemplate.service;

import bip.test.jdbctemplate.model.Test;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by ramezani on 8/9/2018.
 */

public interface TestService {

    Integer insert(Test test);
    Integer update(String sql, Map<String, ?> paramMap, String pkField);
    Integer update(String sql, Object obj, String pkField);
    List<Test> findAll();

}
