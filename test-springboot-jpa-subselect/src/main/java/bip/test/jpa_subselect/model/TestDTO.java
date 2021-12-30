package bip.test.jpa_subselect.model;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ramezani on 4/22/2020.
 */
@Entity
@Immutable
@Subselect("select asf.id,asf.code,decode(code,0,'abc',1,'def',2,'ghi') as code_title,title,t.type_title from app_staus_field asf " +
        " left join bt_type t on t.id=asf.code" +
        "")
public class TestDTO {
    Integer id;
    Integer code;
    String title;
    String code_title;
    String type_title;

    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode_title() {
        return code_title;
    }

    public void setCode_title(String code_title) {
        this.code_title = code_title;
    }

    public String getType_title() {
        return type_title;
    }

    public void setType_title(String type_title) {
        this.type_title = type_title;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
