package bip.test.hibenate3.safehtml.dao;

import bip.test.hibenate3.safehtml.model.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class DepartmentDAO {
    @Autowired
    SessionFactory sessionFactory;

    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    public List<Department> findAll(){
        //getCurrentSession().beginTransaction();
        List<Department> result = getCurrentSession().createQuery("from Department").list();
        //getCurrentSession().getTransaction().rollback();
        return result;
    }

    public void save(Department department){
        getCurrentSession().saveOrUpdate(department);
    }

}
