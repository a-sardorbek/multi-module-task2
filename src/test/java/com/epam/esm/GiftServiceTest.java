package com.epam.esm;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GiftServiceTest {

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @InjectMocks
    private GiftCertificateService giftCertificateService;

    List<GiftCertificateDto> giftCertificateDtoList;
    GiftCertificateDto giftCertificateDto;
    @BeforeEach
    public void setup(){

        giftCertificateDtoList = new ArrayList<>();
        giftCertificateDtoList.add(new GiftCertificateDto(1,"Birthday1","Test description1",50.5,1));
        giftCertificateDtoList.add(new GiftCertificateDto(1,"Birthday2","Test description2",50.5,2));
        giftCertificateDtoList.add(new GiftCertificateDto(3,"Birthday3","Test description3",50.5,3));
    }


   @Test
   public void createTest(){
       GiftCertificateDto giftCertificateDto = new GiftCertificateDto(
               12,"Birthday","Test description",50.5,1);

       when(giftCertificateRepository.insert(giftCertificateDto)).thenReturn(1);
       assertEquals(1,giftCertificateService.create(giftCertificateDto));

   }

   @Test
    public void findAllTest(){
       when(giftCertificateRepository.getAll()).thenReturn(giftCertificateDtoList);
       giftCertificateDtoList = giftCertificateService.findAll();
       assertNotNull(giftCertificateDtoList);
       assertEquals(3,giftCertificateDtoList.size());
       assertEquals("Birthday1",giftCertificateService.findAll().get(0).getName());
   }


    @Test
    public void findByIdTest(){

        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(
                12,"Birthday","Test description",50.5,1);
        when(giftCertificateRepository.getById(giftCertificateDto.getId())).thenReturn(giftCertificateDto);
        when(giftCertificateRepository.checkExists(giftCertificateDto.getId())).thenReturn(true);
        giftCertificateDto = giftCertificateService.findById(String.valueOf(giftCertificateDto.getId()));
        assertEquals("Birthday",giftCertificateDto.getName());
        assertNotNull(giftCertificateDto);
    }


    @Test
    public void deleteUsingIdTest(){
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(
                12,"Birthday","Test description",50.5,1);
        when(giftCertificateRepository.deleteById(giftCertificateDto.getId())).thenReturn(1);
        when(giftCertificateRepository.checkExists(giftCertificateDto.getId())).thenReturn(true);
        int deleted  = giftCertificateService.deleteUsingId(String.valueOf(giftCertificateDto.getId()));
        assertEquals(1,deleted);
        assertNotNull(deleted);

    }



}
