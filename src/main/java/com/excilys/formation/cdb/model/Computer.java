package com.excilys.formation.cdb.model;

import com.excilys.formation.cdb.persistence.dao.impl.DbFields;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Computer implements Model {

    @Id
    @GeneratedValue
    @Column(name = DbFields.COMPUTER_ID)
    private Long id;

    @Column(name = DbFields.COMPUTER_NAME)
    private String name;

    @Column(name = DbFields.COMPUTER_INTRODUCED)
    private LocalDate introduced;

    @Column(name = DbFields.COMPUTER_DISCONTINUED)
    private LocalDate discontinued;

    @ManyToOne
    private Company company;

    public Computer() {

    }

    public Computer(Long id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.company = company;
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

    public LocalDate getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String shortToString() {
        return String.format("ID: %d, name: %s", this.id, this.name);
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

        Computer computer = (Computer) o;
        return Objects.equals(id, computer.id);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        StringBuilder sb = new StringBuilder("ID: ").append(this.id).append("\n");
        sb.append("Name: ").append(this.name).append("\n");
        sb.append("Company ID: ").append(this.company).append("\n");
        sb.append("Introduced in: ");
        if (introduced != null) {
            sb.append(introduced.format(formatter));
        } else {
            sb.append("N/A");
        }
        sb.append("\n");
        sb.append("Discontinued in: ");
        if (discontinued != null) {
            sb.append(discontinued.format(formatter));
        } else {
            sb.append("N/A");
        }
        sb.append("\n");
        return sb.toString();
    }

    public static class Builder {
        private Long nestedId = null;
        private String nestedName = null;
        private LocalDate nestedIntroduced = null;
        private LocalDate nestedDiscontinued = null;
        private Company nestedCompany = null;

        public Builder() {
        }

        public Builder id(final Long id) {
            this.nestedId = id;
            return this;
        }

        public Builder name(final String name) {
            this.nestedName = name;
            return this;
        }

        public Builder introduced(final LocalDate intro) {
            this.nestedIntroduced = intro;
            return this;
        }

        public Builder introduced(final String intro) {
            if ((intro == null) || (intro.equals(""))) {
                this.nestedIntroduced = null;
            } else {
                String tmp = intro.split(" ")[0];
                this.nestedIntroduced = LocalDate.parse(tmp);
            }
            return this;
        }

        public Builder discontinued(final LocalDate discontinued) {
            this.nestedDiscontinued = discontinued;
            return this;
        }

        public Builder discontinued(final String discontinued) {
            if ((discontinued == null) || (discontinued.equals(""))) {
                this.nestedDiscontinued = null;
            } else {
                String tmp = discontinued.split(" ")[0];
                this.nestedDiscontinued = LocalDate.parse(tmp);
            }
            return this;
        }

        public Builder company(final Company company) {
            this.nestedCompany = company;
            return this;
        }

        public Computer build() {
            return new Computer(nestedId, nestedName, nestedIntroduced, nestedDiscontinued, nestedCompany);
        }
    }
}
