package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exceptions.custom.TagNotFoundException;
import com.epam.esm.service.TagService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private Gson gson;

    @PostMapping(value = "/add-tag",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity createTag(@RequestBody String tagCertificate){

        TagDto tagDto = gson.fromJson(tagCertificate,TagDto.class);
        tagService.create(tagDto);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/update-by-id/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> updateById(@PathVariable("id")String id,
                                             @RequestBody String tagUpdateDto) throws TagNotFoundException {
        TagDto tagDto =
                tagService.updateTagById(id,tagUpdateDto);
        return new ResponseEntity<>(gson.toJson(tagDto),HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete-by-id/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> deleteById(@PathVariable("id")String id) throws TagNotFoundException {
        tagService.deleteUsingId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/find-by-id/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> findById(@PathVariable("id")String id) throws TagNotFoundException {
        TagDto tagDto = tagService.findById(id);
        return new ResponseEntity<>(gson.toJson(tagDto),HttpStatus.OK);
    }

    @GetMapping(value = "/all",produces = "application/json")
    public ResponseEntity<String> findAll(){
        String tagAsJson = gson.toJson(tagService.findAll());
        return new ResponseEntity<>(tagAsJson,HttpStatus.OK);
    }

}
