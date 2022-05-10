package com.epam.esm;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftUpdateDto;
import com.epam.esm.repository.GiftCertificateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GiftTest {

    private GiftCertificateRepository giftCertificateRepository;

    GiftCertificateDto giftCertificateDto;
    JdbcTemplate jdbcTemplate;
    DataSource dataSource;

    @BeforeEach
    void setUp() {

         dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:jdbc/schema.sql")
                .addScript("classpath:jdbc/data.sql")
                .build();
        jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        giftCertificateRepository = new GiftCertificateRepository(jdbcTemplate);
    }


    @Test
    public void listGifts(){

        List<GiftCertificateDto> allCertificate = giftCertificateRepository.getAll();
        assertNotNull(allCertificate);

        assertEquals(true,allCertificate.size()==3);
        assertEquals("New Year",allCertificate.get(0).getName());

        assertEquals(3,JdbcTestUtils.countRowsInTable(jdbcTemplate,"gift_certificate"));

    }

    @Test
    public void insertGift(){
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(
                15,
                "test certificate",
                "testing description",
                500D,
                10
        );
        int newGift = giftCertificateRepository.insert(giftCertificateDto);
        assertEquals(1,newGift);
        assertNotNull(newGift);
    }

    @Test
    public void findGiftById(){
        GiftCertificateDto giftCertificateDto = giftCertificateRepository.getById(2);
        assertNotNull(giftCertificateDto);
        assertEquals("Birthday",giftCertificateDto.getName());

    }


    @Test
    public void updateGiftById(){
        GiftUpdateDto giftUpdateDto = new GiftUpdateDto(
                "test gift",
                "test description",
                50.5,
                25);
        GiftCertificateDto giftCertificateDto = giftCertificateRepository.updateById(2,giftUpdateDto);
        assertNotNull(giftCertificateDto);
        assertEquals("test gift",giftCertificateDto.getName());
    }

    @Test
    public void deleteGiftById(){
        int idExists = giftCertificateRepository.deleteById(2);
        assertEquals(1,idExists);

        int idNotExists = giftCertificateRepository.deleteById(2);
        assertEquals(0,idNotExists);
    }

    @Test
    public void checkExistsGift(){
        assertEquals(true,giftCertificateRepository.checkExists(2));
        assertEquals(false,giftCertificateRepository.checkExists(23));
    }


}
