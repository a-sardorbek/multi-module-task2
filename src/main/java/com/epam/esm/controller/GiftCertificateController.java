package com.epam.esm.controller;

import com.epam.esm.dto.*;
import com.epam.esm.exceptions.custom.GiftCertificateNotFoundException;
import com.epam.esm.exceptions.custom.success.SuccessfullyCreated;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/gift")
public class GiftCertificateController {

    private GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }



    @PostMapping(value = "/",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity createGiftCertificate(@RequestBody GiftCertificateDtoNew giftCertificateDto){
        int n = giftCertificateService.create(giftCertificateDto);
        if(n==1){
           throw new SuccessfullyCreated("Gift successfully created");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<GiftResponse> updateById(@PathVariable("id")String id,
                                                   @RequestBody GiftUpdateDtoNew giftUpdateDto){
        return new ResponseEntity<>(giftCertificateService.updateGiftById(id,giftUpdateDto),HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteById(@PathVariable("id")String id){
        int n = giftCertificateService.deleteUsingId(id);
        if(n==1){
            throw new SuccessfullyCreated("Gift successfully deleted");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<GiftResponse> findById(@PathVariable("id")String id) throws GiftCertificateNotFoundException {
        return new ResponseEntity<>(giftCertificateService.findById(id),HttpStatus.OK);
    }


    @PostMapping(value = "/search-by-name-date",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<GiftCertificateWithTagDtoNew>> searchByNameOrDescription(@RequestBody SearchGiftDto searchGiftDto){
        List<GiftCertificateWithTagDtoNew> giftCertificateWithTagDto =
                giftCertificateService.searchByNameOrDescription(searchGiftDto);
        return new ResponseEntity<>(giftCertificateWithTagDto,HttpStatus.OK);
    }


    @GetMapping(value = "/all",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<GiftCertificateDto>> findAll(){
        return new ResponseEntity<>(giftCertificateService.findAll(),HttpStatus.OK);
    }


    @GetMapping(value = "/all-with-tags",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<GiftCertificateWithTagDtoNew>> findAllWithTagsNew(){
        return new ResponseEntity<>(giftCertificateService.getListCertificateWithTagsNew(),HttpStatus.OK);
    }


}
