package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TagDto {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;

    public TagDto(){}

    public TagDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
