package com.epam.esm.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GiftUpdateDto {

    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private String lastUpdateDate;


    public GiftUpdateDto(String name, String description, Double price, Integer duration) {
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

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
