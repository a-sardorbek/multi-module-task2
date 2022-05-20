package com.epam.esm.testing;

import com.epam.esm.dto.*;
import com.epam.esm.repository.GiftCertificateRepository;
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

@Qualifier("giftG")
@ExtendWith(SpringExtension.class)
public class GiftTest {

     JdbcTemplate jdbcTemplate;
     DataSource dataSource;
     GiftCertificateRepository giftCertificateRepository;

    @Before
    public void setUp(){
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/sql/create-db.sql")
                .addScript("db/sql/insert-data.sql")
                .build();
        jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        giftCertificateRepository = new GiftCertificateRepository(jdbcTemplate);
    }

    @Test
    public void testFindAll() {
        List<GiftCertificateDto> allCertificate = giftCertificateRepository.getAll();
        assertNotNull(allCertificate);

        assertEquals(true,allCertificate.size()==3);
        assertEquals("New Year",allCertificate.get(0).getName());

        assertEquals(3, JdbcTestUtils.countRowsInTable(jdbcTemplate,"gift_certificate"));

    }

    @Test
    public void insertNewGiftSuccessfully(){
        GiftCertificateDtoNew giftCertificateDto = new GiftCertificateDtoNew(
                15,
                "test certificate",
                "testing description",
                "500D",
                "10"
        );
        int newGift = giftCertificateRepository.insert(giftCertificateDto);
        assertEquals(1,newGift);
        assertEquals(4,giftCertificateRepository.getAll().size());
        assertNotNull(newGift);
    }




    @Test
    public void findGiftByIdSuccessfully(){
        GiftResponse giftCertificateDto = giftCertificateRepository.getById(2);
        assertNotNull(giftCertificateDto);
        assertEquals("Birthday",giftCertificateDto.getName());
    }


    @Test
    public void updateGiftByIdWithAllFields(){
        GiftUpdateDtoNew giftUpdateDto = new GiftUpdateDtoNew(
                "test gift",
                "test description",
                "50.5",
                25);
        GiftResponse giftCertificateDto = giftCertificateRepository.updateById(2,giftUpdateDto);
        assertNotNull(giftCertificateDto);
        assertEquals("test gift",giftCertificateDto.getName());
    }


    @Test
    public void updateOnlyNameOfGiftById(){
        GiftUpdateDtoNew giftUpdateDto = new GiftUpdateDtoNew();
        giftUpdateDto.setName("test name132");

        GiftResponse giftCertificateDto = giftCertificateRepository.updateById(2,giftUpdateDto);
        assertNotNull(giftCertificateDto);
        assertEquals("test name132",giftCertificateDto.getName());
        assertEquals("congrats",giftCertificateDto.getDescription());
    }


    @Test
    public void deleteGiftIfNotConnectedToTagById(){
        assertThrows(DataIntegrityViolationException.class,()->giftCertificateRepository.deleteById(2,false));
        assertEquals(1, giftCertificateRepository.deleteById(3,false));
        assertEquals(0, giftCertificateRepository.deleteById(3,false));
    }


    @Test
    public void deleteGiftConnectedToTagById(){
        assertEquals(1, giftCertificateRepository.deleteById(2,true));
        assertEquals(1, giftCertificateRepository.deleteById(3,false));
    }


    @Test
    public void checkExistsGiftWithGivenIds(){
        assertEquals(true,giftCertificateRepository.checkExists(2));
        assertEquals(false,giftCertificateRepository.checkExists(23));
    }




}
