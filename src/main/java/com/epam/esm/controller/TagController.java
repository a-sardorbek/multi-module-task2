package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.TagDtoNew;
import com.epam.esm.exceptions.custom.success.SuccessfullyCreated;
import com.epam.esm.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    private TagService tagService;

    public TagController(TagService tagService){
        this.tagService = tagService;
    }


    @PostMapping(value = "/",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity createTag(@RequestBody TagDto tagDto){
        int n = tagService.create(tagDto);
        if(n==1){
            throw new SuccessfullyCreated("Tag successfully created");
        }
        return new ResponseEntity(HttpStatus.OK);
    }



    @DeleteMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity deleteById(@PathVariable("id") String id){
        int n = tagService.deleteUsingId(id);
        if(n==1){
            throw new SuccessfullyCreated("Tag successfully deleted");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TagDto> findById(@PathVariable("id")String id){
        TagDto tagDto = tagService.findById(id);
        return new ResponseEntity<>(tagDto,HttpStatus.OK);
    }

    @GetMapping(value = "/all",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<TagDtoNew>> findAll(){
        return new ResponseEntity<>(tagService.findAll(),HttpStatus.OK);
    }

}
