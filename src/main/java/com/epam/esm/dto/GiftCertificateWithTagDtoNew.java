package com.epam.esm.dto;

import java.util.List;

public class GiftCertificateWithTagDtoNew {

    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;
    private List<TagDtoNew> tagDto;

    public GiftCertificateWithTagDtoNew(){}

    public GiftCertificateWithTagDtoNew(Integer id, String name, String description, Double price, Integer duration, String createDate, String lastUpdateDate, List<TagDtoNew> tagDto) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tagDto = tagDto;
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
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<TagDtoNew> getTagDto() {
        return tagDto;
    }

    public void setTagDto(List<TagDtoNew> tagDto) {
        this.tagDto = tagDto;
    }
}
