package com.epam.esm.testing;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.TagDtoNew;
import com.epam.esm.repository.TagRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Qualifier("tagT")
@ExtendWith(SpringExtension.class)
public class TagTest {

    JdbcTemplate jdbcTemplate;
    DataSource dataSource;
    TagRepository tagRepository;

    @Before
    public void setUp(){
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/sql/create-db.sql")
                .addScript("db/sql/insert-data.sql")
                .build();
        jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        tagRepository = new TagRepository(jdbcTemplate);
    }


    @Test
    public void insertTagSuccessfully(){
        TagDto tagDto = new TagDto(5,"tag test");
        int newTag = tagRepository.insert(tagDto);
        assertEquals(1,newTag);
        assertEquals(5,tagRepository.getAll().size());
        assertNotNull(newTag);
    }

    @Test
    public void insertTagWithoutIdThrowsException(){
        TagDto tagDto = new TagDto();
        tagDto.setName("Tag test");
        assertThrows(DataIntegrityViolationException.class,()->tagRepository.insert(tagDto));
    }

    @Test
    public void findAllTagsSuccessfully(){
        List<TagDtoNew> allCertificate = tagRepository.getAll();
        assertNotNull(allCertificate);
        assertEquals(true,allCertificate.size()==4);
        assertEquals("Winter",allCertificate.get(0).getName());
        assertEquals(4, JdbcTestUtils.countRowsInTable(jdbcTemplate,"tag"));
    }

    @Test
    public void findTagByIdSuccessfully(){
        TagDto tagDto = tagRepository.getById(2);
        assertNotNull(tagDto);
        assertEquals("Snow",tagDto.getName());
    }


    @Test
    public void deleteTagByIdIfNotConnectedToGift(){
        int idExists = tagRepository.deleteById(1,false);
        assertEquals(1,idExists);
        int idNotExists = tagRepository.deleteById(23,false);
        assertEquals(0,idNotExists);
    }

    @Test
    public void deleteTagByIdConnectedToGift(){
        int idExists = tagRepository.deleteById(2,true);
        assertEquals(1,idExists);
    }

    @Test
    public void checkExistsTag(){
        assertEquals(true,tagRepository.checkExists(2));
        assertEquals(false,tagRepository.checkExists(23));
    }

}
