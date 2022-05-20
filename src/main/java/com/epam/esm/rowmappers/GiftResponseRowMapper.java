package com.epam.esm.rowmappers;

import com.epam.esm.dto.GiftResponse;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.TagDtoNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GiftResponseRowMapper implements RowMapper<GiftResponse> {

    private JdbcTemplate jdbcTemplate;
    List<TagDtoNew> tagDtoList;

    @Autowired
    public GiftResponseRowMapper(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GiftResponse mapRow(ResultSet rs, int rowNum) throws SQLException {

        GiftResponse giftResponse = new GiftResponse();
        giftResponse.setId(rs.getInt("idGift"));
        giftResponse.setName(rs.getString("name"));
        giftResponse.setDescription(rs.getString("description"));
        giftResponse.setPrice(rs.getDouble("price"));
        giftResponse.setDuration(rs.getInt("duration"));
        giftResponse.setCreateDate(rs.getString("create_date"));
        giftResponse.setLastUpdateDate(rs.getString("last_update_date"));

        String query = "select t.idTag, t.name from " +
                "tag  as t left join gift_certificate_tag as gct on (t.idTag = gct.id_tag) " +
                "where gct.id_gift_certificate = ?";
        tagDtoList = jdbcTemplate.query(query,new TagResultSet(),giftResponse.getId());
        giftResponse.setTagDtoList(tagDtoList);

        return giftResponse;
    }
}
