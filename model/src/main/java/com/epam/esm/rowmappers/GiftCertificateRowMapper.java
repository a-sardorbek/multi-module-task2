package com.epam.esm.rowmappers;

import com.epam.esm.dto.GiftCertificateDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificateRowMapper implements RowMapper<GiftCertificateDto> {
    @Override
    public GiftCertificateDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        GiftCertificateDto giftCertificate = new GiftCertificateDto();

        Integer id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        Double price = rs.getDouble("price");
        Integer duration = rs.getInt("duration");
        String create_date = rs.getString("create_date");
        String last_update_date = rs.getString("last_update_date");

        giftCertificate.setId(id);
        giftCertificate.setName(name);
        giftCertificate.setDescription(description);
        giftCertificate.setPrice(price);
        giftCertificate.setDuration(duration);
        giftCertificate.setCreateDate(create_date);
        giftCertificate.setLastUpdateDate(last_update_date);


        return giftCertificate;
    }
}
