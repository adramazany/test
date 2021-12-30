package bip.test.data_version.service;

import bip.test.data_version.model.Test;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ramezani on 8/7/2018.
 */
@Repository
public interface TestService extends CrudRepository<Test,Integer>{
}
