package com.epam.esm.rowmappers;

import com.epam.esm.dto.TagDto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagResultSet implements ResultSetExtractor<List<TagDto>>{

    @Override
    public List<TagDto> extractData(ResultSet rs) throws SQLException, DataAccessException {

        List<TagDto> tagDtoList = new ArrayList<>();
        while(rs.next()){
            TagDto tagDto = new TagDto();
            tagDto.setId(rs.getInt("idTag"));
            tagDto.setName(rs.getString("name"));
            tagDtoList.add(tagDto);
        }
        return tagDtoList;
    }
}
