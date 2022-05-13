package com.epam.esm.repository;

import com.epam.esm.dto.TagDto;
import com.epam.esm.rowmappers.TagResultSet;
import com.epam.esm.rowmappers.TagRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    public int insert(TagDto tagDto) {
        String query = "insert into tag(idTag,name) values (?,?)";
        Integer id = tagDto.getId();
        String name = tagDto.getName();

        return jdbcTemplate.update(query,id,name);
    }

    public TagDto getById(Integer id) {
        String query = "select * from tag where idTag = ?";
        return jdbcTemplate.queryForObject(
                query,
                new TagRowMapper(),
                id);
    }

    public List<TagDto> getAll() {
        String query = "select * from tag";
        return jdbcTemplate.query(query, new TagResultSet());
    }

    public int deleteById(Integer id,boolean orphanRemoval) {
        if(orphanRemoval){
           String deleteFromConnectedTable = "delete from gift_certificate_tag where id_tag = ?";
           jdbcTemplate.update(deleteFromConnectedTable,id);
           String deleteFromTag = "delete from tag where idTag = ?";
           return jdbcTemplate.update(deleteFromTag, id);
        }

        String query = "delete from tag where idTag = ?";
        return jdbcTemplate.update(query, id);
    }

    public boolean checkExists(Integer id) {
        String check = "select exists(select * from tag where idTag=?)";
        return jdbcTemplate.queryForObject(check,Boolean.class,id);
    }

    public TagDto updateById(Integer id, String tagName) {
        String query = "update tag set name = ? where idTag = ?";
        jdbcTemplate.update(query,
                tagName,
                id);

        return new TagDto(id,tagName);
    }

    public boolean checkTagConnectedToGift(Integer id) {
        String check = "select exists(select id_tag from gift_certificate_tag where id_tag=?)";
        return jdbcTemplate.queryForObject(check,Boolean.class,id);
    }
}
