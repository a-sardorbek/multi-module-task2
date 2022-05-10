package com.epam.esm.dto;

public class GiftTag {

    private String tag_id;
    private String gift_id;

    public GiftTag(){}

    public GiftTag(String tag_id, String gift_id) {
        this.tag_id = tag_id;
        this.gift_id = gift_id;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getGift_id() {
        return gift_id;
    }

    public void setGift_id(String gift_id) {
        this.gift_id = gift_id;
    }
}
