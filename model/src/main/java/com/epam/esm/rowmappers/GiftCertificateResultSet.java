package com.epam.esm.rowmappers;

import com.epam.esm.dto.GiftCertificateWithTag;
import com.epam.esm.dto.TagDto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GiftCertificateResultSet implements ResultSetExtractor<List<GiftCertificateWithTag>> {


    @Override
    public List<GiftCertificateWithTag> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<GiftCertificateWithTag> certificateList = new ArrayList<>();
        List<TagDto> tagList = new ArrayList<>();
        GiftCertificateWithTag giftCertificate;
        TagDto tagDto;
        while (rs.next()){
            Integer id = rs.getInt("id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            Double price = rs.getDouble("price");
            Integer duration = rs.getInt("duration");
            String create_date = rs.getString("create_date");
            String last_update_date = rs.getString("last_update_date");

            Integer idTag = rs.getInt("t.id");
            String tagNAme = rs.getString("t.name");

            tagDto = new TagDto();
            tagDto.setId(idTag);
            tagDto.setName(tagNAme);

            giftCertificate = new GiftCertificateWithTag();

            giftCertificate.setId(id);
            giftCertificate.setName(name);
            giftCertificate.setDescription(description);
            giftCertificate.setPrice(price);
            giftCertificate.setDuration(duration);
            giftCertificate.setCreateDate(create_date);
            giftCertificate.setLastUpdateDate(last_update_date);
            giftCertificate.setTagDto(tagDto);

            certificateList.add(giftCertificate);
        }


        return certificateList;
    }
}
