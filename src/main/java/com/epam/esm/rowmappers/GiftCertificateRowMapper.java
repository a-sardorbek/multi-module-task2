package com.epam.esm.rowmappers;

import com.epam.esm.dto.GiftCertificateDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificateRowMapper implements RowMapper<GiftCertificateDto> {
    @Override
    public GiftCertificateDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        GiftCertificateDto giftCertificate = new GiftCertificateDto();
        giftCertificate.setId(rs.getInt("idGift"));
        giftCertificate.setName(rs.getString("name"));
        giftCertificate.setDescription(rs.getString("description"));
        giftCertificate.setPrice(rs.getDouble("price"));
        giftCertificate.setDuration(rs.getInt("duration"));
        giftCertificate.setCreateDate(rs.getString("create_date"));
        giftCertificate.setLastUpdateDate(rs.getString("last_update_date"));

        return giftCertificate;
    }
}
