package bip.test.jpa_subselect.repository;

import bip.test.jpa_subselect.model.TestDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ramezani on 4/22/2020.
 */
@Repository
public interface TestDTORepository extends org.springframework.data.repository.Repository<TestDTO,Integer>,MyQueryByExampleExecutor<TestDTO>{
}
