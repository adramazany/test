package bip.test.hibenate3.safehtml.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Vacation {
    private Integer id;
    private Date startdate;
    private Date enddate;
    private Integer tenantid;

    @Id
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "STARTDATE")
    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    @Basic
    @Column(name = "ENDDATE")
    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    @Basic
    @Column(name = "TENANTID")
    public Integer getTenantid() {
        return tenantid;
    }

    public void setTenantid(Integer tenantid) {
        this.tenantid = tenantid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vacation vacation = (Vacation) o;

        if (id != null ? !id.equals(vacation.id) : vacation.id != null) return false;
        if (startdate != null ? !startdate.equals(vacation.startdate) : vacation.startdate != null) return false;
        if (enddate != null ? !enddate.equals(vacation.enddate) : vacation.enddate != null) return false;
        if (tenantid != null ? !tenantid.equals(vacation.tenantid) : vacation.tenantid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (startdate != null ? startdate.hashCode() : 0);
        result = 31 * result + (enddate != null ? enddate.hashCode() : 0);
        result = 31 * result + (tenantid != null ? tenantid.hashCode() : 0);
        return result;
    }
}
