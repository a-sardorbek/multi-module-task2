package com.epam.esm.rowmappers;

import com.epam.esm.dto.TagDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class TagRowMapper implements RowMapper<TagDto> {

    @Override
    public TagDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        TagDto tagDto = new TagDto();
        Integer id = rs.getInt("idTag");
        String name = rs.getString("name");
        tagDto.setId(id);
        tagDto.setName(name);
        return tagDto;
    }
}
