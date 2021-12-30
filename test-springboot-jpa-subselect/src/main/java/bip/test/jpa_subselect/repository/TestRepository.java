package bip.test.jpa_subselect.repository;

import bip.test.jpa_subselect.model.Test;
import bip.test.jpa_subselect.model.TestDTO;
import org.hibernate.criterion.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ramezani on 4/22/2020.
 */
@Repository
public interface TestRepository extends JpaRepository<Test,Integer>{


}
