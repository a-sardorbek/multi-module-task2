package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.TagDtoNew;

import java.util.List;

public interface TagService {

     int create(TagDto tagDto);
     TagDto findById(String id);
     List<TagDtoNew> findAll();
     int deleteUsingId(String id);

}
