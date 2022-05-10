package com.epam.esm.controller;

import com.epam.esm.dto.*;
import com.epam.esm.exceptions.custom.GiftCertificateNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@ResponseBody
@RequestMapping("/gift")
public class GiftCertificateController {

    @Autowired
    private GiftCertificateService giftCertificateService;

    @Autowired
    private Gson gson;

    @PostMapping(value = "/add-tag-to-gift",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity addGiftCertificateToTag(@RequestBody String giftTag){

        GiftTag giftTag1 = gson.fromJson(giftTag,GiftTag.class);
        giftCertificateService.insertTagIdToGift(giftTag1.getTag_id(),giftTag1.getGift_id());
        return new ResponseEntity(HttpStatus.OK);
    }


    @PostMapping(value = "/add",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity createGiftCertificate(@RequestBody String giftCertificate){
        GiftCertificateDto newGiftCertificate = gson.fromJson(giftCertificate,GiftCertificateDto.class);
        giftCertificateService.create(newGiftCertificate);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/update-by-id/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> updateById(@PathVariable("id")String id,
                                                         @RequestBody String giftUpdateDto) throws GiftCertificateNotFoundException {
        GiftCertificateDto giftCertificateDto =
                giftCertificateService.updateGiftById(id,gson.fromJson(giftUpdateDto,GiftUpdateDto.class));
        return new ResponseEntity<>(gson.toJson(giftCertificateDto),HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete-by-id/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> deleteById(@PathVariable("id")String id) throws GiftCertificateNotFoundException {
        giftCertificateService.deleteUsingId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/find-by-id/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> findById(@PathVariable("id")String id) throws GiftCertificateNotFoundException {
            GiftCertificateDto giftCertificateDto = giftCertificateService.findById(id);
        return new ResponseEntity<>(gson.toJson(giftCertificateDto),HttpStatus.OK);
    }


    @GetMapping(value = "/search-by-name-description",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> searchByNameOrDescription(@RequestBody String searchGiftDto){

        List<GiftCertificateWithTag> giftCertificateWithTag =
                giftCertificateService.searchByNameOrDescription(gson.fromJson(searchGiftDto,SearchGiftDto.class));

        return new ResponseEntity<>(gson.toJson(giftCertificateWithTag),HttpStatus.OK);
    }

    @GetMapping(value = "/all",produces = "application/json")
    public ResponseEntity<String> findAll(){
        String giftAsJson = gson.toJson(giftCertificateService.findAll());
        return new ResponseEntity<>(giftAsJson,HttpStatus.OK);
    }

    @GetMapping(value = "/all-with-tags",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> findAllWithTags(){
        String giftAsJson = gson.toJson(giftCertificateService.getListCertificateWithTags());
        return new ResponseEntity<>(giftAsJson,HttpStatus.OK);
    }


}
