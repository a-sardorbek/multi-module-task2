package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class GiftUpdateDtoNew {

    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("price")
    private String price;
    @JsonProperty("duration")
    private Integer duration;
    @JsonProperty("lastUpdateDate")
    private String lastUpdateDate;
    @JsonProperty("tagDtoNewList")
    List<TagDtoNew> tagDtoNewList;


    public GiftUpdateDtoNew(){}

    public GiftUpdateDtoNew(String name, String description, String price, Integer duration) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.lastUpdateDate = LocalDateTime.now().format( DateTimeFormatter.ISO_DATE_TIME );
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


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getLastUpdateDate() {
        return LocalDateTime.now().format( DateTimeFormatter.ISO_DATE_TIME );
    }

    public void setLastUpdateDate(String lastUpdateDate) {

        this.lastUpdateDate = lastUpdateDate;
    }
    public List<TagDtoNew> getTagDtoNewList() {
        return tagDtoNewList;
    }

    public void setTagDtoNewList(List<TagDtoNew> tagDtoNewList) {
        this.tagDtoNewList = tagDtoNewList;
    }

}
