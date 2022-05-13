package com.epam.esm.serviceTest;

import com.epam.esm.dto.*;
import com.epam.esm.exceptions.custom.GiftCertificateNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.implementation.GiftCertificateImpl;
import org.junit.Before;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GiftServiceTest {

    JdbcTemplate jdbcTemplate;
    DataSource dataSource;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @InjectMocks
    private GiftCertificateImpl giftCertificateImpl;

    @Before
    public void setUp(){
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/sql/create-db.sql")
                .addScript("db/sql/insert-data.sql")
                .build();
        jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
    }


    @Test
    public void createGiftSuccessfullyTest(){
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(
                12,"Birthday","Test description",50.5,1);

        when(giftCertificateRepository.checkExists(12)==false).thenReturn(false);
        when(giftCertificateRepository.insert(any(GiftCertificateDto.class))).thenReturn(1);
        assertEquals(1, giftCertificateImpl.create(giftCertificateDto));

    }

    @Test
    public void findAllTest(){
       when(giftCertificateRepository.getAll().size()==0).thenThrow(new GiftCertificateNotFoundException(""));
       assertThrows(GiftCertificateNotFoundException.class,()->giftCertificateImpl.findAll());
    }

    @Test
    public void findByIdTest(){
        assertThrows(GiftCertificateNotFoundException.class,()->giftCertificateImpl.findById("1a"));
        assertEquals(false,giftCertificateRepository.checkExists(1));
    }

    @Test
    public void deleteGiftUsingIdTest(){
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(
                12,"Birthday","Test description",50.5,1);
        when(giftCertificateRepository.deleteById(giftCertificateDto.getId(),false)).thenReturn(1);
        when(giftCertificateRepository.checkExists(giftCertificateDto.getId())).thenReturn(true);
        int deleted  = giftCertificateImpl.deleteUsingId(String.valueOf(giftCertificateDto.getId()));
        assertEquals(1,deleted);
        assertNotNull(deleted);

    }

    @Test
    public void throwExceptionIfIdNotCorrectUpdateGift(){
        assertThrows(GiftCertificateNotFoundException.class,()->giftCertificateImpl.updateGiftById("1a",new GiftUpdateDto()));
        assertEquals(false,giftCertificateRepository.checkExists(1));
    }

    @Test
    public void searchGift(){
        List<GiftCertificateWithTagDto> giftCertificateWithTagDto = new ArrayList<>();
        SearchGiftDto searchGiftDto = new SearchGiftDto();
        searchGiftDto.setName("happy");
        searchGiftDto.setDescription("now");
        searchGiftDto.setAscDesc("asc");
        searchGiftDto.setSortBy("name");
        assertEquals(giftCertificateWithTagDto,giftCertificateImpl.searchByNameOrDescription(searchGiftDto));
    }

}
