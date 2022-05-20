package com.epam.esm.rowmappers;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.TagDtoNew;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagResultSet implements ResultSetExtractor<List<TagDtoNew>>{

    @Override
    public List<TagDtoNew> extractData(ResultSet rs) throws SQLException, DataAccessException {

        List<TagDtoNew> tagDtoList = new ArrayList<>();
        while(rs.next()){
            TagDtoNew tagDto = new TagDtoNew();
            tagDto.setName(rs.getString("name"));
            tagDtoList.add(tagDto);
        }
        return tagDtoList;
    }
}
