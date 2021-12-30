package bip.test.hibenate3.safehtml.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Department {
    private Integer deptid;
    private String name;
    private Integer budget;
    private Integer q1;
    private Integer q2;
    private Integer q3;
    private Integer q4;
    private String deptcode;
    private String location;
    private Integer tenantid;

    @Id
    @Column(name = "DEPTID")
    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "BUDGET")
    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    @Basic
    @Column(name = "Q1")
    public Integer getQ1() {
        return q1;
    }

    public void setQ1(Integer q1) {
        this.q1 = q1;
    }

    @Basic
    @Column(name = "Q2")
    public Integer getQ2() {
        return q2;
    }

    public void setQ2(Integer q2) {
        this.q2 = q2;
    }

    @Basic
    @Column(name = "Q3")
    public Integer getQ3() {
        return q3;
    }

    public void setQ3(Integer q3) {
        this.q3 = q3;
    }

    @Basic
    @Column(name = "Q4")
    public Integer getQ4() {
        return q4;
    }

    public void setQ4(Integer q4) {
        this.q4 = q4;
    }

    @Basic
    @Column(name = "DEPTCODE")
    public String getDeptcode() {
        return deptcode;
    }

    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode;
    }

    @Basic
    @Column(name = "LOCATION")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

        Department that = (Department) o;

        if (deptid != null ? !deptid.equals(that.deptid) : that.deptid != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (budget != null ? !budget.equals(that.budget) : that.budget != null) return false;
        if (q1 != null ? !q1.equals(that.q1) : that.q1 != null) return false;
        if (q2 != null ? !q2.equals(that.q2) : that.q2 != null) return false;
        if (q3 != null ? !q3.equals(that.q3) : that.q3 != null) return false;
        if (q4 != null ? !q4.equals(that.q4) : that.q4 != null) return false;
        if (deptcode != null ? !deptcode.equals(that.deptcode) : that.deptcode != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (tenantid != null ? !tenantid.equals(that.tenantid) : that.tenantid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = deptid != null ? deptid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (budget != null ? budget.hashCode() : 0);
        result = 31 * result + (q1 != null ? q1.hashCode() : 0);
        result = 31 * result + (q2 != null ? q2.hashCode() : 0);
        result = 31 * result + (q3 != null ? q3.hashCode() : 0);
        result = 31 * result + (q4 != null ? q4.hashCode() : 0);
        result = 31 * result + (deptcode != null ? deptcode.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (tenantid != null ? tenantid.hashCode() : 0);
        return result;
    }
}
