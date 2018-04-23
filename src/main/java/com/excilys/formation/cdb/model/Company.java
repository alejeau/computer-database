package com.excilys.formation.cdb.model;

import com.excilys.formation.cdb.persistence.dao.impl.DbFields;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Company implements Model {

    @Id
    @GeneratedValue
    @Column(name = DbFields.COMPANY_ID)
    private Long id;

    @Column(name = DbFields.COMPANY_NAME)
    private String name;

    public Company() {

    }

    public Company(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String shortToString() {
        return this.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Company company = (Company) o;
        return Objects.equals(id, company.id);
    }

    @Override
    public String toString() {
        return String.format("ID: %d, name: %s", this.id, this.name);
    }

    public static class Builder {
        private Long nestedId = null;
        private String nestedName = null;

        public Builder() {
        }

        public Company.Builder id(final Long id) {
            this.nestedId = id;
            return this;
        }

        public Company.Builder name(final String name) {
            this.nestedName = name;
            return this;
        }

        public Company build() {
            return new Company(nestedId, nestedName);
        }
    }
}
