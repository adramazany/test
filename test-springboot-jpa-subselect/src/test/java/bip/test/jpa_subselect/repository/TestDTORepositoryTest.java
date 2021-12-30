package bip.test.jpa_subselect.repository;

import bip.test.TestSpringbootJpaSubselectApplication;
import bip.test.jpa_subselect.model.TestDTO;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by ramezani on 4/22/2020.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestSpringbootJpaSubselectApplication.class)
public class TestDTORepositoryTest {

    @Autowired
    private TestDTORepository repository;

    @Test
    public void findDTOByExample() {
        TestDTO param = new TestDTO();
        //param.setCode("def");
        param.setType_title("بخشنامه مرجع");
        List<TestDTO> sBankDTOs = repository.findAll(Example.of(param));
        System.out.println("sBankDTOs = " + new Gson().toJson( sBankDTOs ));
    }

}