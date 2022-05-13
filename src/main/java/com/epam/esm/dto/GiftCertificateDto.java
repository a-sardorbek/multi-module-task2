package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GiftCertificateDto {

        @JsonProperty("id")
        private Integer id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("description")
        private String description;
        @JsonProperty("price")
        private Double price;
        @JsonProperty("duration")
        private Integer duration;
        @JsonProperty("createDate")
        private String createDate;
        @JsonProperty("lastUpdateDate")
        private String lastUpdateDate;

    public GiftCertificateDto() {
    }

    public GiftCertificateDto(Integer id, String name, String description, Double price, Integer duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = LocalDateTime.now().format( DateTimeFormatter.ISO_DATE_TIME );
        this.lastUpdateDate = LocalDateTime.now().format( DateTimeFormatter.ISO_DATE_TIME );
    }

    public GiftCertificateDto(Integer id, String name, String description, Double price, Integer duration,String lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.lastUpdateDate = lastUpdateDate;
    }


    public GiftCertificateDto(String name, String description, Double price, Integer duration) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
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
