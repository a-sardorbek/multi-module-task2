package com.epam.esm;

import com.epam.esm.dto.TagDto;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TagTest {

    private TagRepository tagRepository;

    TagDto tagDto;
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {

        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:jdbc/schema.sql")
                .addScript("classpath:jdbc/data.sql")
                .build();
        jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        tagRepository = new TagRepository(jdbcTemplate);
    }

    @Test
    public void listTags(){

        List<TagDto> allCertificate = tagRepository.getAll();
        assertNotNull(allCertificate);

        assertEquals(true,allCertificate.size()==4);
        assertEquals("Winter",allCertificate.get(0).getName());

        assertEquals(4, JdbcTestUtils.countRowsInTable(jdbcTemplate,"tag"));

    }

    @Test
    public void insertTag(){
        TagDto tagDto = new TagDto(5,"tag test");
        int newTag = tagRepository.insert(tagDto);
        assertEquals(1,newTag);
        assertNotNull(newTag);
    }

    @Test
    public void findTagById(){
        TagDto tagDto = tagRepository.getById(2);
        assertNotNull(tagDto);
        assertEquals("Snow",tagDto.getName());

    }

    @Test
    public void updateTagById(){
        TagDto tagTestUpdate = tagRepository.updateById(2,"tag test update");
        assertNotNull(tagTestUpdate);
        assertEquals("tag test update",tagTestUpdate.getName());
    }

    @Test
    public void deleteTagById(){
        int idExists = tagRepository.deleteById(2);
        assertEquals(1,idExists);

        int idNotExists = tagRepository.deleteById(23);
        assertEquals(0,idNotExists);
    }

    @Test
    public void checkExistsTag(){
        assertEquals(true,tagRepository.checkExists(2));
        assertEquals(false,tagRepository.checkExists(23));
    }

}
