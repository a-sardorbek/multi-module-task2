package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.TagUpdateDto;
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


    @PostMapping(value = "/add-tag",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity createTag(@RequestBody TagDto tagDto){
        tagService.create(tagDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/update-by-id", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TagDto> updateById(@RequestBody TagUpdateDto tagUpdateDto){
        TagDto tagResponseDto = tagService.updateTagById(tagUpdateDto.getId(),tagUpdateDto.getName());
        return new ResponseEntity<>(tagResponseDto,HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete-by-id/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity deleteById(@PathVariable("id") String id){
        tagService.deleteUsingId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/find-by-id/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TagDto> findById(@PathVariable("id")String id){
        TagDto tagDto = tagService.findById(id);
        return new ResponseEntity<>(tagDto,HttpStatus.OK);
    }

    @GetMapping(value = "/all",produces = "application/json")
    public ResponseEntity<List<TagDto>> findAll(){
        return new ResponseEntity<>(tagService.findAll(),HttpStatus.OK);
    }

}
