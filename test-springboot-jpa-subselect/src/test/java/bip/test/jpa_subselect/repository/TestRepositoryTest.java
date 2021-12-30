package bip.test.jpa_subselect.repository;


import bip.test.TestSpringbootJpaSubselectApplication;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by ramezani on 4/22/2020.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestSpringbootJpaSubselectApplication.class)
//@ActiveProfiles(profiles = {"test", "no-cache", "db2"})
public class TestRepositoryTest {

    @Autowired
    private TestRepository repository;

/*
    @Test
    public void findDTOs() {
        String bankCode=null;
        Integer cbiCode=null;
        Integer shaparakCode=null;
        Integer bankType=null;
        Integer domestic=null;
        Integer status=null;
        List<SBankDTO> sBankDTOs = repository.findDTOs(bankCode,cbiCode,shaparakCode,bankType,domestic,status);
        System.out.println("sBankDTOs = " + new Gson().toJson( sBankDTOs ));
    }
*/

/*
    @Test
    public void findDTOByExample() {
        SBank param = new SBank();
        param.setCode("1");
        param.setCountry_uid(364);
        //List<SBank> sBankDTOs = repository.findAll(Example.of(param));
        List<SBankDTO> sBankDTOs = repository.findByExampleDTO(Example.of(param));
        System.out.println("sBankDTOs = " + new Gson().toJson( sBankDTOs ));
    }
*/


    @Test
    public void findAll() {
        List<bip.test.jpa_subselect.model.Test> sBanks = repository.findAll();
        System.out.println("sBanks = " + new Gson().toJson( sBanks ));
    }

/*
    @Test
    public void findOne() {
        bip.test.jpa_subselect.model.Test sBank = repository.findOne(24);
        System.out.println("sBankDTOs = " + new Gson().toJson( sBank ));
    }
*/

/*
    @Test
    public void save() {
        SBank sBank = new SBank();
        sBank.setCode("2");
        sBank.setName("بانک پارسیان");
        sBank.setLatin("Parsian");
        sBank.setType((short)1);
        sBank.setInstitution_uid(1);
        sBank.setCountry_uid(364);
        sBank.setCurrency_uid(364);
        sBank.setStatus((short)1);
        repository.save(sBank);
        System.out.println("sBank = " + new Gson().toJson( sBank ));
    }

    @Test
    public void update() {
        List<SBank> sBanks = repository.findAll();
        SBank sBank = repository.findOne( sBanks.get(0).getUid() );
        int len = sBank.getLatin().indexOf(" ");
        if(len==-1)len=sBank.getLatin().length();
        String latin = sBank.getLatin().substring( 0, len );
        sBank.setLatin(latin+" "+(new Date()));
        repository.update(sBank);
        System.out.println("sBank = " + new Gson().toJson( sBank ));
    }

    @Test
    public void delete() {
        System.out.println("count banks before delete = " + repository.count() );
        repository.delete(41);
        System.out.println("count banks after delete = " + repository.count() );
    }
*/


}