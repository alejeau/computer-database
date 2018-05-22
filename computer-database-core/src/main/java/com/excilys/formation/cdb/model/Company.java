package com.excilys.formation.cdb.model;


import com.excilys.formation.cdb.model.constants.DbFields;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = DbFields.COMPANY)
public class Company implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DbFields.COMPANY_ID)
    private Long id;

    @Column(name = DbFields.COMPANY_NAME)
    private String name;

    @Column(name = DbFields.COMPANY_PICTURE_URL)
    private String pictureUrl;

    public Company() {

    }

    public Company(Long id, String name) {
        this.id = id;
        this.name = name;
        this.pictureUrl = null;
    }

    public Company(Long id, String name, String pictureUrl) {
        this.id = id;
        this.name = name;
        this.pictureUrl = pictureUrl;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
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
        private String nestedPictureUrl = null;

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

        public Company.Builder pictureUrl(final String pictureUrl) {
            this.nestedPictureUrl = pictureUrl;
            return this;
        }

        public Company build() {
            return new Company(nestedId, nestedName, nestedPictureUrl);
        }
    }
}
