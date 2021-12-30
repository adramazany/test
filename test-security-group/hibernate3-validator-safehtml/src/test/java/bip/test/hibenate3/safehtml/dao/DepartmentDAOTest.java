package bip.test.hibenate3.safehtml.dao;

import bip.test.hibenate3.safehtml.model.Department;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

//@RunWith(SpringRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-hibernate.xml"})
public class DepartmentDAOTest {

    @Autowired
    DepartmentDAO departmentDAO;

    Gson gson = new Gson();

    @Test
    public void findAll() {
        List<Department> result = departmentDAO.findAll();
        System.out.println("result = " + gson.toJson(result));
    }

    @Test
    public void save() {
        Department d= new Department();
        d.setDeptid(100);
        d.setName("BI <script>alert(1);</script>");//script injection
        d.setLocation("teh ';update department set q1=0 where depid=1;--");//sql injection
        departmentDAO.save(d);
        System.out.println("DepartmentDAOTest.save succeed.");
    }
}