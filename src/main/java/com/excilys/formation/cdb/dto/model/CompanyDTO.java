package com.excilys.formation.cdb.dto.model;

import com.excilys.formation.cdb.dto.ModelDTO;

public class CompanyDTO extends ModelDTO {

    private Long id;
    private String name;

    public CompanyDTO() {

    }

    public CompanyDTO(Long id, String name) {
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
}
