package com.epam.esm.serviceTest;

import com.epam.esm.dto.TagDto;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.implementation.TagImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagImpl tagImpl;

    JdbcTemplate jdbcTemplate;
    DataSource dataSource;

    List<TagDto> tagDtos;
    @BeforeEach
    public void setup(){

        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/sql/create-db.sql")
                .addScript("db/sql/insert-data.sql")
                .build();
        jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);

        tagDtos = new ArrayList<>();
        tagDtos.add(new TagDto(1,"Birthday1"));
        tagDtos.add(new TagDto(1,"Birthday2"));
        tagDtos.add(new TagDto(3,"Birthday3"));
    }


    @Test
    public void createTest(){
        TagDto tagDto = new TagDto(1,"Birthday1");
        when(tagRepository.insert(tagDto)).thenReturn(1);
        assertEquals(1, tagImpl.create(tagDto));
        assertNotNull(tagDto);

    }

    @Test
    public void findAllTest(){
        when(tagRepository.getAll()).thenReturn(tagDtos);
        tagDtos = tagImpl.findAll();
        assertNotNull(tagDtos);
        assertEquals(3,tagDtos.size());
        assertEquals("Birthday1", tagImpl.findAll().get(0).getName());
    }


    @Test
    public void findByIdTest(){
        TagDto tagDto = new TagDto(1,"Birthday1");
        when(tagRepository.getById(tagDto.getId())).thenReturn(tagDto);
        when(tagRepository.checkExists(tagDto.getId())).thenReturn(true);
        tagDto = tagImpl.findById(String.valueOf(tagDto.getId()));
        assertEquals("Birthday1",tagDto.getName());
        assertNotNull(tagDto);

    }


    @Test
    public void deleteUsingIdTest(){
        TagDto tagDto = new TagDto(1,"Birthday1");
        when(tagRepository.deleteById(tagDto.getId(),false)).thenReturn(1);
        when(tagRepository.checkExists(tagDto.getId())).thenReturn(true);
        int deleted  = tagImpl.deleteUsingId(String.valueOf(tagDto.getId()));
        assertEquals(1,deleted);
        assertNotNull(deleted);

    }



}
