package com.epam.esm.rowmappers;

import com.epam.esm.dto.GiftCertificateWithTagDto;
import com.epam.esm.dto.TagDto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GiftCertificateResultSet implements ResultSetExtractor<List<GiftCertificateWithTagDto>> {


    @Override
    public List<GiftCertificateWithTagDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<GiftCertificateWithTagDto> certificateList = new ArrayList<>();
        while (rs.next()){

            TagDto tagDto = new TagDto();
            tagDto.setId(rs.getInt("t.idTag"));
            tagDto.setName(rs.getString("t.name"));

            GiftCertificateWithTagDto giftCertificate = new GiftCertificateWithTagDto();
            giftCertificate.setId(rs.getInt("idGift"));
            giftCertificate.setName(rs.getString("name"));
            giftCertificate.setDescription(rs.getString("description"));
            giftCertificate.setPrice(rs.getDouble("price"));
            giftCertificate.setDuration(rs.getInt("duration"));
            giftCertificate.setCreateDate(rs.getString("create_date"));
            giftCertificate.setLastUpdateDate(rs.getString("last_update_date"));
            giftCertificate.setTagDto(tagDto);

            certificateList.add(giftCertificate);
        }
        return certificateList;
    }
}
