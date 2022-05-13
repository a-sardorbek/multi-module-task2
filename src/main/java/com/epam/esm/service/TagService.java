package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService {

     int create(TagDto tagDto);
     TagDto findById(String id);
     List<TagDto> findAll();
     int deleteUsingId(String id);
     TagDto updateTagById(String id, String tagName);

}
