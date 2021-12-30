package bip.test.timeout.service;


import java.util.List;
import java.util.Map;

/**
 * Created by ramezani on 8/9/2018.
 */

public interface TestService {

    Integer update(String sql, Map<String, ?> paramMap, String pkField);
    Integer update(String sql, Object obj, String pkField);
    List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap);

}
