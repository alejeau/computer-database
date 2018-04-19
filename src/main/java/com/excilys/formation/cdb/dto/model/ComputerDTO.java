package com.excilys.formation.cdb.dto.model;

import com.excilys.formation.cdb.dto.ModelDTO;

import java.util.Objects;

public class ComputerDTO implements ModelDTO {
    private long id;
    private String name;
    private String introduced;
    private String discontinued;
    private String companyName;
    private Long companyId;

    public ComputerDTO() {
        this.id = -1;
    }

    public ComputerDTO(long id, String name, String introduced, String discontinued, String companyName, Long companyId) {
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.companyName = companyName;
        this.companyId = companyId;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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
