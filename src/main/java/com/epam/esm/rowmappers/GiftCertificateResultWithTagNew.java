package com.epam.esm.rowmappers;

import com.epam.esm.dto.GiftCertificateWithTagDtoNew;
import com.epam.esm.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GiftCertificateResultWithTagNew implements ResultSetExtractor<List<GiftCertificateWithTagDtoNew>> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateResultWithTagNew(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GiftCertificateWithTagDtoNew> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<GiftCertificateWithTagDtoNew> giftCertificateDtoList = new ArrayList<>();
        List<TagDto> tagDtoList;

        while (rs.next()){
            GiftCertificateWithTagDtoNew giftCertificateWithTagDto = new GiftCertificateWithTagDtoNew();
            giftCertificateWithTagDto.setId(rs.getInt("idGift"));
            giftCertificateWithTagDto.setName(rs.getString("name"));
            giftCertificateWithTagDto.setDescription(rs.getString("description"));
            giftCertificateWithTagDto.setPrice(rs.getDouble("price"));
            giftCertificateWithTagDto.setDuration(rs.getInt("duration"));
            giftCertificateWithTagDto.setCreateDate(rs.getString("create_date"));
            giftCertificateWithTagDto.setLastUpdateDate(rs.getString("last_update_date"));

            String query = "select t.idTag, t.name from\n" +
                    "tag  as t left join gift_certificate_tag as gct on (t.idTag = gct.id_tag)\n" +
                    "where gct.id_gift_certificate = ?";
            tagDtoList = jdbcTemplate.query(query,new TagResultSet(),giftCertificateWithTagDto.getId());
            giftCertificateWithTagDto.setTagDto(tagDtoList);
            giftCertificateDtoList.add(giftCertificateWithTagDto);


        }
        return giftCertificateDtoList;
    }
}