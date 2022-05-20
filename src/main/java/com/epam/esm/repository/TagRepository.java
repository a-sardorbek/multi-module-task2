package com.epam.esm.repository;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.TagDtoNew;
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

    public int insertNew(String name) {
        String query = "insert into tag(name) values (?)";
        return jdbcTemplate.update(query,name);
    }


    public TagDto getById(Integer id) {
        String query = "select * from tag where idTag = ?";
        return jdbcTemplate.queryForObject(
                query,
                new TagRowMapper(),
                id);
    }

    public List<TagDtoNew> getAll() {
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

    public boolean checkExistsNew(String name) {
        String check = "select exists(select * from tag where name=?)";
        return jdbcTemplate.queryForObject(check,Boolean.class,name);
    }

    public boolean checkTagConnectedToGift(Integer id) {
        String check = "select exists(select id_tag from gift_certificate_tag where id_tag=?)";
        return jdbcTemplate.queryForObject(check,Boolean.class,id);
    }

    public int getNextId() {
        String query = "SELECT AUTO_INCREMENT" +
                " FROM information_schema.TABLES" +
                " WHERE TABLE_SCHEMA = 'modulestodo'" +
                " AND TABLE_NAME = 'tag'";
        return jdbcTemplate.queryForObject(query,Integer.class);
    }

    public int getLastTagId(String name) {
        String query = "select idTag from tag where name=?";
        return jdbcTemplate.queryForObject(query,Integer.class,name);
    }

    public String getNameTag(int parseInt) {
        String query = "select name from tag where idTag=?";
        return jdbcTemplate.queryForObject(query,String.class,parseInt);
    }
}
