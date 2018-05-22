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

    private String pictureUrl;

    public CompanyDTO() {

    }

    public CompanyDTO(@NotNull @Min(1L) Long id, @NotNull @NotEmpty String name) {
        this.id = id;
        this.name = name;
        this.pictureUrl = null;
    }

    public CompanyDTO(@NotNull @Min(1L) Long id, @NotNull @NotEmpty String name, String pictureUrl) {
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
    public String toString() {
        return String.format("ID: %d, name: %s", this.id, this.name);
    }
}
