package com.epam.esm.repository;

import com.epam.esm.dto.*;
import com.epam.esm.rowmappers.GiftCertificateResultSet;
import com.epam.esm.rowmappers.GiftCertificateResultWithTagNew;
import com.epam.esm.rowmappers.GiftCertificateRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GiftCertificateRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    public int insert(GiftCertificateDto giftCertificateDto) {
        String query = "insert into gift_certificate(idGift,name,description,price,duration,create_date,last_update_date)" +
                " values (?,?,?,?,?,?,?)";

        return jdbcTemplate.update(query,
                giftCertificateDto.getId(),
                giftCertificateDto.getName(),
                giftCertificateDto.getDescription(),
                giftCertificateDto.getPrice(),
                giftCertificateDto.getDuration(),
                giftCertificateDto.getCreateDate(),
                giftCertificateDto.getLastUpdateDate());
    }

    public GiftResponse getById(Integer id) {
        String query = "select * from gift_certificate where idGift = ?";
        GiftResponse giftResponse = jdbcTemplate.queryForObject(
                query,
                new BeanPropertyRowMapper<>(GiftResponse.class)
                ,id);
        giftResponse.setId(id);
        return giftResponse;
    }

    public List<GiftCertificateDto> getAll() {
        String query = "select * from gift_certificate";
        return jdbcTemplate.query(query, new GiftCertificateRowMapper());
    }

    public int deleteById(Integer id,boolean orphanRemoval) {
        if(orphanRemoval){
            String deleteFromConnectedTable = "delete from gift_certificate_tag where id_gift_certificate = ?";
            jdbcTemplate.update(deleteFromConnectedTable,id);
            String deleteFromTag = "delete from gift_certificate where idGift = ?";
            return jdbcTemplate.update(deleteFromTag, id);
        }

            String query = "delete from gift_certificate where idGift = ?";
            return jdbcTemplate.update(query, id);
    }

    public boolean checkExists(Integer id){
        String check = "select exists(select * from gift_certificate where idGift=?)";
        return jdbcTemplate.queryForObject(check,Boolean.class,id);
    }


    public List<GiftCertificateWithTagDtoNew> searchGiftByPartNameDescription(String name, String description, String sortBy, String ascDesc, int withNameOrDescription){
        String query0 ="select gc.idGift, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date, t.idTag, t.name from\n" +
                "gift_certificate as gc inner join gift_certificate_tag as gct on (gc.idGift = gct.id_gift_certificate)\n" +
                "inner join tag as t on (t.idTag = gct.id_tag) where gc.name like ? group by id_gift_certificate order by gc."+sortBy+" "+ascDesc;

        String query1 = "select gc.idGift, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date, t.idTag, t.name from\n" +
                "gift_certificate as gc inner join gift_certificate_tag as gct on (gc.idGift = gct.id_gift_certificate)\n" +
                "inner join tag as t on (t.idTag = gct.id_tag) where gc.description like ? group by id_gift_certificate order by gc."+sortBy+" "+ascDesc;

        String query2 = "select gc.idGift, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date, t.idTag, t.name from\n" +
                "gift_certificate as gc inner join gift_certificate_tag as gct on (gc.idGift = gct.id_gift_certificate)\n" +
                "inner join tag as t on (t.idTag = gct.id_tag) where gc.name like ? or gc.description like ? group by id_gift_certificate order by gc."+sortBy+" "+ascDesc;

        switch (withNameOrDescription){
            case 0: return jdbcTemplate.query(query0, new GiftCertificateResultWithTagNew(jdbcTemplate),"%"+name+"%");
            case 1: return jdbcTemplate.query(query1, new GiftCertificateResultWithTagNew(jdbcTemplate),"%"+description+"%");
            case 2: return jdbcTemplate.query(query2, new GiftCertificateResultWithTagNew(jdbcTemplate),"%"+name+"%","%"+description+"%");
        }
        return new ArrayList<>();
    }


    public int insertWithTag(Integer tagId,Integer giftId) {
        String query = "insert into gift_certificate_tag(id_gift_certificate, id_tag) values (?,?)";
        return jdbcTemplate.update(query,
                giftId,tagId);
    }


    public List<GiftCertificateWithTagDtoNew> getListGiftCertificatesWithTagsNew() {
        String query = "select gc.idGift, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date, t.idTag, t.name from\n" +
                "gift_certificate as gc inner join gift_certificate_tag as gct on (gc.idGift = gct.id_gift_certificate)\n" +
                "inner join tag as t on (t.idTag = gct.id_tag) group by id_gift_certificate";
        return jdbcTemplate.query(query, new GiftCertificateResultWithTagNew(jdbcTemplate));
    }


    public GiftResponse updateById(Integer id, GiftUpdateDto giftUpdateDto) {

        String query = "update gift_certificate set name = ifnull(?,name), description = ifnull(?,description), price = ifnull(?,price), duration = ifnull(?,duration), last_update_date = ifnull(?,last_update_date) where idGift = ?";
        jdbcTemplate.update(query,
                giftUpdateDto.getName(),
                giftUpdateDto.getDescription(),
                giftUpdateDto.getPrice(),
                giftUpdateDto.getDuration(),
                giftUpdateDto.getLastUpdateDate(),
                id);


        String queryGet = "select * from gift_certificate where idGift = ?";

        GiftResponse giftResponse = jdbcTemplate.queryForObject(
                queryGet,
                new BeanPropertyRowMapper<>(GiftResponse.class)
                ,id);
        giftResponse.setId(id);
        return giftResponse;
    }


    public boolean checkTagIdAndGiftIdConnected(int giftId, int tagId) {
        String exist = "select exists(select * from gift_certificate_tag where id_gift_certificate=? and id_tag=?)";
        return jdbcTemplate.queryForObject(exist,Boolean.class,giftId,tagId);
    }

    public boolean checkTagConnectedToGift(int giftId) {
        String check = "select exists(select id_gift_certificate from gift_certificate_tag where id_gift_certificate=?)";
        return jdbcTemplate.queryForObject(check,Boolean.class,giftId);
    }
}
