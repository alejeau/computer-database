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
    private String description;

    public CompanyDTO() {

    }

    public CompanyDTO(@NotNull @Min(1L) Long id, @NotNull @NotEmpty String name) {
        this.id = id;
        this.name = name;
        this.pictureUrl = null;
    }

    public CompanyDTO(@NotNull @Min(1L) Long id, @NotNull @NotEmpty String name, String pictureUrl, String description) {
        this.id = id;
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String shortToString() {
        return this.toString();
    }

    @Override
    public String toString() {
        return String.format("ID: %d\nname: %s\nurl: %s\ndesciption: %s", this.id, this.name, this.pictureUrl, this.description);
    }
}
