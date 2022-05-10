package com.epam.esm;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private TagService tagService;

    List<TagDto> tagDtos;
    @BeforeEach
    public void setup(){

        tagDtos = new ArrayList<>();
        tagDtos.add(new TagDto(1,"Birthday1"));
        tagDtos.add(new TagDto(1,"Birthday2"));
        tagDtos.add(new TagDto(3,"Birthday3"));
    }


    @Test
    public void createTest(){
        TagDto tagDto = new TagDto(1,"Birthday1");
        when(tagRepository.insert(tagDto)).thenReturn(1);
        assertEquals(1,tagService.create(tagDto));
        assertNotNull(tagDto);

    }

    @Test
    public void findAllTest(){
        when(tagRepository.getAll()).thenReturn(tagDtos);
        tagDtos = tagService.findAll();
        assertNotNull(tagDtos);
        assertEquals(3,tagDtos.size());
        assertEquals("Birthday1",tagService.findAll().get(0).getName());
    }


    @Test
    public void findByIdTest(){
        TagDto tagDto = new TagDto(1,"Birthday1");
        when(tagRepository.getById(tagDto.getId())).thenReturn(tagDto);
        when(tagRepository.checkExists(tagDto.getId())).thenReturn(true);
        tagDto = tagService.findById(String.valueOf(tagDto.getId()));
        assertEquals("Birthday1",tagDto.getName());
        assertNotNull(tagDto);

    }


    @Test
    public void deleteUsingIdTest(){
        TagDto tagDto = new TagDto(1,"Birthday1");
        when(tagRepository.deleteById(tagDto.getId())).thenReturn(1);
        when(tagRepository.checkExists(tagDto.getId())).thenReturn(true);
        int deleted  = tagService.deleteUsingId(String.valueOf(tagDto.getId()));
        assertEquals(1,deleted);
        assertNotNull(deleted);

    }


}
