package com.excilys.formation.cdb.dto.model;

import com.excilys.formation.cdb.dto.ModelDTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CompanyDTO implements ModelDTO {

    @NotNull
    @Min(1L)
    private Long id;

    @NotNull
    @NotEmpty
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
