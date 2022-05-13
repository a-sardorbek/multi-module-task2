package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GiftTagDto {

    @JsonProperty("tagId")
    private String tagId;
    @JsonProperty("giftId")
    private String giftId;

    public GiftTagDto(){}

    public GiftTagDto(String tagId, String giftId) {
        this.tagId = tagId;
        this.giftId = giftId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }
}
