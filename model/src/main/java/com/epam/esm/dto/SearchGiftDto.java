package com.epam.esm.dto;

public class SearchGiftDto {

    private String name;
    private String description;
    private String sortBy;
    private String ascDesc;

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getAscDesc() {
        return ascDesc;
    }

    public void setAscDesc(String ascDesc) {
        this.ascDesc = ascDesc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
