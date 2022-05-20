package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GiftCertificateDtoNew {

        @JsonProperty("id")
        private Integer id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("description")
        private String description;
        @JsonProperty("price")
        private String price;
        @JsonProperty("duration")
        private String duration;
        @JsonProperty("createDate")
        private String createDate;
        @JsonProperty("lastUpdateDate")
        private String lastUpdateDate;

    public List<TagDtoNew> getTagDtoNewList() {
        return tagDtoNewList;
    }

    public void setTagDtoNewList(List<TagDtoNew> tagDtoNewList) {
        this.tagDtoNewList = tagDtoNewList;
    }

    List<TagDtoNew> tagDtoNewList;

    public GiftCertificateDtoNew() {
    }

    public GiftCertificateDtoNew(Integer id, String name, String description, String price, String duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = LocalDateTime.now().format( DateTimeFormatter.ISO_DATE_TIME );
        this.lastUpdateDate = LocalDateTime.now().format( DateTimeFormatter.ISO_DATE_TIME );
    }

    public GiftCertificateDtoNew(Integer id, String name, String description, String price, String duration, String lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.lastUpdateDate = lastUpdateDate;
    }


    public GiftCertificateDtoNew(String name, String description, String price, String duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.lastUpdateDate = LocalDateTime.now().format( DateTimeFormatter.ISO_DATE_TIME );
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCreateDate() {
        return LocalDateTime.now().format( DateTimeFormatter.ISO_DATE_TIME );
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateDate() {
        return LocalDateTime.now().format( DateTimeFormatter.ISO_DATE_TIME );
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

}
