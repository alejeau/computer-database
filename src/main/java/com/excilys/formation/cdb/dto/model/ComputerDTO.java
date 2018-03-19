package com.excilys.formation.cdb.dto.model;

import com.excilys.formation.cdb.dto.ModelDTO;

import java.util.Objects;

public class ComputerDTO extends ModelDTO {
    private long id;
    private String name;
    private String introduced;
    private String discontinued;
    private String companyName;

    public ComputerDTO() {

    }

    public ComputerDTO(long id, String name, String introduced, String discontinued, String companyName) {
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.companyName = companyName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public static class Builder {
        private long nestedId = -1;
        private String nestedName = null;
        private String nestedIntroduced = null;
        private String nestedDiscontinued = null;
        private String nestedCompanyName = null;

        public Builder() {
        }

        public ComputerDTO.Builder id(final long id) {
            this.nestedId = id;
            return this;
        }

        public ComputerDTO.Builder name(final String name) {
            this.nestedName = name;
            return this;
        }

        public ComputerDTO.Builder introduced(final String intro) {
            this.nestedIntroduced = intro;
            return this;
        }

        public ComputerDTO.Builder discontinued(final String discontinued) {
            this.nestedDiscontinued = discontinued;
            return this;
        }

        public ComputerDTO.Builder companyId(final String company) {
            this.nestedCompanyName = company;
            return this;
        }

        public ComputerDTO build() {
            return new ComputerDTO(nestedId, nestedName, nestedIntroduced, nestedDiscontinued, nestedCompanyName);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ID: ").append(this.id).append("\n");
        sb.append("Name: ").append(this.name).append("\n");
        sb.append("Company ID: ").append(this.companyName).append("\n");
        sb.append("Introduced in: ");
        if (introduced != null) {
            sb.append(introduced);
        } else {
            sb.append("N/A");
        }
        sb.append("\n");
        sb.append("Discontinued in: ");
        if (discontinued != null) {
            sb.append(discontinued);
        } else {
            sb.append("N/A");
        }
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ComputerDTO computerDTO = (ComputerDTO) o;
        return Objects.equals(id, computerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
