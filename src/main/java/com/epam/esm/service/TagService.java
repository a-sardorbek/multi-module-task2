package com.epam.esm.service;

import com.epam.esm.base.RootService;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exceptions.custom.TagNotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TagService implements RootService<TagDto> {

    @Autowired
    private TagRepository tagRepository;


    @Override
    public int create(TagDto tagDto) {
        int newGift = 0;
        boolean giftExists = tagRepository.checkExists(tagDto.getId());
        if(!giftExists){
            newGift = tagRepository.insert(tagDto);
        }
        return newGift;
    }


    @Override
    public TagDto findById(String id) {
        if(!ServiceUtils.checkIsNumeric(id)) {
            throw new TagNotFoundException();
        }
        if(tagRepository.checkExists(Integer.parseInt(id)) == false) {
            throw new TagNotFoundException();
        }
        return tagRepository.getById(Integer.parseInt(id));
    }


    @Override
    public List<TagDto> findAll() {
        return tagRepository.getAll();
    }


    @Override
    public int deleteUsingId(String id) {
        if(!ServiceUtils.checkIsNumeric(id)) {
            throw new TagNotFoundException();
        }
        if(tagRepository.checkExists(Integer.parseInt(id)) == false) {
            throw new TagNotFoundException();
        }
        return tagRepository.deleteById(Integer.parseInt(id));
    }



    public TagDto updateTagById(String id, String tagName){
        if(!ServiceUtils.checkIsNumeric(id)) {
            throw new TagNotFoundException();
        }
        if(tagRepository.checkExists(Integer.parseInt(id)) == false) {
            throw new TagNotFoundException();
        }
        return tagRepository.updateById(Integer.parseInt(id),tagName);
    }

}
