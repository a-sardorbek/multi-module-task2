package com.epam.esm.repository;

import com.epam.esm.base.RootDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateWithTag;
import com.epam.esm.dto.GiftUpdateDto;
import com.epam.esm.rowmappers.GiftCertificateResultSet;
import com.epam.esm.rowmappers.GiftCertificateRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class GiftCertificateRepository implements RootDto<GiftCertificateDto> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public int insert(GiftCertificateDto giftCertificateDto) {

        String query = "insert into gift_certificate(id,name,description,price,duration,create_date,last_update_date)" +
                " values (?,?,?,?,?,?,?)";

        Integer id = giftCertificateDto.getId();
        String name = giftCertificateDto.getName();
        String description = giftCertificateDto.getDescription();
        Double price = giftCertificateDto.getPrice();
        Integer duration = giftCertificateDto.getDuration();
        String create_date =  giftCertificateDto.getCreateDate();
        String last_update =  giftCertificateDto.getLastUpdateDate();

        return jdbcTemplate.update(query,
                id,
                name,
                description,
                price,
                duration,
                create_date,
                last_update);
    }

    @Override
    public GiftCertificateDto getById(Integer id) {
        String query = "select * from gift_certificate where id = ?";
        return jdbcTemplate.queryForObject(
                query,
                new BeanPropertyRowMapper<>(GiftCertificateDto.class)
                ,id);
    }

    @Override
    public List<GiftCertificateDto> getAll() {
        String query = "select * from gift_certificate";
        return jdbcTemplate.query(query, new GiftCertificateRowMapper());
    }

    @Override
    public int deleteById(Integer id) {
            String query = "delete from gift_certificate where id = ?";
            return jdbcTemplate.update(query, id);
    }

    @Override
    public boolean checkExists(Integer id){
        String check = "select exists(select * from gift_certificate where id=?)";
        return jdbcTemplate.queryForObject(check,Boolean.class,id);
    }


    public List<GiftCertificateWithTag> searchGiftByPartNameDescription(String name, String description, String sortBy, String ascDesc,int withNameOrDescription){
        String query = "";

        List<GiftCertificateWithTag> giftCertificateWithTags = null;

        if(withNameOrDescription == 1) {
            query = "select gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date, t.id, t.name from\n" +
                    "gift_certificate as gc left join gift_certificate_tag as gct on (gc.id = gct.id_gift_certificate)\n" +
                    "left join tag as t on (t.id = gct.id_tag) where gc.description like ? order by gc."+sortBy+" "+ascDesc;
            giftCertificateWithTags = jdbcTemplate.query(query, new GiftCertificateResultSet(),"%"+description+"%");

        }

        if(withNameOrDescription == 0) {
            query = "select gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date, t.id, t.name from\n" +
                    "gift_certificate as gc left join gift_certificate_tag as gct on (gc.id = gct.id_gift_certificate)\n" +
                    "left join tag as t on (t.id = gct.id_tag) where gc.name like ? order by gc."+sortBy+" "+ascDesc;
            giftCertificateWithTags = jdbcTemplate.query(query, new GiftCertificateResultSet(),"%"+name+"%");
        }


        if(withNameOrDescription == 2) {
            query = "select gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date, t.id, t.name from\n" +
                    "gift_certificate as gc left join gift_certificate_tag as gct on (gc.id = gct.id_gift_certificate)\n" +
                    "left join tag as t on (t.id = gct.id_tag) where gc.name like ? or gc.description like ? order by gc."+sortBy+" "+ascDesc;
            giftCertificateWithTags = jdbcTemplate.query(query, new GiftCertificateResultSet(),"%"+name+"%","%"+description+"%");

        }


        return giftCertificateWithTags;
    }


    public int insertWithTag(Integer tag_id,Integer gift_id) {
        String query = "insert into gift_certificate_tag(id_gift_certificate, id_tag) values (?,?)";
        return jdbcTemplate.update(query,
                gift_id,tag_id);
    }



    public List<GiftCertificateWithTag> getListGiftCertificatesWithTags() {
        String query = "select gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date, t.id, t.name from\n" +
                "gift_certificate as gc left join gift_certificate_tag as gct on (gc.id = gct.id_gift_certificate)\n" +
                "left join tag as t on (t.id = gct.id_tag)";
        return jdbcTemplate.query(query, new GiftCertificateResultSet());
    }


    public GiftCertificateDto updateById(Integer id, GiftUpdateDto giftUpdateDto) {

        String query = "update gift_certificate set name = ?, description = ?, price = ?, duration = ?, last_update_date = ? where id = ?";

        String name = giftUpdateDto.getName();
        String description = giftUpdateDto.getDescription();
        Double price = giftUpdateDto.getPrice();
        Integer duration = giftUpdateDto.getDuration();
        String last_update_date =  giftUpdateDto.getLastUpdateDate();

        jdbcTemplate.update(query,
                name,
                description,
                price,
                duration,
                last_update_date,
                id);

        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(name,description,price,duration);;
        giftCertificateDto.setId(id);

        return giftCertificateDto;
    }


}
