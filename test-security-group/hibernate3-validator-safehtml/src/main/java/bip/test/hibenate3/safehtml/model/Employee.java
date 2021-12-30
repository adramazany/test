package bip.test.hibenate3.safehtml.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Employee {
    private Integer eid;
    private String firstname;
    private String lastname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private Date birthdate;
    private String picurl;
    private String twitterid;
    private Integer tenantid;

    @Id
    @Column(name = "EID", nullable = false)
    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    @Basic
    @Column(name = "FIRSTNAME", nullable = true, length = 255)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "LASTNAME", nullable = true, length = 255)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "STREET", nullable = true, length = 255)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Basic
    @Column(name = "CITY", nullable = true, length = 255)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "STATE", nullable = true, length = 2)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "ZIP", nullable = true, length = 255)
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Basic
    @Column(name = "BIRTHDATE", nullable = true)
    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Basic
    @Column(name = "PICURL", nullable = true, length = 255)
    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    @Basic
    @Column(name = "TWITTERID", nullable = true, length = 40)
    public String getTwitterid() {
        return twitterid;
    }

    public void setTwitterid(String twitterid) {
        this.twitterid = twitterid;
    }

    @Basic
    @Column(name = "TENANTID", nullable = true)
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

        Employee employee = (Employee) o;

        if (eid != null ? !eid.equals(employee.eid) : employee.eid != null) return false;
        if (firstname != null ? !firstname.equals(employee.firstname) : employee.firstname != null) return false;
        if (lastname != null ? !lastname.equals(employee.lastname) : employee.lastname != null) return false;
        if (street != null ? !street.equals(employee.street) : employee.street != null) return false;
        if (city != null ? !city.equals(employee.city) : employee.city != null) return false;
        if (state != null ? !state.equals(employee.state) : employee.state != null) return false;
        if (zip != null ? !zip.equals(employee.zip) : employee.zip != null) return false;
        if (birthdate != null ? !birthdate.equals(employee.birthdate) : employee.birthdate != null) return false;
        if (picurl != null ? !picurl.equals(employee.picurl) : employee.picurl != null) return false;
        if (twitterid != null ? !twitterid.equals(employee.twitterid) : employee.twitterid != null) return false;
        if (tenantid != null ? !tenantid.equals(employee.tenantid) : employee.tenantid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = eid != null ? eid.hashCode() : 0;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        result = 31 * result + (birthdate != null ? birthdate.hashCode() : 0);
        result = 31 * result + (picurl != null ? picurl.hashCode() : 0);
        result = 31 * result + (twitterid != null ? twitterid.hashCode() : 0);
        result = 31 * result + (tenantid != null ? tenantid.hashCode() : 0);
        return result;
    }
}
