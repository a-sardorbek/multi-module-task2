package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TagDtoNew {

    @JsonProperty("name")
    private String name;

    public TagDtoNew() {
    }

    public TagDtoNew(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
