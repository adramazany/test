package bip.test.data_version.model;

import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Created by ramezani on 8/7/2018.
 */
@Entity
@Table
@Audited
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    String name;
    String code;

    public Test(){}
    public Test(String name, String code) {
        this.name = name;
        this.code = code;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
