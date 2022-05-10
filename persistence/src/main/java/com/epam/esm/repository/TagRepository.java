package com.epam.esm.repository;

import com.epam.esm.base.RootDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.rowmappers.TagRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepository implements RootDto<TagDto> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public int insert(TagDto tagDto) {
        String query = "insert into tag(id,name) values (?,?)";
        Integer id = tagDto.getId();
        String name = tagDto.getName();

        return jdbcTemplate.update(query,id,name);
    }

    @Override
    public TagDto getById(Integer id) {
        String query = "select * from tag where id = ?";
        return jdbcTemplate.queryForObject(
                query,
                new BeanPropertyRowMapper<>(TagDto.class),
                id);
    }

    @Override
    public List<TagDto> getAll() {
        String query = "select * from tag";
        return jdbcTemplate.query(query, new TagRowMapper());
    }

    @Override
    public int deleteById(Integer id) {
        String query = "delete from tag where id = ?";
        return jdbcTemplate.update(query, id);
    }

    @Override
    public boolean checkExists(Integer id) {
        String check = "select exists(select * from tag where id=?)";
        return jdbcTemplate.queryForObject(check,Boolean.class,id);
    }

    public TagDto updateById(Integer id, String tagName) {
        String query = "update tag set name = ? where id = ?";
        jdbcTemplate.update(query,
                tagName,
                id);

        return new TagDto(id,tagName);
    }

}
